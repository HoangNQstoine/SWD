package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.AnimalDietManagementPayload;
import SWD.NET1704.services.AnimalDietManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo-server/api/v1/animal-diet-management")
@AllArgsConstructor
public class AnimalDietManagementController {

    private final AnimalDietManagementService animalDietManagementService;

    @GetMapping("/getAllAnimalDietManagementResponses")
    public ResponseEntity<?> getAllAnimalDietManagementResponses() {
        return animalDietManagementService.getAllAnimalDietManagementResponses();
    }

    @GetMapping("/getAnimalDietManagementResponse/{id}")
    public  ResponseEntity<?> getAnimalDietManagementResponse(@PathVariable("id") int animalDietManagementId) throws Exception {
        return animalDietManagementService.getAnimalDietManagementResponse(animalDietManagementId);
    }
    @GetMapping("/getAllByAnimalId/{id}")
    public  ResponseEntity<?> getAllByAnimalId(@PathVariable("id") int animalId) throws Exception {
        return animalDietManagementService.getAllByAnimalId(animalId);
    }

    @PostMapping("/createAnimalDietManagement")
    public  ResponseEntity<?> createAnimalDietManagement(@RequestBody AnimalDietManagementPayload animalDietManagementPayload) {
        return animalDietManagementService.createAnimalDietManagement(animalDietManagementPayload);
    }

    @PutMapping("/updateAnimalDietManagement/{id}")
    public  ResponseEntity<?> updateAnimalDietManagement(@PathVariable("id") int animalDietManagementId, @RequestBody AnimalDietManagementPayload animalDietManagementPayload) {
        return animalDietManagementService.updateAnimalDietManagement(animalDietManagementId, animalDietManagementPayload);
    }

    @DeleteMapping("/deleteAnimalDietManagement/{id}")
    public  ResponseEntity<?> deleteAnimalDietManagement(@PathVariable("id") int animalDietManagementId) {
        return animalDietManagementService.deleteAnimalDietManagement(animalDietManagementId);
    }
}
