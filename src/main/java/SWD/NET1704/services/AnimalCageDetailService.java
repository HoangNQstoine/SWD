package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.AnimalCageDetailPayload;


public interface AnimalCageDetailService {
    ResponseEntity<?> getAllAnimalCageDetail();
    ResponseEntity<?> getAnimalCageDetailById(int animalCageDetailId);
    ResponseEntity<?> getAllByAnimal(int animalId);
    ResponseEntity<?> createNewAnimalCageDetail(AnimalCageDetailPayload animalCageDetailPayload);
    ResponseEntity<?> updateAnimalCageDetail(int animalCageDetailId, AnimalCageDetailPayload animalCageDetailPayload);
    ResponseEntity<?> deleteAnimalCageDetail(int animalCageDetailId);
}
