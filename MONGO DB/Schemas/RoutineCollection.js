db.createCollection("Exercises", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["_id", "user", "listExercises", "AproximateTime", "anyRecomendation"],
        properties: {
          "_id": {
            "bsonType": "string",
            "description": "Must be a string and is required"
          },
          "user": {
            "bsonType": "string",
            "description": "Must be a string and is required"
          },
          "listExercises": {
            "bsonType": "array",
            "description": "Must be an array and is required",
            "items": {
              "bsonType": "object",
              "required": ["nameExercise", "repetitions", "series", "recommendation"],
              "properties": {
                "nameExercise": {
                  "bsonType": "string",
                  "description": "Must be a string and is required"
                },
                "repetitions": {
                  "bsonType": "int",
                  "description": "Must be an integer and is required"
                },
                "series": {
                  "bsonType": "int",
                  "description": "Must be an integer and is required"
                },
                "recommendation": {
                  "bsonType": "string",
                  "description": "Must be a string and is required"
                }
              }
            }
          },
          "AproximateTime": {
            "bsonType": "string",
            "description": "Must be a string and is required"
          },
          "anyRecomendation": {
            "bsonType": "string",
            "description": "Must be a string and is required"
          }
        }
      }
    }
  })
  
 // Creating the compound index
 db.Routines.createIndex(
    { _id: 1, user: 1 }, // Compound index on _id and user
    { name: "id_user_index" } // Optional: index name
  )