package com.example.myprogress.app.FeauturesServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.Exercise;
import com.example.myprogress.app.Entites.RandomExercise;
import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;

import lombok.Data;

@Data
@Service
// In this class will do several query in different collection using only objects (RandomExercise)
public class RandomRoutineService {
    
    private final MongoTemplate mongoTemplate;
    private final CollectionProperties collectionProperties;

    
    // Rules to select series and reps
    // 0 a 30 minutos: 2 exercises 2 series x 12 reps for each exercise (2 exercises)
    // 30 minutos a 1 hora: 3 exercises 2 series x 12 reps for each exercise (3 exercises)
    // 1 a 1 hora y 30 minutos: 5 exercises 2 series x 12 reps for each exercise (5 exercises)
    // 1 y 30 minutos a 2 hora : 4 exercises 3 series x 15 reps for each exercise (4 exercises)
    // 2 a 3 horas: 5 exercises 3 series x 15  reps for each exercise (6 exercises)
    
    public Routine selectSeriesRepsOnlyMuscle(String timeAvailable,String muscleGroup) {
        String collectionName = getCollectionName(muscleGroup);// Here I get the name of the collection using the muscleGroup
        List<RandomExercise> exercisesRandom; 
        Routine routine = new Routine();
        switch (timeAvailable.toLowerCase()) {
            case "0 a 30 minutos" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 2); //Here I select a 2 exercises randomly according the muscle to train 
                //Here I create the routine and define the reps and series for each exercise (in this case 12 reps and 2 series by each exercise = 4 exercises)
                generateResponse(exercisesRandom, routine, "0 a 30 minutos", "Tiempo de descanso de 2 minutos entre series", 2, 12);
            }

