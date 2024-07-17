package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.AnimalManagementPayload;
import SWD.NET1704.services.AnimalManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo-server/api/v1/animal-management")
@AllArgsConstructor
public class AnimalManagementController {

    private final AnimalManagementService animalManagementService;

    @GetMapping("/getAllAnimalManagementResponses")
    public ResponseEntity<?> getAllAnimalManagementResponses() {
        return animalManagementService.getAllAnimalManagementResponses();
    }

    @GetMapping("/getAnimalManagementResponse/{id}")
    public  ResponseEntity<?> getAnimalManagementResponse(@PathVariable("id") int animalManagementId) throws Exception {
        return animalManagementService.getAnimalManagementResponse(animalManagementId);
    }
    @GetMapping("/getAllByAnimal/{id}")
    public  ResponseEntity<?> getAllByAnimal(@PathVariable("id") int animalId) {
        return animalManagementService.getAllByAnimal(animalId);
    }
    @GetMapping("/getAllByTrainer/{id}")
    public  ResponseEntity<?> getAllByTrainer(@PathVariable("id") int userId) {
        return animalManagementService.getAllByTrainer(userId);
    }

    @PostMapping("/createAnimalManagement")
    public  ResponseEntity<?> createAnimalManagement(@RequestBody AnimalManagementPayload animalManagementPayload) {
        return animalManagementService.createAnimalManagement(animalManagementPayload);
    }

    @PutMapping("/updateAnimalManagement/{id}")
    public  ResponseEntity<?> updateAnimalManagement(@PathVariable("id") int animalManagementId, @RequestBody AnimalManagementPayload animalManagementPayload) {
        return animalManagementService.updateAnimalManagement(animalManagementId, animalManagementPayload);
    }
    @PutMapping("/unAssignTrainer/{id}")
    public  ResponseEntity<?> unAssignTrainer(@PathVariable("id") int animalManagementId) {
        return animalManagementService.unAssignTrainer(animalManagementId);
    }
    @DeleteMapping("/deleteAnimalManagement/{id}")
    public  ResponseEntity<?> deleteAnimalManagement(@PathVariable("id") int animalManagementId) {
        return animalManagementService.deleteAnimalManagement(animalManagementId);
    }
}
