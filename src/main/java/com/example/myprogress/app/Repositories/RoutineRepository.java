package com.example.myprogress.app.Repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.Entites.Routine;

public interface RoutineRepository extends MongoRepository<Routine, ObjectId> {

    List<Routine> findAllByUser(String user);
    Routine findByUserAndNameRoutine(String user, String nameRoutine);
    void deleteByUserAndNameRoutine(String user, String nameRoutine);

}
