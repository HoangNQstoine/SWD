package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.AnimalCageDetailPayload;
import SWD.NET1704.services.AnimalCageDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/zoo-server/api/v1/AnimalCageDetail")
@AllArgsConstructor
public class AnimalCageDetailController {
    private final AnimalCageDetailService animalCageDetailService;


    @GetMapping("/getAllAnimalCageDetail")
    public ResponseEntity<?> getAllAnimalCageDetail() {
        return animalCageDetailService.getAllAnimalCageDetail();
    }

    @GetMapping("/getAnimalCageDetailById/{id}")
    public ResponseEntity<?> getAnimalCageDetailById(@PathVariable("id") int animalCageDetailId) {
        return animalCageDetailService.getAnimalCageDetailById(animalCageDetailId);
    }
    @GetMapping("/getAllByAnimal/{id}")
    public ResponseEntity<?> getAllByAnimal(@PathVariable("id") int animalId) {
        return animalCageDetailService.getAllByAnimal(animalId);
    }
    @PostMapping("/createNewAnimalCageDetail")
    public ResponseEntity<?> createNewAnimalCageDetail(@RequestBody AnimalCageDetailPayload animalCageDetailPayload) {
        return animalCageDetailService.createNewAnimalCageDetail(animalCageDetailPayload);
    }

    @PutMapping("/updateAnimalCageDetail/{id}")
    public ResponseEntity<?> updateAnimalCageDetail(@PathVariable("id") int animalCageDetailId, @RequestBody AnimalCageDetailPayload animalCageDetailPayload) {
        return animalCageDetailService.updateAnimalCageDetail(animalCageDetailId, animalCageDetailPayload);
    }

    @DeleteMapping("/deleteAnimalCageDetail/{id}")
    public ResponseEntity<?> deleteAnimalCageDetail(@PathVariable("id") int animalCageDetailId) {
        return animalCageDetailService.deleteAnimalCageDetail(animalCageDetailId);
    }
}
