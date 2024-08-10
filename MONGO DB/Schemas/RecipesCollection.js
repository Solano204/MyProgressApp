db.createCollection("Recipes", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["_id", "user", "Utensils", "Ingredients", "Steps", "AproximateTime", "anyRecomendation"],
        properties: {
          _id: {
            bsonType: "string",
            description: "Must be a string and is required"
          },
          user: {
            bsonType: "string",
            description: "Must be a string and is required"
          },
          Utensils: {
            bsonType: "array",
            description: "Must be an array and is required",
            items: {
              bsonType: "string",
              description: "Each item in the array must be a string"
            }
          },
          Ingredients: {
            bsonType: "object",
            description: "Must be an object and is required",
            properties: {
              avocado: {
                bsonType: "string",
                description: "Must be a string"
              },
              lime: {
                bsonType: "string",
                description: "Must be a string"
              },
              salt: {
                bsonType: "string",
                description: "Must be a string"
              }
            }
          },
          Steps: {
            bsonType: "object",
            description: "Must be an object and is required",
            properties: {
              "1": {
                bsonType: "string",
                description: "Must be a string"
              },
              "2": {
                bsonType: "string",
                description: "Must be a string"
              },
              "3": {
                bsonType: "string",
                description: "Must be a string"
              }
            }
          },
          AproximateTime: {
            bsonType: "string",
            description: "Must be a string and is required"
          },
          anyRecomendation: {
            bsonType: "string",
            description: "Must be a string and is required"
          }
        }
      }
    }
  });


 db.Recipes.createIndex(
    { _id: 1, user: 1 }, // Compound index on _id and user
    { name: "id_user_index" } // Optional: index name
  )