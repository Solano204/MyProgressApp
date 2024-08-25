package com.example.myprogress.app.MongoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.myprogress.app.Entites.Exercise;
import com.example.myprogress.app.Entites.RandomExercise;
import com.example.myprogress.app.Entites.Routine;

@SpringBootTest

class MuscleAvaibleServiceTest {

    List<RandomExercise> exercisesRandom;
    // Reusable String variables for muscle groups
    private final String muscleGroup_Cuadriceps = "cuadriceps";
    private final String muscleGroup_Gluteos = "gluteos";
    private final String muscleGroup_Isquiotibiales = "isquiotibiales";
    private final String muscleGroup_Aductores = "aductores";
    private final String muscleGroup_Biceps = "bíceps";
    private final String muscleGroup_Trapecios = "trapecios";
    private final String muscleGroup_Triceps = "tríceps";
    private final String muscleGroup_Abdomen = "abdomen";
    private final String muscleGroup_Oblicuos = "oblicuos";
    private final String muscleGroup_Core = "core";
    private final String muscleGroup_Espalda = "espalda";
    private final String muscleGroup_PectoralesInferiores = "Pectorales Inferiores";
    private final String muscleGroup_Pectorales = "Pectorales";
    private final String muscleGroup_PectoralesSuperiores = "Pectorales Superiores";
    private final String muscleGroup_PectoralesInternos = "Pectorales Internos";
    private final String muscleGroup_Antebrazos = "antebrazos";
    private final String muscleGroup_Gemelos = "gemelos";
    private final String muscleGroup_Hombros = "hombros";
    public static final String TIME_0_TO_30_MINUTES = "0 a 30 minutos";
    public static final String TIME_30_MINUTES_TO_1_HOUR = "30 minutos a 1 hora";
    public static final String TIME_1_TO_1_HOUR_30_MINUTES = "1 a 1 hora y 30 minutos";
    public static final String TIME_1_HOUR_30_MINUTES_TO_2_HOURS = "1 y 30 minutos a 2 horas";
    public static final String TIME_2_TO_3_HOURS = "2 a 3 horas";

    @BeforeEach
    void setUp() {
        exercisesRandom = new ArrayList<>();
    }

    @Test
    void listExercisesTest() {

        // Before operation
        assertAll("Initial Checks",
                () -> assertNotNull(muscleGroup_Gluteos),
                () -> assertEquals(0, exercisesRandom.size()) // Simulate expected condition
        );

        exercisesRandom = getRandomExercises("some collection to search the exercises that include that muscle",
                muscleGroup_Gluteos, 2);

        // After operation
        assertAll("Post Operation Checks",
                () -> assertNotNull(exercisesRandom),
                () -> assertEquals(2, exercisesRandom.size()),
                () -> assertTrue(
                        exercisesRandom.stream().allMatch(ex -> ex.getMuscleGroups().contains(muscleGroup_Gluteos))));
    }

    @Test
    void generateRoutineWithOneMuscle() {
        List<RandomExercise> exercisesRandom;
        Routine routine = new Routine();

        // Before operation
        assertAll("Initial Checks",
                () -> assertNotNull(muscleGroup_Gluteos),
                () -> assertNull(routine.getListExercises()));

        exercisesRandom = getRandomExercises("some collection to search the exercises that include that muscle",
                muscleGroup_Gluteos, 2);

        generateResponse(exercisesRandom, routine, TIME_0_TO_30_MINUTES,
                "Tiempo de descanso de 2 minutos entre series", 1, 12);

        // After operation
        assertAll("Post Operation Checks",
                () -> assertNotNull(routine.getListExercises()),
                () -> assertEquals(2, routine.getListExercises().size()),
                () -> assertEquals(TIME_0_TO_30_MINUTES, routine.getTimeDuration()),
                () -> assertEquals("Tiempo de descanso de 2 minutos entre series", routine.getAnyRecomendation()),
                () -> assertTrue(routine.getListExercises().stream().allMatch(ex -> ex.getSeries() == 1)),
                () -> assertTrue(routine.getListExercises().stream().allMatch(ex -> ex.getRepetitions() == 12)),
                () -> assertTrue(exercisesRandom.stream().anyMatch(ex -> ex.getMuscleGroups().contains("gluteos"))));
    }

