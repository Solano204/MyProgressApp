package com.example.myprogress.app.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.myprogress.app.Entites.CaloriesIntake;


// Here Im going to work with JPA NORMAL
@Repository
public interface CaloriesIntakeDayRepository extends MongoRepository<CaloriesIntake, String>{

    
}
