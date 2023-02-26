package nl.pratik.elastic.spring_boot_elastic_search.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LocationAction implements Serializable {
    private int id;
    private int sequenceNumber;
    private Date dateFrom;
    private Date dateUntil;
    private ActionType actionType;
    private Location location;
    private boolean lateToLocation;
    private int shipmentNumber;
}
