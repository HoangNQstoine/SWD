package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.DietPayload;
import SWD.NET1704.dtos.response.ApiResponse;

public interface DietService {
    ResponseEntity<ApiResponse> getAllDiets();
    ResponseEntity<ApiResponse> getDietById(int dietId) throws Exception;
    ResponseEntity<ApiResponse> createDiet(DietPayload dietPayload);
    ResponseEntity<ApiResponse> updateDiet(int dietId,DietPayload dietPayload);
    ResponseEntity<ApiResponse> deleteDiet(int dietId);
}
