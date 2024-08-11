package com.example.myprogress.app.FeauturesServices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Exceptions.FieldIncorrectException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;


@Service
public class informationCalories {

    public JSONArray ReciveFood(String food,int page) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://myfitnesspal2.p.rapidapi.com/searchByKeyword?keyword="+food+"%20breats&page="+page) // Here I send the request
                
                .get()
                .addHeader("x-rapidapi-key", "a346331799msh90ddab41c697ed3p1c787cjsnc9c74e15b7a6")
                .addHeader("x-rapidapi-host", "myfitnesspal2.p.rapidapi.com")
                .build();
        try {
            Response response = client.newCall(request).execute(); // Here I get the response
            ResponseBody responseBody = response.body();
            if (response.isSuccessful() && responseBody != null) { // if the response is successful
                return  translateEnglishToSpanish(responseBody);
            } else {
               throw new FieldIncorrectException("No se encontraron resultados");
            }
        } catch (IOException e) {
            throw new FieldIncorrectException("Error en la petición, Revise en la conexion"); 
        }
    }

    //This method will translate the headers or title from english to spanish
    public JSONArray translateEnglishToSpanish(ResponseBody responseBody)  throws IOException {

        String responseData = responseBody.string();
        // Returning the raw JSON response data as a string
        JSONArray jsonArray = new JSONArray(responseData);
        JSONArray translatedArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject originalItem = jsonArray.getJSONObject(i);
            JSONObject translatedItem = new JSONObject();

            // Translate the fields
            translatedItem.put("nombre", originalItem.getString("name")); // "name" -> "nombre"

            translatedItem.put("marca", originalItem.getString("brand")); // "brand" -> "marca"

            JSONObject originalNutrition = originalItem.getJSONObject("nutrition");
            JSONObject translatedNutrition = new JSONObject();

            translatedNutrition.put("Tamaño de la Porción", originalNutrition.getString("Serving Size"));
            translatedNutrition.put("Calorías", originalNutrition.getString("Calories"));
            translatedNutrition.put("Grasa", originalNutrition.getString("Fat"));
            translatedNutrition.put("Carbohidratos", originalNutrition.getString("Carbs"));
            translatedNutrition.put("Proteína", originalNutrition.getString("Protein"));

            translatedItem.put("nutrición", translatedNutrition);

            translatedArray.put(translatedItem);
        }
        return translatedArray;
}

}