            case "30 minutos a 1 hora" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 3); 
                generateResponse(exercisesRandom, routine, "30 minutos a 1 hora", "Tiempo de descanso de 3 minuto entre series", 2, 12);
            }
            case "1 a 1 hora y 30 minutos" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 5); 
                generateResponse(exercisesRandom, routine, "1 a 1 hora y 30 minutos", "Tiempo de descanso de 4 minutos entre series", 2, 12);
            }
            case "1 y 30 minutos a 2 horas" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 4); 
                generateResponse(exercisesRandom, routine, "1 y 30 minutos a 2 horas", "Tiempo de descanso de 4 minutos entre series", 3, 15);
                
            }
            case "2 a 3 horas" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 5); 
                generateResponse(exercisesRandom, routine, "2 a 3 horas", "Tiempo de descanso de 4 minutos entre series", 3, 15);
            }
            default -> throw new FieldIncorrectException("No se encontro la rutina para el grupo muscular: " + timeAvailable);
        }

        return routine;
    }

    
    // Rules to select series and reps to train two muscle
    // 0 a 30 minutos: First muscle 2 exercises 1 series x 12 reps for each exercise (2 exercises) and second muscle 2 exercises 1 series x 12 reps for each exercise (2 exercises)
    // 30 minutos a 1 hora: First muscle 3 exercises 1 series x 12 reps for each exercise (3 exercises) and second muscle 3 exercises 1 series x 12 reps for each exercise (3 exercises)
    // 1 a 1 hora y 30 minutos: First muscle 3 exercises 2 series x 15 reps for each exercise (3 exercises) and second muscle 3 exercises 2 series x 15 reps for each exercise (3 exercises)
    // 1 y 30 minutos a 2 hora : First muscle 4 exercises 2 series x 15 reps for each exercise (4 exercises) and second muscle 3 exercises 2 series x 12 reps for each exercise (2 exercises)
    // 2 a 3 horas: First muscle 4 exercises 2 series x 15 reps for each exercise (4 exercises) and second muscle 4 exercises 2 series x 12 reps for each exercise (2 exercises)
    public Routine selectSeriesRepsTwoMuscle(String timeAvailable,String muscleGroup,String muscleGroup2) {
        String collectionName = getCollectionName(muscleGroup);// Here I get the name of the collection using the first muscle to train
        String collectionName2 = getCollectionName(muscleGroup2);// Here I get the name of the collection using the second muscle to train
        List<RandomExercise> exercisesRandom; 
        List<RandomExercise> exercisesRandom2; 
        Routine routine = new Routine();
        routine.setListExercises(new ArrayList<>());
        switch (timeAvailable.toLowerCase()) {
            case "0 a 30 minutos" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 2); //Here I select a 2 exercises randomly according the muscle to train 
                exercisesRandom2 = getRandomExercises(collectionName2, muscleGroup2, 2); //Here I select a 2 exercises randomly according the muscle to train 
               generateResponse2(exercisesRandom, exercisesRandom2, routine, "0 a 30 minutos", "Tiempo de descanso de 2 minutos entre series", 1,12); 
            }
            case "30 minutos a 1 hora" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 3); 
                exercisesRandom2 = getRandomExercises(collectionName2, muscleGroup2,3); 
               generateResponse2(exercisesRandom, exercisesRandom2, routine, "30 minutos a 1 hora", "Tiempo de descanso de 3 minuto entre series", 1,12);
            }
            case "1 a 1 hora y 30 minutos" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 3); 
                exercisesRandom2 = getRandomExercises(collectionName2, muscleGroup2,3);
               generateResponse2(exercisesRandom, exercisesRandom2,routine, "1 a 1 hora y 30 minutos", "Tiempo de descanso de 4 minutos entre series", 2,15); 
            }
            case "1 y 30 minutos a 2 horas" -> {
                exercisesRandom = getRandomExercises(collectionName, muscleGroup, 3); 
                exercisesRandom2 = getRandomExercises(collectionName2, muscleGroup2,3);
               generateResponse2(exercisesRandom, exercisesRandom2,routine, "1 y 30 minutos a 2 horas", "Tiempo de descanso de 4 minutos entre series", 3,15); 

            }
            case "2 a 3 horas" -> {
               exercisesRandom = getRandomExercises(collectionName, muscleGroup, 4); 
               exercisesRandom2 = getRandomExercises(collectionName2, muscleGroup2,4);
               generateResponse2(exercisesRandom, exercisesRandom2,routine, "2 a 3 horas", "Tiempo de descanso de 4 minutos entre series", 3,15);
            }
            default -> throw new FieldIncorrectException("No se encontro la rutina para el grupo muscular: " + timeAvailable);
        }
        return routine;
    }

    public void generateResponse(List<RandomExercise> exercisesRandom,Routine routine,String timeDuration,String recommendation,int series,int reps) {
        routine.setListExercises(new ArrayList<>());
        routine.setTimeDuration(timeDuration);
        routine.setAnyRecomendation(recommendation);
        exercisesRandom.stream().forEach(exercise -> {
            Exercise exe = Exercise.builder().nameExercise(exercise.getName()).series(reps).repetitions(series).recommendation(exercise.getDescription()).build();
            routine.getListExercises().add(exe); //Here I add the exercise to the routine
        });                
    }
    public void generateResponse2(List<RandomExercise> exercisesRandom,List<RandomExercise> exercisesRandom2,Routine routine,String timeDuration,String recommendation,int series,int reps) {
        routine.setListExercises(new ArrayList<>());
        routine.setTimeDuration(timeDuration);
        routine.setAnyRecomendation(recommendation);
        exercisesRandom.stream().forEach(exercise -> {
            Exercise exe = Exercise.builder().nameExercise(exercise.getName()).series(reps).repetitions(series).recommendation(exercise.getDescription()).build();
            routine.getListExercises().add(exe); //Here I add the exercise to the routine
        });   
        exercisesRandom2.stream().forEach(exercise -> {
            Exercise exe = Exercise.builder().nameExercise(exercise.getName()).series(reps).repetitions(series).recommendation(exercise.getDescription()).build();
            routine.getListExercises().add(exe); //Here I add the exercise to the routine
        });               
    }



    // Here I select a random number of objects of that all docs met with the condition IN
    public List<RandomExercise> getRandomExercises(String collectionName, String muscleGroup, int limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("muscleGroups").regex("^" + muscleGroup + "$", "i")); // Case-insensitive regex search
        List<RandomExercise> exercises = mongoTemplate.find(query, RandomExercise.class, collectionName);
        Collections.shuffle(exercises); // Shuffle the documents randomly
        return exercises.stream().limit(limit).toList();
    }
    

    //Here I get the name of the collection using the muscleGroup

    public String getCollectionName(String muscleGroup) {
        switch (muscleGroup.toLowerCase()) {
            case "cuadriceps": return collectionProperties.getLegs();         // Legs
            case "gluteos": return collectionProperties.getLegs();         // Legs
            case "isquiotibiales": return collectionProperties.getLegs();
            case "aductores": return collectionProperties.getLegs();
            case "bíceps": return collectionProperties.getBic();          // Biceps
            case "trapecios": return collectionProperties.getTrap();      // Trapezoids
            case "tríceps": return collectionProperties.getTrip();        // Triceps
            case "abdomen": return collectionProperties.getAbs();     // Abs
            case "oblicuos": return collectionProperties.getAbs();     // Abs
            case "core": return collectionProperties.getAbs();     // Abs
            case "espalda": return collectionProperties.getBack();        // Back
            case "Pectorales Inferiores": return collectionProperties.getChest();         // Chest
            case "Pectorales": return collectionProperties.getChest();         // Chest
            case "Pectorales Superiores": return collectionProperties.getChest();         // Chest
            case "Pectorales Internos": return collectionProperties.getChest();         // Chest
            case "antebrazos": return collectionProperties.getFore();     // ForeArms
            case "gemelos": return collectionProperties.getCalf();   // Calf
            case "hombros": return collectionProperties.getSho();         // Shoulders
            default: throw new FieldIncorrectException("No se encontró la rutina para el grupo muscular: " + muscleGroup);
        }
    }
       
}
