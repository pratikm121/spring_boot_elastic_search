package nl.pratik.elastic.spring_boot_elastic_search.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShippingLine implements Serializable {
    private int id;
    private String code;
    private String description;
    private boolean isSCREnabled;
}
