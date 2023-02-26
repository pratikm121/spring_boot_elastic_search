package nl.pratik.elastic.spring_boot_elastic_search.service;

import lombok.extern.slf4j.Slf4j;
import nl.pratik.elastic.spring_boot_elastic_search.model.Shipment;
import nl.pratik.elastic.spring_boot_elastic_search.repo.ShipmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ShipmentService {

    private final ShipmentRepo repo;

    @Autowired
    public ShipmentService(ShipmentRepo repo) {
        this.repo = repo;
    }

    public void refreshIndex(){
        this.repo.refreshIndex();
    }

    public void createIndex(){
        this.repo.createIndex();
    }

    public void saveShipment(Shipment s){
        log.info("Got shipment " + s);
        this.repo.saveShipment(s);
    }

    public void saveBulkShipments(List<Shipment> list){
        this.repo.saveBulkShipments(list);
    }

    public Shipment fetchByShipmentId(String id){
        return this.repo.fetchByShipmentId(id);
    }

    public List<Shipment> searchInShipments(String term){
        return this.repo.searchInShipments(term);
    }
}
