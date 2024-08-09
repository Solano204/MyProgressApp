//Validations in exercise schema
db.createCollection("ExercisesLegs", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });

  db.ExercisesLegs.createIndex({ "name": 1 }, { unique: true });
/**
   Example :  
  {
    name : "Elevación de Cadera",
    description : "Un ejercicio que trabaja los glúteos e isquiotibiales.",
    muscleGroups : ["Glúteos", "Isquiotibiales"]
}
 * 
 */

    


  db.createCollection("ExercisesTriceps", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });
  db.ExercisesTriceps.createIndex({ "name": 1 }, { unique: true });


  db.createCollection("ExercisesChest", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });
  db.ExercisesChest.createIndex({ "name": 1 }, { unique: true });



  db.createCollection("ExercisesBack", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });
  db.ExercisesBack.createIndex({ "name": 1 }, { unique: true });



  db.createCollection("ExercisesShoulders", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });
  db.ExercisesShoulders.createIndex({ "name": 1 }, { unique: true });



  db.createCollection("ExercisesBiceps", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });

  db.ExercisesBiceps.createIndex({ "name": 1 }, { unique: true });


  db.createCollection("ExercisesAbs", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });
  db.ExercisesAbs.createIndex({ "name": 1 }, { unique: true });


  db.createCollection("ExercisesTrapezoids", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });
  db.ExercisesTrapezoids.createIndex({ "name": 1 }, { unique: true });


  db.createCollection("ExercisesForeams", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });
  db.ExercisesForeams.createIndex({ "name": 1 }, { unique: true });

  

    db.createCollection("ExercisesGlutes", {
        validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["name", "description", "muscleGroups"],
            properties: {
            name: {
                bsonType: "string",
                description: "must be a string and is required"
            },
            description: {
                bsonType: "string",
                description: "must be a string and is required"
            },
            muscleGroups: {
                bsonType: "array",
                items: {
                bsonType: "string"
                },
                description: "must be an array of strings and is required"
            }
            }
        }
        }
    });


  db.createCollection("ExercisesCalf", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: ["name", "description", "muscleGroups"],
        properties: {
          name: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          description: {
            bsonType: "string",
            description: "must be a string and is required"
          },
          muscleGroups: {
            bsonType: "array",
            items: {
              bsonType: "string"
            },
            description: "must be an array of strings and is required"
          }
        }
      }
    }
  });
  db.ExercisesCalf.createIndex({ "name": 1 }, { unique: true });













