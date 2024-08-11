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
    { nameRecipe: 1, user: 1 },
    { unique: true }
)

  db.Recipes.insertOne({
    nameRecipe: 'Gsscamole',
    user: 'younowjs33',
    Utensils: [ 'bowl', 'fork' ],
    Ingredients: { avocado: '2', lime: '1', salt: 'to taste' },
    Steps: {
      '1': 'Mash the avocados.',
      '2': 'Add lime and salt.',
      '3': 'Mix well.'
    },
    AproximateTime: '15 minutes',
    anyRecomendation: 'Serve with tortilla chips.',
    _class: 'com.example.myprogress.app.Entites.Recipe'
  })