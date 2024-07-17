package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.DietPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.services.DietService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo-server/api/v1/diet")
@AllArgsConstructor
public class DietController {
    private final DietService dietService;

    @GetMapping("/getAllDiets")
    public ResponseEntity<ApiResponse> getAllDiets() {
        return dietService.getAllDiets();
    }

    @GetMapping("/getDietById/{id}")
    public   ResponseEntity<ApiResponse> getDietById(@PathVariable("id") int dietId) throws Exception {
        return dietService.getDietById(dietId);
    }

    @PostMapping("/createNewDiet")
    public   ResponseEntity<ApiResponse> createNewDiet(@RequestBody DietPayload dietPayload) {
        return dietService.createDiet(dietPayload);
    }

    @PutMapping("/updateDiet/{id}")
    public   ResponseEntity<ApiResponse> updateDiet(@PathVariable("id") int dietId, @RequestBody DietPayload dietPayload) {
        return dietService.updateDiet(dietId, dietPayload);
    }

    @DeleteMapping("/deleteDiet/{id}")
    public   ResponseEntity<ApiResponse> deleteDiet(@PathVariable("id") int dietId) {
        return dietService.deleteDiet(dietId);
    }
}
