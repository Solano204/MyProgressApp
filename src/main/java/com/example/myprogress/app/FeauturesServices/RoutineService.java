package com.example.myprogress.app.FeauturesServices;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.example.myprogress.app.Entites.Exercise;
import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Repositories.RoutineRepository;

import lombok.Data;

@Service
@Data
public class RoutineService {


    private final MongoTemplate mongoTemplate;
    private final RoutineRepository routineRepository;

    /////// SECTIONS RELATED WITH THE EXERCISES OF THE ROUTINES ///////
    //Here I add a new exercise in a routine of the user // Add a new exercise to a routine
    public boolean addNewExerciseToRoutine(String routineName, String user, Exercise newExercise) {
        Criteria criteria = Criteria.where("_id").is(user.concat("/" + routineName));
        Update update = new Update().addToSet("listExercises", newExercise);
        return mongoTemplate.updateFirst(Query.query(criteria), update, Routine.class) != null;
    }

    // Delete an exercise from the routine by its name
    public boolean deleteExerciseByName(String routineName, String user, String exerciseName) {
        Criteria criteria = Criteria.where("_id").is(user.concat("/" + routineName));
        Update update = new Update().pull("listExercises", Query.query(Criteria.where("nameExercise").is(exerciseName)));
        return mongoTemplate.updateFirst(Query.query(criteria), update, Routine.class) != null;
    }

    // Update an exercise in a routine
    public boolean updateExercise(String routineName, String user, String exerciseName, Exercise updatedExercise) {
        return deleteExerciseByName(routineName, user, exerciseName) &&
               addNewExerciseToRoutine(routineName, user, updatedExercise);
    }

    // Update the name of an exercise in a routine
    public void updateExerciseName(String routineName, String user, String oldName, String newName) {
        Query query = new Query(Criteria.where("_id").is(user.concat("/" + routineName)));
        Routine routine = mongoTemplate.findOne(query, Routine.class);
        if (routine != null) {
            routine.getListExercises().stream()
                   .filter(exercise -> exercise.getNameExercise().equals(oldName))
                   .findFirst()
                   .ifPresent(exercise -> exercise.setNameExercise(newName));
            mongoTemplate.save(routine);
        } else {
            throw new FieldIncorrectException("Exercise not found");
        }
    }

    /////////////// SECTION RELATED TO USER ///////////////////////

    // Update all routines when the user changes their name to avoid inconsistencies
    public void updateUserInRoutines(String oldUserName, String newUserName) {
        Query query = new Query(Criteria.where("user").is(oldUserName));
        Update update = new Update().set("user", newUserName);
        mongoTemplate.updateMulti(query, update, Routine.class);
    }

    // Delete all routines when the user changes their name to avoid inconsistencies
    public void deleteRoutinesByUser(String userName) {
        Query query = new Query(Criteria.where("user").is(userName));
        mongoTemplate.remove(query, Routine.class);
    }

    ////// SECTION RELATED TO ROUTINE ///////////////////////

    // Save a new routine for a user
    public Routine saveRoutine(Routine routine) {
        routine.setNameRoutine(routine.getUser().concat("/" + routine.getNameRoutine()));
        try {
            return routineRepository.save(routine);
        } catch (Exception e) {
            throw new FieldIncorrectException("Failed to save new routine");
        }
    }

    // Get all routines for a user
    public List<Routine> getAllRoutines(String user) {
        return routineRepository.findByUser(user);
    }

    // Get a specific routine for a user
    public Routine getRoutine(String routineName, String user) {
        return routineRepository.findById(user.concat("/" + routineName)).orElseThrow(() ->
                new FieldIncorrectException("Routine not found"));
    }

    // Delete a routine for a user
    public void deleteRoutine(String routineName, String user) {
        try {
            routineRepository.deleteById(user.concat("/" + routineName));
        } catch (Exception e) {
            throw new FieldIncorrectException("Failed to delete routine");
        }
    }

    // Change the name of a routine
    public boolean changeNameRoutine(String oldName, String user, String newName) {
        Query query = new Query(Criteria.where("_id").is(user.concat("/" + oldName)));
        Routine originalRoutine = mongoTemplate.findOne(query, Routine.class, "Routines");
        if (originalRoutine != null) {
            originalRoutine.setNameRoutine(user.concat("/" + newName));
            mongoTemplate.save(originalRoutine, "Routines");
            mongoTemplate.remove(query, "Routines");
            return true;
        }
        return false;
    }

    // Update the approximate time duration of a routine
    public boolean updateAproximateTime(String routineName, String user, String newTimeDuration) {
        Query query = new Query(Criteria.where("_id").is(user.concat("/" + routineName)));
        Update update = new Update().set("AproximateTime", newTimeDuration);
        return mongoTemplate.updateFirst(query, update, Routine.class) != null;
    }

    // Update the recommendation for a routine
    public boolean updateAnyRecomendation(String routineName, String user, String newRecommendation) {
        Query query = new Query(Criteria.where("_id").is(user.concat("/" + routineName)));
        Update update = new Update().set("anyRecomendation", newRecommendation);
        return mongoTemplate.updateFirst(query, update, Routine.class) != null;
    }
}