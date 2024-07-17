package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.AnimalCagePayload;

public interface AnimalCageService {
    ResponseEntity<?> getAllAnimalCages();
    ResponseEntity<?> getAllAnimalCagesNotUse();
    ResponseEntity<?> getAnimalCageById(int animalCageId) throws Exception;
    ResponseEntity<?> createNewAnimalCage(AnimalCagePayload animalCagePayload);
    ResponseEntity<?> updateAnimalCage(int animalCageId,AnimalCagePayload animalCagePayload);
    ResponseEntity<?> deleteAnimalCage(int animalCageId);
}
