package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.AnimalPayload;
import SWD.NET1704.services.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/zoo-server/api/v1/animal")
@AllArgsConstructor
public class AnimalController {


    private final AnimalService animalService;

    @GetMapping("/getAllAnimal")
    public  ResponseEntity<?> getAllAnimal() {
        return animalService.getAllAnimal();
    }

    @GetMapping("/getAnimalById/{id}")
    public  ResponseEntity<?> getAnimalById(@PathVariable("id") int animalId) throws Exception {
        return animalService.getAnimal(animalId);
    }

    @PostMapping("/createAnimal")
    public ResponseEntity<?> createAnimal(@RequestBody AnimalPayload animalPayload) {
        return animalService.createNewAnimal(animalPayload);
    }

    @PutMapping("/updateAnimal/{id}")
    public  ResponseEntity<?> updateAnimal(@PathVariable("id") int animalId, @RequestBody AnimalPayload animalPayload) {
        return animalService.updateAnimal(animalId, animalPayload);
    }

    @DeleteMapping("/deleteAnimal/{id}")
    public  ResponseEntity<?> deleteAnimal(@PathVariable("id") int animalId) {
        return animalService.deleteAnimal(animalId);
    }
}
