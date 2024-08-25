package com.example.myprogress.app.MongoTest;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.example.myprogress.app.ConfigurationTest.TestMongoConfig;
import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.InfosLogged;
import com.example.myprogress.app.Entites.Recipe;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Repositories.CaloriesIntakeDayRepository;
import com.example.myprogress.app.Repositories.RecipesRepository;
import com.example.myprogress.app.Repositories.RoutineRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.junit.jupiter.api.Assertions.*;




// In this test I check if the repository works correctly (CaloriesIntakeDayRepository Collection)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = { TestMongoConfig.class })
public class TestCaloriesIntakeRepository {

    @Autowired
    private CaloriesIntakeDayRepository caloriesIntakeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private CaloriesIntake caloriesIntake;
    private String user = "Carlos";

    @BeforeEach
    void setup() {
        mongoTemplate.dropCollection(CaloriesIntake.class);
        mongoTemplate.createCollection(CaloriesIntake.class);

        caloriesIntake = new CaloriesIntake();
        caloriesIntake.setId(user);
        caloriesIntake.setCalorieIntake(2000);
        caloriesIntake.setProteinsConsumed(150);
        caloriesIntake.setFatsConsumed(70);
        caloriesIntake.setCarbohydratesConsumed(300);
        caloriesIntakeRepository.save(caloriesIntake);
    }

    @Test
    void testSaveUser() {
        // Arrange
        CaloriesIntake newCaloriesIntake = new CaloriesIntake();
        newCaloriesIntake.setId("newUser");
        newCaloriesIntake.setCalorieIntake(2000);
        newCaloriesIntake.setProteinsConsumed(150);
        newCaloriesIntake.setFatsConsumed(70);
        newCaloriesIntake.setCarbohydratesConsumed(300);

        // Act
        caloriesIntakeRepository.save(newCaloriesIntake);
        CaloriesIntake saved = mongoTemplate.findById(newCaloriesIntake.getId(), CaloriesIntake.class);

        // Assert
        assertAll("Save User",
            () -> assertNotNull(saved, "Saved object should not be null"),
            () -> assertEquals("newUser", saved.getId(), "User ID should match"),
            () -> assertEquals(2000, saved.getCalorieIntake(), "Calorie intake should match"),
            () -> assertEquals(150, saved.getProteinsConsumed(), "Proteins consumed should match"),
            () -> assertEquals(70, saved.getFatsConsumed(), "Fats consumed should match"),
            () -> assertEquals(300, saved.getCarbohydratesConsumed(), "Carbohydrates consumed should match")
        ); 
    }

    @Test
    void testGetById() {
        // Act
        CaloriesIntake found = caloriesIntakeRepository.findById(caloriesIntake.getId()).orElse(null);

        // Assert
        assertAll("Get By ID",
            () -> assertNotNull(found, "Found object should not be null"),
            () -> assertEquals(caloriesIntake.getId(), found.getId(), "User ID should match"),
            () -> assertEquals(2000, found.getCalorieIntake(), "Calorie intake should match"),
            () -> assertEquals(150, found.getProteinsConsumed(), "Proteins consumed should match"),
            () -> assertEquals(70, found.getFatsConsumed(), "Fats consumed should match"),
            () -> assertEquals(300, found.getCarbohydratesConsumed(), "Carbohydrates consumed should match")
        );
    }

    @Test
    void testDeleteById() {
        // Act
        caloriesIntakeRepository.deleteById(caloriesIntake.getId());
        CaloriesIntake found = mongoTemplate.findById(caloriesIntake.getId(), CaloriesIntake.class);

        // Assert
        assertNull(found, "Object should be null after deletion");
    }

    @Test
    void testChangeDocumentId() {
        // Arrange
        Query query = new Query(Criteria.where("_id").is(caloriesIntake.getId()));
        String newId = "Carlos_New";
        CaloriesIntake originalDocument = mongoTemplate.findOne(query, CaloriesIntake.class);

        if (originalDocument != null) {
            originalDocument.setId(newId);
            mongoTemplate.save(originalDocument);

            // Act
            mongoTemplate.remove(query, CaloriesIntake.class);
            CaloriesIntake updatedDocument = mongoTemplate.findById(newId, CaloriesIntake.class);

            // Assert
            assertAll("Change Document ID",
                () -> assertNotNull(updatedDocument, "Updated document should not be null"),
                () -> assertEquals(newId, updatedDocument.getId(), "Document ID should be updated")
            );
        }
    }

