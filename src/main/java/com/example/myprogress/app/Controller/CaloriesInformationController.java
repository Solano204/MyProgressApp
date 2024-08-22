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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;
import io.swagger.v3.oas.annotations.Parameter;

// In this controller I get the information about a food
@RestController
@Data   
@RequestMapping("/InformationFood")
public class CaloriesInformationController {
    
private final informationCalories informationCalories;


@Operation(
        summary = "Retrieve information about a specific food",
        description = "Fetches nutritional information for a given food item and page number.",
        parameters = {
            @Parameter(name = "food", description = "Name of the food item", required = true, example = "apple"),
            @Parameter(name = "numPage", description = "Page number for pagination", required = true, example = "1")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved food information",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Todos los alimentos disponibles"))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request, invalid parameters",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "El alimento no fue encontrado"))
            )
        }
    )
@GetMapping("")
public ResponseEntity<?> getFoodInformation(@RequestParam String food, @RequestParam int numPage) {
    JSONArray information = informationCalories.ReciveFood(food, numPage);
    if(information != null){ 
        return ResponseEntity.status(HttpStatus.CREATED).body(information.toString());
    
}
throw new FieldIncorrectException("Error al generar la solicitud");         

}
}
