package com.example.myprogress.app.Entites;

import java.util.List;

import jakarta.persistence.Id;
import lombok.Data;


@Data
    public class RandomExercise {
        
            private String id;
            private String name;
            private String description;
            private List<String> muscleGroups;

        }
