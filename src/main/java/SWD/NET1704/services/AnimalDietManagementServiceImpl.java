package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.AnimalDietManagementPayload;
import SWD.NET1704.dtos.response.AnimalDietManagementResponse;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.entities.AnimalDietManagementEntity;
import SWD.NET1704.entities.AnimalEntity;
import SWD.NET1704.entities.DietEntity;
import SWD.NET1704.repositories.AnimalDietManagementRepository;
import SWD.NET1704.repositories.AnimalRepository;
import SWD.NET1704.repositories.DietRepository;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;
import SWD.NET1704.utils.HelperUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static SWD.NET1704.utils.Constants.CURRENT_USE;
import static SWD.NET1704.utils.Constants.NO_LONGER_AVAILABLE;

@Service
@AllArgsConstructor
public class AnimalDietManagementServiceImpl implements AnimalDietManagementService {

    private final AnimalDietManagementRepository animalDietManagementRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntity dtoToEntity;
    private final AnimalRepository animalRepository;
    private final DietRepository dietRepository;
    private final HelperUtil helperUtil;
    @Override
    public ResponseEntity<ApiResponse> getAllAnimalDietManagementResponses() {
        try {
            List<AnimalDietManagementEntity> animalDietManagementEntities = animalDietManagementRepository.getAllByStatusIsTrue();
            if (animalDietManagementEntities == null)
                return ResponseEntity.ok(new ApiResponse(true, "Not found !!!"));

            List<AnimalDietManagementResponse> animalDietManagementResponseList =
                    animalDietManagementEntities.stream().map(entityToDTOMapper::mapEntityToAnimalDietManagementResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "Get Success!!", animalDietManagementResponseList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> getAnimalDietManagementResponse(int animalDietManagementId)  {
        try {
            if (animalDietManagementRepository.findById(animalDietManagementId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(true, "Not found AnimalDietManagementEntity!!!"));
            AnimalDietManagementEntity animalDietManagementEntity = animalDietManagementRepository.findById(animalDietManagementId).get();
            AnimalDietManagementResponse animalDietManagementResponse  = entityToDTOMapper.mapEntityToAnimalDietManagementResponse(animalDietManagementEntity);

            return ResponseEntity.ok(new ApiResponse(true, "Success", animalDietManagementResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<?> getAllByAnimalId(int animalId) {
        try {
            List<AnimalDietManagementEntity> animalDietManagementEntities = animalDietManagementRepository.findByAnimalId(animalId);
            List<AnimalDietManagementResponse> animalDietManagementResponseList = new ArrayList<>();
            if (animalDietManagementEntities == null)
                return ResponseEntity.ok(new ApiResponse(true, "Not found !!!"));
            animalDietManagementEntities.forEach(entity -> {
                AnimalDietManagementResponse response = entityToDTOMapper.mapEntityToAnimalDietManagementResponse(entity);
                animalDietManagementResponseList.add(response);
            });
            return ResponseEntity.ok(new ApiResponse(true, "Get Success!!", animalDietManagementResponseList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> createAnimalDietManagement(AnimalDietManagementPayload payload) {
        try {
            AnimalDietManagementEntity animalDietManagementEntity = dtoToEntity.mapPayloadToAnimalDietManagementEntity(payload);
            String message = helperUtil.validateAnimalDietPayload(payload,animalDietManagementEntity);
            if (message!= null)
                return ResponseEntity.badRequest().body(new ApiResponse(false,message));
            if  (animalDietManagementRepository.findByAnimalIdCurrentUse(payload.getAnimalId())!=null){
                AnimalDietManagementEntity olAnimalDietManagementEntity = animalDietManagementRepository.findByAnimalIdCurrentUse(payload.getAnimalId());
                olAnimalDietManagementEntity.setUseStatus(NO_LONGER_AVAILABLE);
                olAnimalDietManagementEntity.setDateEnd(payload.getDateStart());
                animalDietManagementRepository.save(olAnimalDietManagementEntity);
            }
            animalDietManagementEntity.setStatus(true);
            animalDietManagementEntity.setUseStatus(CURRENT_USE);
            AnimalEntity animalEntity = animalRepository.getOne(payload.getAnimalId());
            DietEntity dietEntity = dietRepository.getOne(payload.getDietId());
            animalDietManagementEntity.setAnimalEntity(animalEntity);
            animalDietManagementEntity.setDietEntity(dietEntity);

            animalDietManagementRepository.save(animalDietManagementEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Create Success!!", payload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updateAnimalDietManagement(int animalDietManagementId, AnimalDietManagementPayload payload) {
        try {
            if (animalDietManagementRepository.findById(animalDietManagementId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Not found AnimalDietManagement!!!"));
            AnimalDietManagementEntity animalDietManagementEntity = animalDietManagementRepository.getOne(animalDietManagementId);

            String message = helperUtil.validateAnimalDietPayload(payload,animalDietManagementEntity);
            if (message!= null)
                return ResponseEntity.badRequest().body(new ApiResponse(false,message));

            AnimalEntity animalEntity = animalRepository.getOne(payload.getAnimalId());
            DietEntity dietEntity = dietRepository.getOne(payload.getDietId());
            animalDietManagementEntity.setAnimalEntity(animalEntity);
            animalDietManagementEntity.setDietEntity(dietEntity);
            animalDietManagementEntity.setAnimalDietManagementName(payload.getAnimalDietManagementName());
            animalDietManagementRepository.save(animalDietManagementEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Update Success!!", payload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> deleteAnimalDietManagement(int animalDietManagementId) {
        try {
            if (animalDietManagementRepository.findById(animalDietManagementId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Not found AnimalDietManagement!!!"));
            AnimalDietManagementEntity animalDietManagementEntity = animalDietManagementRepository.findById(animalDietManagementId).get();
            animalDietManagementEntity.setStatus(false);
            animalDietManagementRepository.save(animalDietManagementEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Delete Success!!"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}
