package com.example.myprogress.app.SpringSecurity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

    /// This class is use to change the name a value that enter inside constructor per other name
    public abstract class ClassCasteoContructor {

        @JsonCreator // @JsonCreator: Indicates the constructor to use for deserialization, MARKS the constructor to be used for creating instances during deserialization. It tells Jackson to use this constructor when deserializing JSON into a SimpleGrantedAuthority object.
        ClassCasteoContructor (@JsonProperty("authority") String role){

        }   
    }
