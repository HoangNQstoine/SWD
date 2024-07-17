package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.FoodPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.services.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo-server/api/v1/food")
@AllArgsConstructor
public class FoodController {

    private final FoodService foodService;
    @GetMapping("/getAllFoods")
    public ResponseEntity<ApiResponse> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/getAllFoodNotExpired")
    public ResponseEntity<ApiResponse> getAllFoodNotExpired() {
        return foodService.getAllFoodNotExpired();
    }

    @GetMapping("/getFoodById/{id}")
    public  ResponseEntity<ApiResponse> getFoodById(@PathVariable("id") int foodId) throws Exception {
        return foodService.getFoodById(foodId);
    }

    @PostMapping("/createNewFood")
    public  ResponseEntity<ApiResponse> createNewFood(@RequestBody FoodPayload foodPayload) {
        return foodService.createNewFood(foodPayload);
    }

    @PutMapping("/updateFood/{id}")
    public  ResponseEntity<ApiResponse> updateFood(@PathVariable("id") int foodId, @RequestBody FoodPayload foodPayload) {
        return foodService.updateFood(foodId, foodPayload);
    }

    @DeleteMapping("/deleteFood/{id}")
    public  ResponseEntity<ApiResponse> deleteFood(@PathVariable("id") int foodId) {
        return foodService.deleteFood(foodId);
    }
}
