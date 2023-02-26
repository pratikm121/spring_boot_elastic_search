package nl.pratik.elastic.spring_boot_elastic_search.repo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import lombok.extern.slf4j.Slf4j;
import nl.pratik.elastic.spring_boot_elastic_search.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class ShipmentRepo {

    private final ElasticsearchClient client;
    private static final String INDEX= "shipments_idx";

    @Autowired
    public ShipmentRepo(ElasticsearchClient client){
        this.client = client;
    }

    public void refreshIndex(){
        try {
            //client.delete(d -> d.index(INDEX));
            client.indices().delete(c -> c.index(INDEX));
            createIndex();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createIndex(){
        try {
            client.indices().create(c -> c.index(INDEX));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBulkShipments(List<Shipment> list){
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (Shipment s : list) {
            br.operations(op -> op.index(idx -> idx.index(INDEX)
                                                   .id(Integer.toString(s.getShipmentId())).document(s)));
        }

        try {
            BulkResponse result = client.bulk(br.build());
            if (result.errors()) {
                log.error("Bulk had errors");
                for (BulkResponseItem item: result.items()) {
                    if (item.error() != null) {
                        log.error(item.error().reason());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveShipment(Shipment s){
        try {
            IndexResponse response = client.index(i -> i.index(INDEX)
                                            .id(Integer.toString(s.getShipmentId()))
                                            .document(s));
            log.info("response {}", response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Shipment fetchByShipmentId(String id){
        GetResponse<Shipment> response;
        try {
            response = client.get(g -> g.index(INDEX).id(id), Shipment.class);
            if (response.found()) {
                Shipment s = response.source();
                log.info("Shipment {}", s);
                return s;
            } else {
                log.info("Shipment not found");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /* https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/searching.html*/
    public List<Shipment> searchInShipments(String term){
        /*Query to search in individual fields*/
        Query shippingLineCode = MatchQuery.of(m -> m.field("shippingLine.code").query(term))._toQuery();
        Query shipmentStatus = MatchQuery.of(m -> m.field("status").query(term))._toQuery();

        /*Query to search in combined fields*/
        List<String> fieldsList = Arrays.asList("shipmentId","shippingLine.code", "status","driverName","shipperName","locationActions.location.city");
        Query combined =MultiMatchQuery.of(m -> m.query(term).fields(fieldsList))._toQuery();

        // Search by max price
        //Query byMaxPrice = RangeQuery.of(r -> r.field("price").gte(JsonData.of(maxPrice)))._toQuery();

        SearchResponse<Shipment> response ;
        try {
            response = client.search(s -> s.index(INDEX)
                            .query(q -> q.bool(b -> b.must(combined)))
                            .sort(so -> so.field(f -> f.field("shipmentId").order(SortOrder.Asc)))
                            //.query(q -> q.bool(b -> b.must(shippingLineCode)))
                            , Shipment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TotalHits total = response.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

        if (isExactResult) {
            log.info("There are " + total.value() + " results");
        } else {
            log.info("There are more than " + total.value() + " results");
        }


        return response.hits().hits().stream().map(Hit::source).toList();
        /*
        List<Hit<Shipment>> hits = response.hits().hits();
        List<Shipment> shipmentList = new ArrayList<>();
        for (Hit<Shipment> hit: hits) {
            Shipment s = hit.source();
            shipmentList.add(s);
        }
        return shipmentList;
        */
    }
}
