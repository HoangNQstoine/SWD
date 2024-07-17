package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.FoodPayload;
import SWD.NET1704.dtos.response.ApiResponse;

public interface FoodService {

    ResponseEntity<ApiResponse> getAllFoods();
    ResponseEntity<ApiResponse> getAllFoodNotExpired();
    ResponseEntity<ApiResponse> getFoodById(int foodId) throws Exception;
    ResponseEntity<ApiResponse>createNewFood(FoodPayload foodPayload);
    ResponseEntity<ApiResponse> updateFood(int foodId,FoodPayload foodPayload);
    ResponseEntity<ApiResponse> deleteFood(int foodId);
}
