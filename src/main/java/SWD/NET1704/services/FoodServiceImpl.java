package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.FoodPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.FoodDTO;
import SWD.NET1704.entities.FoodEntity;
import SWD.NET1704.repositories.FoodRepository;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntity dtoToEntity;

    @Override
    public ResponseEntity<ApiResponse> getAllFoods() {
        try {
            List<FoodEntity> foodEntityList = foodRepository.getAllByStatusTrue();
            if (foodEntityList == null || foodEntityList.isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found!!!! "));
            List<FoodDTO> foodDTOS = foodEntityList.stream().map(entityToDTOMapper::mapFoodEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ApiResponse(true, "Get All Foods Success ", foodDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<ApiResponse> getAllFoodNotExpired() {
        try {
            List<FoodEntity> foodEntityList = foodRepository.getAllFoodNotExpired();
            if (foodEntityList == null || foodEntityList.isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found!!!! "));
            List<FoodDTO> foodDTOS = new ArrayList<>();
            foodEntityList.forEach(food -> {
                FoodDTO foodDTO = entityToDTOMapper.mapFoodEntityToDTO(food);
                foodDTOS.add(foodDTO);
            });
            return ResponseEntity.ok().body(new ApiResponse(true, "Get All Foods Success ", foodDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getFoodById(int foodId) {
        try {
            if (foodRepository.findById(foodId).isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found!!!! "));
            FoodEntity foodEntity = foodRepository.findById(foodId).get();
            FoodDTO foodDTO = entityToDTOMapper.mapFoodEntityToDTO(foodEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, "getFoodById Success ", foodDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createNewFood(FoodPayload foodPayload) {
        try {
            FoodEntity foodEntity = dtoToEntity.mapPayloadToFoodEntity(foodPayload);
            foodEntity.setStatus(true);
            foodRepository.save(foodEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, "createNewFood Success ", foodPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateFood(int foodId, FoodPayload foodPayload) {
        try {
            if (foodRepository.findById(foodId).isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found!!!! "));

            FoodEntity oldFoodEntity = foodRepository.findById(foodId).get();
            oldFoodEntity.setFoodName(foodPayload.getFoodName());
            oldFoodEntity.setNutriment(foodPayload.getNutriment());
            oldFoodEntity.setUnit(foodPayload.getUnit());
            oldFoodEntity.setDateStart(foodPayload.getDateStart());
            oldFoodEntity.setImage(foodPayload.getImage());
            oldFoodEntity.setDateEnd(foodPayload.getDateEnd());
            oldFoodEntity.setQuantity(foodPayload.getQuantity());
            foodRepository.save(oldFoodEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, "updateFood Success ", foodPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteFood(int foodId) {
        try {
            if (foodRepository.findById(foodId).isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found!!!! "));
            FoodEntity foodEntity = foodRepository.findById(foodId).get();
            foodEntity.setStatus(false);
            foodRepository.save(foodEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, "delete Food Success "));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}
