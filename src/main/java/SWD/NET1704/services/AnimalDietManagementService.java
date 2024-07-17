package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.AnimalDietManagementPayload;

public interface AnimalDietManagementService {

    ResponseEntity<?> getAllAnimalDietManagementResponses();
    ResponseEntity<?> getAnimalDietManagementResponse(int animalDietManagementId) throws Exception;
    ResponseEntity<?> getAllByAnimalId(int animalId);
    ResponseEntity<?> createAnimalDietManagement(AnimalDietManagementPayload payload);
    ResponseEntity<?> updateAnimalDietManagement(int animalDietManagementId,AnimalDietManagementPayload payload);
    ResponseEntity<?> deleteAnimalDietManagement(int animalDietManagementId);
}
