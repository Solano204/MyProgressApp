package com.example.myprogress.app.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.Entites.Routine;

public interface RecipesRepository   extends MongoRepository<Recipe, String>  {
   
    List<Recipe> findByUser(String user);

}
