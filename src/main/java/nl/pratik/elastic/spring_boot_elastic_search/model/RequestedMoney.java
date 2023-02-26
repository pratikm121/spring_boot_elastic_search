package nl.pratik.elastic.spring_boot_elastic_search.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestedMoney implements Serializable {
    private int value;
    private String currency;
}
