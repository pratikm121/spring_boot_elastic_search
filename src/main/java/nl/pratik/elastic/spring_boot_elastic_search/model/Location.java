package nl.pratik.elastic.spring_boot_elastic_search.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class Location implements Serializable {
    private String name;
    private String city;
    private Country country;
    private boolean detailsHidden;
}
