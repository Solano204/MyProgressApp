package com.example.myprogress.app.updateInformationService;

    import java.util.Map;
    import java.util.Objects;
    import java.util.Optional;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.mongodb.core.MongoTemplate;
    import org.springframework.data.mongodb.core.query.Criteria;
    import org.springframework.data.mongodb.core.query.Query;
    import org.springframework.data.mongodb.core.query.Update;
    import org.springframework.stereotype.Service;

    import com.example.myprogress.app.Entites.CaloriesIntake;
    import com.example.myprogress.app.Entites.appUser;
    import com.example.myprogress.app.Exceptions.FieldIncorrectException;
    import com.example.myprogress.app.Repositories.CaloriesIntakeDayRepository;


// This service will manage the collection caloriesIntake, all information linked with the user's progress
@Service
public class caloriesIntakeService {

    @Autowired
    private MongoTemplate mongoTemplate;
    private CaloriesIntakeDayRepository caloriesIntakeRepository;

    public caloriesIntakeService(CaloriesIntakeDayRepository caloriesIntakeRepository) {
        this.caloriesIntakeRepository = caloriesIntakeRepository;
    }

    // This method only will be execute when I new User is registered
    public CaloriesIntake saveUser(CaloriesIntake caloriesIntake) {
        return caloriesIntakeRepository.save(caloriesIntake);
    }

    // This method get the current protein, fat, carbohydrates and calorie intake
    public CaloriesIntake getById(String id) {
        return caloriesIntakeRepository.findById(id).get();
    }

    // This method will be used to delete the user in the application
    public void deleteById(String id) {
        CaloriesIntake existingDataOpt = caloriesIntakeRepository.findById(id).get();
        caloriesIntakeRepository.deleteById(existingDataOpt.getId());
    }


    // Here I have to update the doc calorieIntake related with the user
    public void changeDocumentId(String oldId, String newId) {
        // 1. Find the original document
        Query query = new Query(Criteria.where("_id").is(oldId));
        CaloriesIntake originalDocument = mongoTemplate.findOne(query, CaloriesIntake.class, "caloriesIntake");
        if (originalDocument != null) {
            originalDocument.setId(newId);
            mongoTemplate.save(originalDocument, "caloriesIntake");
            mongoTemplate.remove(query, "caloriesIntake");
        }
    }


    // Here I update the calorie intake of the user
    public CaloriesIntake updateCaloriesIntake(String idUser, CaloriesIntake updatedData) {
        Query query = new Query(Criteria.where("_id").is(idUser)); // Find document by idUser
        Update update = new Update();
        update.set("calorieIntake", updatedData.getCalorieIntake());
        update.set("proteinsConsumed", updatedData.getProteinsConsumed());
        update.set("fatsConsumed", updatedData.getFatsConsumed());
        update.set("carbohydratesConsumed", updatedData.getCarbohydratesConsumed());
        return mongoTemplate.findAndModify(query, update, CaloriesIntake.class);
    }

    // This method will be used to add the calories
    public boolean addCaloriesConsumed(CaloriesIntake caloriesIntake, appUser user, Map response) {
        Optional<CaloriesIntake> existingDataOpt = caloriesIntakeRepository.findById(user.getUser());
        if (!existingDataOpt.isPresent()) {
            throw new FieldIncorrectException("Happened an error while adding calories");
        }
        ;
        CaloriesIntake existingData = existingDataOpt.get();
        existingData.setCalorieIntake(caloriesIntake.getCalorieIntake());
        existingData.setProteinsConsumed(caloriesIntake.getProteinsConsumed());
        existingData.setFatsConsumed(caloriesIntake.getFatsConsumed());
        existingData.setCarbohydratesConsumed(caloriesIntake.getCarbohydratesConsumed());
        existingData = updateCaloriesIntake(user.getUser(), existingData); 
        verifyState(existingData, user, response);
        return true;
    }

    // This method will serve to recommender the user about the daily calorie,
    // protein, fat, carbohydrates intake and How much is left to achieve its limit
    @SuppressWarnings("unchecked")
    public void verifyState(CaloriesIntake caloriesIntake, appUser user, Map response) {
        int currentCalories = (user.getInfoLogged().getCurrentCalories() - caloriesIntake.getCalorieIntake());
        int currentProtein = (user.getInfoLogged().getCurrentProtein() - caloriesIntake.getProteinsConsumed());
        int currentCars = (user.getInfoLogged().getCurrentCarbohydrates() - caloriesIntake.getCarbohydratesConsumed());
        int currentFats = (user.getInfoLogged().getCurrentFats() - caloriesIntake.getFatsConsumed());

        response.put("StateCalories", (user.getInfoLogged().getCurrentCalories() > caloriesIntake.getCalorieIntake()
                ? "Le falta consumir " + (Math.abs(currentCalories) + " Para alcanzar su meta de calorias recomendadas")
                : " Ha consumido " + (Math.abs(currentCalories) + " calorias de mas del limite recomendado ")));

        response.put("StateProtein", (user.getInfoLogged().getCurrentProtein() > caloriesIntake.getProteinsConsumed()
                ? "Le falta consumir " + (Math.abs(currentProtein) + " Para alcanzar su meta de proteinas recomendadas")
                : " Ha consumido " + (Math.abs(currentProtein) + " proteinas de mas del limite recomendado ")));

        response.put("StateCarbohydrates",
                (user.getInfoLogged().getCurrentCarbohydrates() > caloriesIntake.getCarbohydratesConsumed()
                        ? "Le falta consumir "
                                + (Math.abs(currentCars) + " Para alcanzar su meta de carbohidratos recomendadas")
                        : " Ha consumido "
                                + (Math.abs(currentCars) + " carbohidratos de mas del limite recomendado ")));

        response.put("StateFats", (user.getInfoLogged().getCurrentFats() > caloriesIntake.getFatsConsumed()
                ? "Le falta consumir " + (Math.abs(currentFats) + " Para alcanzar su meta de grasas recomendadas")
                : " Ha consumido " + (Math.abs(currentFats) + " grasas de mas del limite recomendado ")));
    }

}
