package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.AnimalPayload;

public interface AnimalService {

    ResponseEntity<?>getAllAnimal();
    ResponseEntity<?> getAnimal(int animalId) throws Exception;
    ResponseEntity<?> createNewAnimal(AnimalPayload animalPayload);
    ResponseEntity<?> updateAnimal(int animalId,AnimalPayload animalPayload);
    ResponseEntity<?> deleteAnimal(int animalId);
}
