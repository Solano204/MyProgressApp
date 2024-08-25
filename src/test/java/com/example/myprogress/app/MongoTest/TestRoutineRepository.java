package com.example.myprogress.app.MongoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.example.myprogress.app.ConfigurationTest.TestMongoConfig;
import com.example.myprogress.app.Entites.Exercise;
import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.FeauturesServices.RoutineService;
import com.mongodb.client.result.UpdateResult;
import com.example.myprogress.app.Repositories.RoutineRepository;


// HERE I TEST THE ROUTINE REPOSITORY (ROUTINE COLLECTION)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = { TestMongoConfig.class })
public class TestRoutineRepository {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String user = "Carlos";
    private final String routineName = "New Routine";
    private final String nameExercise = "Push-up";
    private final String newName = "Push-up2";
    private final String newUserName = "Paco";
    private Routine routineGeneral;
    private Exercise newExercise;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Routine.class); // Clear the collection before each test
        mongoTemplate.createCollection(Routine.class);
        routineGeneral = new Routine();
        routineGeneral.setUser(user);
        routineGeneral.setNameRoutine(routineName);
        routineGeneral.setListExercises(new ArrayList<>());
        routineGeneral.setTimeDuration("00:30:00");
        newExercise = new Exercise(nameExercise, 3, 12, "Care to add more details?");
        routineGeneral.setAnyRecomendation("Care to add more details?");
        routineGeneral.getListExercises().add(newExercise);
        mongoTemplate.save(routineGeneral);
    }

    @Test
    void testAddNewExerciseToRoutine() {
        Exercise newExercise = new Exercise(newName, 3, 12, "Care to add more details?");
        routineGeneral.getListExercises().add(newExercise);
        mongoTemplate.save(routineGeneral);

        Routine updatedRoutine = mongoTemplate.findOne(Query.query(Criteria.where("nameRoutine").is(routineName)), Routine.class);

        assertAll("Add New Exercise To Routine",
            () -> assertNotNull(updatedRoutine, "Updated routine should not be null"),
            () -> assertTrue(updatedRoutine.getListExercises().stream()
                            .anyMatch(exercise -> newName.equals(exercise.getNameExercise())),
                            "New exercise should be added to the routine")
        );
    }

    @Test
    void testDeleteExerciseByName() {
        Criteria criteria = Criteria.where("user").is(user).and("nameRoutine").is(routineName);
        Update update = new Update().pull("listExercises", Query.query(Criteria.where("nameExercise").is(nameExercise)));
        UpdateResult result = mongoTemplate.updateFirst(Query.query(criteria), update, Routine.class);

        Routine updatedRoutine = mongoTemplate.findOne(Query.query(Criteria.where("nameRoutine").is(routineName)), Routine.class);

        assertAll("Delete Exercise By Name",
            () -> assertEquals(1, result.getModifiedCount(), "One document should be updated"),
            () -> assertFalse(updatedRoutine.getListExercises().stream()
                            .anyMatch(exercise -> nameExercise.equals(exercise.getNameExercise())),
                            "Exercise should be removed from the routine")
        );
    }

    @Test
    void testUpdateExercise() {
        Criteria criteria = Criteria.where("user").is(user).and("nameRoutine").is(routineName);
        Update update = new Update().pull("listExercises", Query.query(Criteria.where("nameExercise").is(nameExercise)));
        mongoTemplate.updateFirst(Query.query(criteria), update, Routine.class);

        Exercise updatedExercise = new Exercise(newName, 3, 12, "Care to add more details?");
        routineGeneral.setAnyRecomendation("Care to add more details?");
        routineGeneral.getListExercises().add(updatedExercise);
        mongoTemplate.save(routineGeneral);

        Routine updatedRoutine = mongoTemplate.findOne(Query.query(Criteria.where("nameRoutine").is(routineName)), Routine.class);

        assertAll("Update Exercise",
            () -> assertNotNull(updatedRoutine, "Updated routine should not be null"),
            () -> assertTrue(updatedRoutine.getListExercises().stream()
                            .anyMatch(exercise -> newName.equals(exercise.getNameExercise())),
                            "Updated exercise should be present in the routine")
        );
    }

    @Test
    void testUpdateExerciseName() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRoutine").is(routineName));
        Routine routine = mongoTemplate.findOne(query, Routine.class);

        if (routine != null) {
            routine.getListExercises().stream()
                    .filter(exercise -> nameExercise.equals(exercise.getNameExercise()))
                    .findFirst()
                    .ifPresent(exercise -> exercise.setNameExercise(newName));
            mongoTemplate.save(routine);

            Routine updatedRoutine = mongoTemplate.findOne(Query.query(Criteria.where("nameRoutine").is(routineName)), Routine.class);

            assertAll("Update Exercise Name",
                () -> assertNotNull(updatedRoutine, "Updated routine should not be null"),
                () -> assertTrue(updatedRoutine.getListExercises().stream()
                                .anyMatch(exercise -> newName.equals(exercise.getNameExercise())),
                                "Exercise name should be updated")
            );
        }
    }

    @Test
    void testUpdateUserInRoutines() {
        Query query = new Query(Criteria.where("user").is(user));
        Update update = new Update().set("user", newUserName);
        UpdateResult result = mongoTemplate.updateMulti(query, update, Routine.class);

        assertAll("Update User In Routines",
            () -> assertEquals(1, result.getModifiedCount(), "User should be updated in the routines"),
            () -> assertEquals(newUserName, mongoTemplate.findOne(Query.query(Criteria.where("user").is(newUserName)), Routine.class).getUser(), "User name should be updated")
        );
    }

    @Test
    void testDeleteRoutinesByUser() {
        Query query = new Query(Criteria.where("user").is(user));
        mongoTemplate.remove(query, Routine.class);

        assertAll("Delete Routines By User",
            () -> assertNull(mongoTemplate.findOne(Query.query(Criteria.where("user").is(user)), Routine.class), "Routine should be null after deletion")
        );
    }

    @Test
    void testSaveRoutine() {
        Routine newRoutine = new Routine();
        newRoutine.setUser("Carlos");
        newRoutine.setNameRoutine("New Routine");
        newRoutine.setListExercises(new ArrayList<>());
        newRoutine.setTimeDuration("00:45:00");
        newRoutine.setAnyRecomendation("New routine recommendation");
        Routine savedRoutine = mongoTemplate.save(newRoutine);

        assertAll("Save Routine",
            () -> assertNotNull(savedRoutine, "Saved routine should not be null"),
            () -> assertEquals("New Routine", savedRoutine.getNameRoutine(), "Routine name should match"),
            () -> assertEquals("00:45:00", savedRoutine.getTimeDuration(), "Time duration should match"),
            () -> assertEquals("New routine recommendation", savedRoutine.getAnyRecomendation(), "Recommendation should match")
        );
    }

    @Test
    void testGetAllRoutines() {
        List<Routine> routines = routineRepository.findAllByUser(user);

        assertAll("Get All Routines",
            () -> assertNotNull(routines, "Routines list should not be null"),
            () -> assertFalse(routines.isEmpty(), "Routines list should not be empty")
        );
    }

    @Test
    void testGetRoutine() {
        Routine routine = routineRepository.findByUserAndNameRoutine(user, routineName);

        assertAll("Get Routine",
            () -> assertNotNull(routine, "Routine should not be null"),
            () -> assertEquals(routineName, routine.getNameRoutine(), "Routine name should match")
        );
    }

    @Test
    void testDeleteRoutine() {
        routineRepository.deleteByUserAndNameRoutine(user, routineName);

        assertAll("Delete Routine",
            () -> assertNull(mongoTemplate.findOne(Query.query(Criteria.where("nameRoutine").is(routineName)), Routine.class), "Routine should be null after deletion")
        );
    }

    @Test
    void testChangeNameRoutine() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRoutine").is(routineName));
        Routine originalRoutine = mongoTemplate.findOne(query, Routine.class);

        if (originalRoutine != null) {
            originalRoutine.setNameRoutine(newName);
            mongoTemplate.save(originalRoutine);
            Routine updatedRoutine = mongoTemplate.findOne(Query.query(Criteria.where("nameRoutine").is(newName)), Routine.class);

            assertAll("Change Name Routine",
                () -> assertNotNull(updatedRoutine, "Updated routine should not be null"),
                () -> assertEquals(newName, updatedRoutine.getNameRoutine(), "Routine name should be updated")
            );
        }
    }

    @Test
    void testUpdateAproximateTime() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRoutine").is(routineName));
        Update update = new Update().set("AproximateTime", "00:30:10");
        UpdateResult result = mongoTemplate.updateFirst(query, update, Routine.class);

        assertAll("Update Approximate Time",
            () -> assertEquals(1, result.getModifiedCount(), "One document should be updated"),
            () -> assertEquals("00:30:10", mongoTemplate.findOne(Query.query(Criteria.where("nameRoutine").is(routineName)), Routine.class).getTimeDuration(), "Time duration should be updated")
        );
    }

    @Test
    void testUpdateAnyRecomendation() {
        Query query = new Query(Criteria.where("user").is(user).and("nameRoutine").is(routineName));
        Update update = new Update().set("anyRecomendation", "New routine recommendation");
        UpdateResult result = mongoTemplate.updateFirst(query, update, Routine.class);

        assertAll("Update Any Recommendation",
            () -> assertEquals(1, result.getModifiedCount(), "One document should be updated"),
            () -> assertEquals("New routine recommendation", mongoTemplate.findOne(Query.query(Criteria.where("nameRoutine").is(routineName)), Routine.class).getAnyRecomendation(), "Recommendation should be updated")
        );
    }
}
