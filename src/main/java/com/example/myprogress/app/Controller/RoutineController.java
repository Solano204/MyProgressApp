package com.example.myprogress.app.Controller;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.myprogress.app.Entites.Exercise;
import com.example.myprogress.app.Entites.Routine;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.FeauturesServices.RoutineService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.Data;

@Data
@RestController()
@RequestMapping("/Routine")
public class RoutineController {
    private final RoutineService routineService;
    //In this method add a new Routine
    @PostMapping("/AddNewRoutine")
   public ResponseEntity<?> addNewRoutine( @RequestBody Routine newRoutine) { 
       if(routineService.saveRoutine(newRoutine) != null) {
           return ResponseEntity.status(HttpStatus.CREATED).body("Rutina creada exitosamente");
       }  
       throw new FieldIncorrectException("La rutina no pudo ser creada");         
   }
    

   // This method will be used to delete the routine 
   @DeleteMapping("/DeleteRoutine")
   public ResponseEntity<?> deleteRoutine(@RequestParam String nameRoutine,@RequestParam String user) { 
       routineService.deleteRoutine(nameRoutine,user);
           return ResponseEntity.status(HttpStatus.CREATED).body("Rutina fue eleiminada exitosamente");
   }

   // Method to change the name of the routine
   @PutMapping("/ChangeNameRoutine")
   public ResponseEntity<?> changeNameRoutine(@RequestParam String oldNameRoutine,@RequestParam String newNameRoutine,@RequestParam String user) { 
    if(routineService.changeNameRoutine(oldNameRoutine, user, newNameRoutine)){
        return ResponseEntity.status(HttpStatus.CREATED).body("El cambio de nombre ala rutina fue exitosamente");
    }  
    throw new FieldIncorrectException("El cambio de nombre ala rutina se pudo realizar");         
}


 // Method to get the routine
 @GetMapping("/GetRoutine")
    public ResponseEntity<?> getRoutine(@RequestParam String nameRoutine,@RequestParam String user) { 
     if(routineService.getRoutine(nameRoutine, user) != null) {
         return ResponseEntity.status(HttpStatus.CREATED).body(getRoutine(nameRoutine, user));
     }  
     throw new FieldIncorrectException("Error al obtener la rutina");         
 }

 @GetMapping("/GetALLRoutines")
    public ResponseEntity<?> getRoutine(@RequestParam String user) {
        List<Routine> routines = routineService.getAllRoutines(user); 
     if(!routines.isEmpty() && routines != null) {
         return ResponseEntity.status(HttpStatus.CREATED).body(routines);
     }  
        return ResponseEntity.status(HttpStatus.OK).body("El usuario no tiene rutinas ");      
     
 }
 

//SECTION RELATED WITH THE EXERCISE

@PostMapping("/AddNewExercise")
public ResponseEntity<?> addNewExercise(@Valid @RequestBody Exercise newExercise,@RequestParam String nameRoutine,@RequestParam String user) {
    if(routineService.addNewExerciseToRoutine(nameRoutine, user, newExercise)) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Ejericio agregado exitosamente");
    }  
    throw new FieldIncorrectException("La ejercicio no pudo ser agregado");         
}

@DeleteMapping("/DeleteExercise")
public ResponseEntity<?> deleteExercise(@RequestParam String nameExercise, @RequestParam String nameRoutine,@RequestParam String user) {
    if(routineService.deleteExerciseByName(nameRoutine, user, nameExercise)) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ejericio agregado exitosamente");
    }  
    throw new FieldIncorrectException("El ejercicio no pudo ser eliminado");         
}


@PutMapping("/UpdateExercise")
public ResponseEntity<?> updateExercise(@Valid @RequestBody Exercise newExercise,@RequestParam String nameRoutine,@RequestParam String user,@RequestParam String nameExercise) {
    if(routineService.updateExercise(nameRoutine, user, nameExercise, newExercise)) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Ejericio fue modificado exitosamente");
    }  
    throw new FieldIncorrectException("El ejercicio no pudo ser modificado");         
}



//Method to update the approximate time
@PutMapping("/UpdateTime")
public ResponseEntity<?> updateTime( @RequestParam String nameRoutine,@RequestParam String user,@RequestParam  String newTimeDuration) {
    if(routineService.updateAproximateTime(nameRoutine,user, newTimeDuration)) {
        return ResponseEntity.status(HttpStatus.CREATED).body("EL Tiempo aproximado fue modificado exitosamente");
    }  
    throw new FieldIncorrectException("El tiempo no pudo ser modificado");         
}

//Method to update the Recomendation
@PutMapping("/UpdateRecomendation")
public ResponseEntity<?> updateRecomendation(@RequestParam String nameRoutine, @RequestParam String user,  @RequestParam String newRecomendation) {
        if(routineService.updateAnyRecomendation(nameRoutine,user, newRecomendation)) {
        return ResponseEntity.status(HttpStatus.CREATED).body("La Recomendacion fue modificado exitosamente");
    }  
    throw new FieldIncorrectException("La Recomendacion no pudo ser modificado");         
}


   
}
