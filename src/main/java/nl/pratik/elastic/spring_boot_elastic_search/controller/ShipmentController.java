package nl.pratik.elastic.spring_boot_elastic_search.controller;

import nl.pratik.elastic.spring_boot_elastic_search.model.Shipment;
import nl.pratik.elastic.spring_boot_elastic_search.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/shipment")
public class ShipmentController {

    private final ShipmentService service;

    @Autowired
    public ShipmentController(ShipmentService service){
        this.service = service;
    }

    @PostMapping(value = "/refresh-index")
    public void refreshIndex(){
        this.service.refreshIndex();
    }

    @PostMapping(value = "/save")
    public void saveShipment(@RequestBody Shipment shipment){
        this.service.saveShipment(shipment);
    }

    @PostMapping(value = "/bulk-save")
    public void saveBulkShipment(@RequestBody List<Shipment> list){
        this.service.saveBulkShipments(list);
    }

    @GetMapping(value = "/{id}")
    public Shipment fetchByShipmentId(@PathVariable String id){
        return this.service.fetchByShipmentId(id);
    }

    @GetMapping(value = "search/{term}")
    public List<Shipment> searchInShipments(@PathVariable String term){
        return this.service.searchInShipments(term);
    }
}
