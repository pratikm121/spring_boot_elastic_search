package nl.pratik.elastic.spring_boot_elastic_search.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActionType implements Serializable {
    private int id;
    private String code;
    private String description;
    private boolean disabled;
    private boolean displayed;
    private int sortOrder;
}
