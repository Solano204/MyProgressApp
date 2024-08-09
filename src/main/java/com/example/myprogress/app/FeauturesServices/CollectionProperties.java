package com.example.myprogress.app.FeauturesServices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "col") // Here I inject the properties with the prefix
@Data
public class CollectionProperties {
    //Here the fields have to be matched with the properties
    private String legs;
    private String bic;
    private String trap;
    private String trip;
    private String abs;
    private String back;
    private String chest;
    private String fore;
    private String calf;
    private String sho;

}