    @Test
    void testUpdateCaloriesIntake() {
        // Arrange
        CaloriesIntake updatedData = new CaloriesIntake();
        updatedData.setCalorieIntake(2100);
        updatedData.setProteinsConsumed(160);
        updatedData.setFatsConsumed(80);
        updatedData.setCarbohydratesConsumed(310);

        Query query = new Query(Criteria.where("_id").is(caloriesIntake.getId()));
        Update update = new Update()
                .set("calorieIntake", updatedData.getCalorieIntake())
                .set("proteinsConsumed", updatedData.getProteinsConsumed())
                .set("fatsConsumed", updatedData.getFatsConsumed())
                .set("carbohydratesConsumed", updatedData.getCarbohydratesConsumed());

        // Act
        mongoTemplate.findAndModify(query, update, CaloriesIntake.class);
        CaloriesIntake updatedDocument = mongoTemplate.findById(caloriesIntake.getId(), CaloriesIntake.class);

        // Assert
        assertAll("Update Calories Intake",
            () -> assertNotNull(updatedDocument, "Updated document should not be null"),
            () -> assertEquals(2100, updatedDocument.getCalorieIntake(), "Calorie intake should be updated"),
            () -> assertEquals(160, updatedDocument.getProteinsConsumed(), "Proteins consumed should be updated"),
            () -> assertEquals(80, updatedDocument.getFatsConsumed(), "Fats consumed should be updated"),
            () -> assertEquals(310, updatedDocument.getCarbohydratesConsumed(), "Carbohydrates consumed should be updated")
        );
    }

    @Test
    void testAddCaloriesConsumed() {
        // Arrange
        Optional<CaloriesIntake> existingDataOpt = caloriesIntakeRepository.findById(user);
        if (!existingDataOpt.isPresent()) {
            fail("User not found for adding calories");
        }
        CaloriesIntake existingData = existingDataOpt.get();
        existingData.setCalorieIntake(existingData.getCalorieIntake() + 100);
        existingData.setProteinsConsumed(existingData.getProteinsConsumed() + 10);
        existingData.setFatsConsumed(existingData.getFatsConsumed() + 10);
        existingData.setCarbohydratesConsumed(existingData.getCarbohydratesConsumed() + 10);

        Query query = new Query(Criteria.where("_id").is(user));
        Update update = new Update()
                .set("calorieIntake", existingData.getCalorieIntake())
                .set("proteinsConsumed", existingData.getProteinsConsumed())
                .set("fatsConsumed", existingData.getFatsConsumed())
                .set("carbohydratesConsumed", existingData.getCarbohydratesConsumed());

        // Act
        mongoTemplate.findAndModify(query, update, CaloriesIntake.class);
        CaloriesIntake updatedData = mongoTemplate.findById(user, CaloriesIntake.class);

        // Assert
        assertAll("Add Calories Consumed",
            () -> assertNotNull(updatedData, "Updated data should not be null"),
            () -> assertEquals(2100, updatedData.getCalorieIntake(), "Calorie intake should be updated"),
            () -> assertEquals(160, updatedData.getProteinsConsumed(), "Proteins consumed should be updated"),
            () -> assertEquals(80, updatedData.getFatsConsumed(), "Fats consumed should be updated"),
            () -> assertEquals(310, updatedData.getCarbohydratesConsumed(), "Carbohydrates consumed should be updated")
        );
    }


    @Disabled
    void testVerifyState() {
        // Arrange
        CaloriesIntake caloriesIntake = new CaloriesIntake();
        caloriesIntake.setCalorieIntake(2000);
        caloriesIntake.setProteinsConsumed(150);
        caloriesIntake.setFatsConsumed(70);
        caloriesIntake.setCarbohydratesConsumed(300);

        appUser user = new appUser();
        user.setInfoLogged(new InfosLogged());
        user.getInfoLogged().setCurrentCalories(2500);
        user.getInfoLogged().setCurrentProtein(200);
        user.getInfoLogged().setCurrentCarbohydrates(350);
        user.getInfoLogged().setCurrentFats(90);

        Map<String, String> response = new HashMap<>();
        response.put("calories", String.valueOf(user.getInfoLogged().getCurrentCalories()));
        response.put("protein", String.valueOf(user.getInfoLogged().getCurrentProtein()));
        response.put("carbohydrates", String.valueOf(user.getInfoLogged().getCurrentCarbohydrates()));
        response.put("fats", String.valueOf(user.getInfoLogged().getCurrentFats()));

        // Assert
        assertAll("Verify State",
            () -> assertEquals("2500", response.get("calories"), "Calories should match"),
            () -> assertEquals("200", response.get("protein"), "Protein should match"),
            () -> assertEquals("350", response.get("carbohydrates"), "Carbohydrates should match"),
            () -> assertEquals("90", response.get("fats"), "Fats should match")
        );
    
    }
}