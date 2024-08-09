package com.example.myprogress.app.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.FeauturesServices.RandomRoutineService;
import lombok.Data;

@Data
@RestController
@RequestMapping("/RandomRoutines")
public class RandomRoutinesController {

    private final RandomRoutineService randomRoutineService;

    @GetMapping("/GenerateOneMuscle")
   public ResponseEntity<?> getRecipe(@RequestParam String muscle,@RequestParam String timeAvailable) { 
    Routine routine = randomRoutineService.selectSeriesRepsOnlyMuscle(timeAvailable, muscle.toLowerCase());
    if(routine != null) {
        return ResponseEntity.status(HttpStatus.CREATED).body(routine);
    }  
    throw new FieldIncorrectException("Error al generar la rutina");         
}


    @GetMapping("/GenerateTwoMuscle")
   public ResponseEntity<?> generateTwoMuscle(@RequestParam String muscle,@RequestParam String muscle2,@RequestParam String timeAvailable) { 
        Routine routine = randomRoutineService.selectSeriesRepsTwoMuscle(timeAvailable, muscle.toLowerCase(), muscle2.toLowerCase());
    if(routine != null) {
        return ResponseEntity.status(HttpStatus.CREATED).body(routine);
    }  
    throw new FieldIncorrectException("Error al generar la rutina");         
}

}
