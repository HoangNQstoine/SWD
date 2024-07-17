package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.AnimalCagePayload;
import SWD.NET1704.services.AnimalCageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/zoo-server/api/v1/animalCage")
@AllArgsConstructor
public class AnimalCageController {
    private final AnimalCageService animalCageService;
    @GetMapping("/getAllAnimalCage")
    public ResponseEntity<?> getAllAnimalCage() {
        return animalCageService.getAllAnimalCages();
    }

    @GetMapping("/getAllAnimalCagesNotUse")
    public ResponseEntity<?> getAllAnimalCagesNotUse() {
        return animalCageService.getAllAnimalCagesNotUse();
    }

    @GetMapping("/getAnimalCage/{id}")
    public ResponseEntity<?> getAnimalCage(@PathVariable("id") int animalCageId) throws Exception {
        return animalCageService.getAnimalCageById(animalCageId);
    }

    @PostMapping("/createNewAnimalCage")
    public ResponseEntity<?> createNewAnimalCage(@RequestBody AnimalCagePayload animalCagePayload) {
        return animalCageService.createNewAnimalCage(animalCagePayload);
    }

    @PutMapping("/updateAnimalCage/{id}")
    public ResponseEntity<?> updateAnimalCage(@PathVariable("id") int animalCageId, @RequestBody AnimalCagePayload animalCagePayload) {
        return animalCageService.updateAnimalCage(animalCageId, animalCagePayload);
    }

    @DeleteMapping("/deleteAnimalCage/{id}")
    public ResponseEntity<?> deleteAnimalCage(@PathVariable("id") int animalCageId) {
        return animalCageService.deleteAnimalCage(animalCageId);
    }
}
