package nl.pratik.elastic.spring_boot_elastic_search.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Data
public class Shipment implements Serializable {

    private int shipmentId;
    private ShippingLine shippingLine;
    private String driverName;
    private String driverUUID;
    private String shipperName;
    private String shipmentType;
    private String shipmentTypeName;
    private String shipmentRequirement;
    private int shipmentRouteLength;
    private String shipmentUNCode;
    private RequestedMoney requestedMoney;
    private LatestQuote latestQuote;
    private Date endDate;
    private boolean dutyPaid;
    private int distanceToPickUpLocationInMeter;
    private String status;
    private boolean generatorSetRequired;
    private boolean fixedPrice;
    private boolean costable;
    private String shipmentStatusCode;
    private ArrayList<LocationAction> locationActions;
    private ArrayList<Requirement> requirements;
    private String isoType;
    private int quoteCount;
    private String carrierName;
    private String shipmentStatus;
    private String orderStatus;
    private String invoiceReference;
    private String currencyCode;
    private String currencySign;
    private boolean pendingChangeRequest;
    private int cocFieldsHashCode;
    private String driverFirstName;

}
