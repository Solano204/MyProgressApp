package com.example.myprogress.app.Controller;

import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.FeauturesServices.RandomRoutineService;
import com.example.myprogress.app.FeauturesServices.informationCalories;

import lombok.Data;

// In this controller I get the information about a food
@RestController
@Data   
@RequestMapping("/InformationFood")
public class CaloriesInformationController {
    
private final informationCalories informationCalories;


@GetMapping("")
public ResponseEntity<?> getFoodInformation(@RequestParam String food, @RequestParam int numPage) {
    JSONArray information = informationCalories.ReciveFood(food, numPage);
    if(information != null){ {
        return ResponseEntity.status(HttpStatus.CREATED).body(information.toString());
    }  
}
throw new FieldIncorrectException("Error al generar la solicitud");         

}
}
