package SWD.NET1704.services;

import SWD.NET1704.entities.DietEntity;
import SWD.NET1704.entities.DietFoodDetailEntity;
import SWD.NET1704.entities.FoodEntity;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.DietPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.DietDTO;
import SWD.NET1704.entities.*;
import SWD.NET1704.repositories.DietFoodDetailRepository;
import SWD.NET1704.repositories.DietRepository;
import SWD.NET1704.repositories.FoodRepository;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DietServiceImpl implements DietService {
    private final DietRepository dietRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntity dtoToEntity;
    private final FoodRepository foodRepository;
    private final DietFoodDetailRepository dietFoodDetailRepository;

    @Override
    public ResponseEntity<ApiResponse> getAllDiets() {
        try {
            List<DietEntity> dietEntityList = dietRepository.getAllByStatusTrue();
            if (dietEntityList == null || dietEntityList.isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found any Diet!!!"));
            List<DietDTO> dietDTOS = dietEntityList.stream().map(entityToDTOMapper::mapDietEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ApiResponse(true, "getAllDiets Success", dietDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getDietById(int dietId) {
        try {
            if (dietRepository.findById(dietId).isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found Diet!!!"));
                DietEntity dietEntity = dietRepository.findById(dietId).get();
                DietDTO dietDTO = new DietDTO();
                dietDTO.setDietId(dietEntity.getDietId());
                dietDTO.setDietName(dietEntity.getDietName());
                dietDTO.setStatus(dietEntity.getStatus());
            return ResponseEntity.ok().body(new ApiResponse(true, "getDietById Success", dietDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createDiet(DietPayload dietPayload) {
        try {
            if (Strings.isNullOrEmpty(dietPayload.getDietName()))
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Diet name cannot null or empty"));
            DietEntity dietEntity = new DietEntity();
            dietEntity.setDietName(dietPayload.getDietName());
            dietEntity.setStatus(true);
            dietRepository.saveAndFlush(dietEntity);
            saveDietFoodDetail(dietPayload,dietEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, "createDiet Success", dietPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
    private void  saveDietFoodDetail(DietPayload dietPayload, DietEntity dietEntity) {

        dietPayload.getDietFoodRequests().forEach(foodRequest -> {
            FoodEntity foodEntity = foodRepository.getOne(foodRequest.getFoodId());
            if (foodEntity.getQuantity() >= foodRequest.getQuantity()) {
                int quantity =foodEntity.getQuantity() - foodRequest.getQuantity();
                foodEntity.setQuantity(quantity);
                foodRepository.save(foodEntity);
                DietFoodDetailEntity dietFoodDetailEntity = new DietFoodDetailEntity();
                dietFoodDetailEntity.setQuantity(foodRequest.getQuantity());
                dietFoodDetailEntity.setFoodEntity(foodEntity);
                dietFoodDetailEntity.setDietEntity(dietEntity);
                dietFoodDetailRepository.save(dietFoodDetailEntity);
            }
        });
    }
    @Override
    public ResponseEntity<ApiResponse> updateDiet(int dietId, DietPayload dietPayload) {
        try {
            if (Strings.isNullOrEmpty(dietPayload.getDietName()))
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Diet name cannot null or empty"));

            if (dietRepository.findById(dietId).isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found Diet!!!"));
            DietEntity dietEntity = dietRepository.findById(dietId).get();
            dietEntity.setDietName(dietPayload.getDietName());
            dietRepository.save(dietEntity);
            saveDietFoodDetail(dietPayload,dietEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, "updateDiet Success", dietPayload));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<ApiResponse> deleteDiet(int dietId) {
        try {
            if (dietRepository.findById(dietId).isEmpty())
                return ResponseEntity.ok().body(new ApiResponse(true, "Not found Diet!!!"));
            DietEntity dietEntity = dietRepository.findById(dietId).get();
            dietEntity.setStatus(false);
            dietRepository.save(dietEntity);
            return ResponseEntity.ok().body(new ApiResponse(true, "deleteDiet Success"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}
