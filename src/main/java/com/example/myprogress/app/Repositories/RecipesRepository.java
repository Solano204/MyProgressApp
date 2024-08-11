package com.example.myprogress.app.Repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.Entites.Routine;

public interface RecipesRepository   extends MongoRepository<Recipe, ObjectId>  {
   
    List<Recipe> findByUser(String user);  
    Recipe findByUserAndNameRecipe(String user, String nameRecipe);
    void deleteByUserAndNameRecipe(String user, String nameRecipe); 

}