    @Test
    void generateRoutineWithTwoMuscles() {
        List<RandomExercise> exercisesRandom;
        List<RandomExercise> exercisesRandom2;
        Routine routine = new Routine();

        // Before operation
        assertAll("Initial Checks",
                () -> assertNotNull(muscleGroup_Gluteos),
                () -> assertNotNull(muscleGroup_Espalda),
                () -> assertNull(routine.getListExercises()));

        exercisesRandom = getRandomExercises("some collection to search the exercises that include that muscle",
                muscleGroup_Gluteos, 2);
        exercisesRandom2 = getRandomExercises("some collection to search the exercises that include that muscle",
                muscleGroup_Espalda, 2);

        generateResponse2(exercisesRandom, exercisesRandom2, routine, TIME_1_HOUR_30_MINUTES_TO_2_HOURS,
                "Tiempo de descanso de 2 minutos entre series", 1, 12);

        // After operation
        assertAll("Post Operation Checks",
                () -> assertNotNull(routine.getListExercises()),
                () -> assertEquals(4, routine.getListExercises().size()), // 2 from each muscle group
                () -> assertEquals(TIME_1_HOUR_30_MINUTES_TO_2_HOURS, routine.getTimeDuration()),
                () -> assertEquals("Tiempo de descanso de 2 minutos entre series", routine.getAnyRecomendation()),
                () -> assertTrue(routine.getListExercises().stream().allMatch(ex -> ex.getSeries() == 1)),
                () -> assertTrue(routine.getListExercises().stream().allMatch(ex -> ex.getRepetitions() == 12)),
                () -> assertTrue(exercisesRandom.stream().anyMatch(ex -> ex.getMuscleGroups().contains("gluteos"))),
                () -> assertTrue(exercisesRandom2.stream().anyMatch(ex -> ex.getMuscleGroups().contains("espalda"))));
    }

    
    // Supporting methods as in the original class
    public List<RandomExercise> getRandomExercises(String collectionName, String muscleGroup, int limit) {
        List<RandomExercise> exercises = new ArrayList<>();
        exercises.add(RandomExercise.builder()
                .name("1 exercise that includes that muscle and " + collectionName)
                .description("description of exercise").muscleGroups(List.of(muscleGroup, "other muscle"))
                .build());
        exercises.add(RandomExercise.builder()
                .name("2 exercise that includes that muscle and " + collectionName)
                .description("description of exercise").muscleGroups(List.of(muscleGroup, "other muscle"))
                .build());
        exercises.add(RandomExercise.builder()
                .name("3 exercise that includes that muscle and " + collectionName)
                .description("description of exercise").muscleGroups(List.of(muscleGroup, "other muscle"))
                .build());
        exercises.add(RandomExercise.builder()
                .name("4 exercise that includes that muscle and " + collectionName)
                .description("description of exercise").muscleGroups(List.of(muscleGroup, "other muscle"))
                .build());
        exercises.add(RandomExercise.builder()
                .name("5 exercise that includes that muscle and " + collectionName)
                .description("description of exercise").muscleGroups(List.of(muscleGroup, "other muscle"))
                .build());

        Collections.shuffle(exercises);
        return exercises.stream().limit(limit).toList();
    }

    public void generateResponse(List<RandomExercise> exercisesRandom, Routine routine, String timeDuration,
            String recommendation, int series, int reps) {
        routine.setListExercises(new ArrayList<>());
        routine.setTimeDuration(timeDuration);
        routine.setAnyRecomendation(recommendation);
        exercisesRandom.forEach(exercise -> {
            Exercise exe = Exercise.builder()
                    .nameExercise(exercise.getName())
                    .series(series)
                    .repetitions(reps)
                    .recommendation(exercise.getDescription())
                    .build();
            routine.getListExercises().add(exe); // Here I add the exercise to the routine
        });
    }

    public void generateResponse2(List<RandomExercise> exercisesRandom, List<RandomExercise> exercisesRandom2,
            Routine routine, String timeDuration, String recommendation, int series, int reps) {
        routine.setListExercises(new ArrayList<>());
        routine.setTimeDuration(timeDuration);
        routine.setAnyRecomendation(recommendation);
        exercisesRandom.forEach(exercise -> {
            Exercise exe = Exercise.builder()
                    .nameExercise(exercise.getName())
                    .series(series)
                    .repetitions(reps)
                    .recommendation(exercise.getDescription())
                    .build();
            routine.getListExercises().add(exe); // Here I add the exercise to the routine
        });

        exercisesRandom2.forEach(exercise -> {
            Exercise exe = Exercise.builder()
                    .nameExercise(exercise.getName())
                    .series(series)
                    .repetitions(reps)
                    .recommendation(exercise.getDescription())
                    .build();
            routine.getListExercises().add(exe); // Here I add the exercise to the routine
        });
    }
}