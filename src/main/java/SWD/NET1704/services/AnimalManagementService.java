package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.AnimalManagementPayload;

public interface AnimalManagementService {
    ResponseEntity<?> getAllAnimalManagementResponses();

    ResponseEntity<?> getAnimalManagementResponse(int animalManagementId) throws Exception;

    ResponseEntity<?> createAnimalManagement(AnimalManagementPayload payload);

    ResponseEntity<?> updateAnimalManagement(int animalManagementId, AnimalManagementPayload payload);

    ResponseEntity<?> unAssignTrainer(int animalManagementId);

    ResponseEntity<?> getAllByAnimal(int animalId);

    ResponseEntity<?> getAllByTrainer(int userId);

    ResponseEntity<?> deleteAnimalManagement(int animalManagementId);
}
