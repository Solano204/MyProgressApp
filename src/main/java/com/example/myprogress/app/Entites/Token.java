package com.example.myprogress.app.Entites;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This class will represent the current token that User is using

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {
    private String token;
    private String user;
}
