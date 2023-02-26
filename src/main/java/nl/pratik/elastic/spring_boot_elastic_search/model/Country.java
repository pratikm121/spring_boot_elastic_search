package nl.pratik.elastic.spring_boot_elastic_search.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Country implements Serializable {
    private int id;
    private String countryCode;
    private String description;
    private boolean disabled;
}
