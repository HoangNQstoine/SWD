package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.AnimalManagementPayload;
import SWD.NET1704.dtos.response.AnimalManagementResponse;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.entities.AnimalEntity;
import SWD.NET1704.entities.AnimalManagementEntity;
import SWD.NET1704.entities.UserEntity;
import SWD.NET1704.repositories.AnimalManagementRepository;
import SWD.NET1704.repositories.AnimalRepository;
import SWD.NET1704.repositories.UserRepository;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;
import SWD.NET1704.utils.HelperUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static SWD.NET1704.utils.Constants.*;

@Service
@AllArgsConstructor
public class AnimalManagementServiceImpl implements AnimalManagementService {
    private final AnimalManagementRepository animalManagementRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntity dtoToEntity;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final HelperUtil helperUtil;

    @Override
    public ResponseEntity<?> getAllAnimalManagementResponses() {
        try {
            List<AnimalManagementEntity> animalManagementEntities = animalManagementRepository.getAllByStatusIsTrue();
            if (animalManagementEntities == null)
                return ResponseEntity.ok(new ApiResponse(true, "Not found"));
            List<AnimalManagementResponse> animalManagementResponses =
                    animalManagementEntities.stream().map(entityToDTOMapper::mapEntityToAnimalManagementResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "Get All Success", animalManagementResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get  All AnimalManagement"));
        }
    }

    @Override
    public ResponseEntity<?> getAnimalManagementResponse(int animalManagementId) {
        try {
            if (animalManagementRepository.findById(animalManagementId).isPresent()) {
                AnimalManagementEntity animalManagementEntity = animalManagementRepository.findById(animalManagementId).get();
                return ResponseEntity.ok(new ApiResponse(true, "Success", entityToDTOMapper.mapEntityToAnimalManagementResponse(animalManagementEntity)));
            } else {
                return ResponseEntity.ok(new ApiResponse(true, "Not found!!!"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get AnimalManagement"));
        }
    }

    @Override
    public ResponseEntity<?> createAnimalManagement(AnimalManagementPayload payload) {
        try {
            AnimalManagementEntity animalManagementEntity = dtoToEntity.mapPayloadToAnimalManagementEntity(payload);

            String message = helperUtil.validateAnimalManagementPayload(payload,animalManagementEntity);
            if (message != null)
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE,message));

            if (animalManagementRepository.getHistoryByAnimal(payload.getAnimalId()) != null){
                AnimalManagementEntity oldAnimalManagementEntity = animalManagementRepository.getHistoryByAnimal(payload.getAnimalId());
                oldAnimalManagementEntity.setDateEnd(payload.getDateStart());
                oldAnimalManagementEntity.setAssignStatus(UN_ASSIGN);
                animalManagementRepository.save(oldAnimalManagementEntity);
            }
            UserEntity userEntity = userRepository.getOne(payload.getUserId());
            animalManagementEntity.setStatus(true);
            AnimalEntity animalEntity = animalRepository.getOne(payload.getAnimalId());
            animalManagementEntity.setAnimalEntity(animalEntity);
            animalManagementEntity.setUserEntity(userEntity);
            animalManagementEntity.setDateStart(payload.getDateStart());
            animalManagementEntity.setTrainingDescription(payload.getDescription());
            animalManagementEntity.setAssignStatus(ASSIGNED);
            animalManagementRepository.save(animalManagementEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, payload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updateAnimalManagement(int animalManagementId, AnimalManagementPayload payload) {
        try {
            AnimalManagementEntity oldAnimalManagementEntity = animalManagementRepository.getOne(animalManagementId);

           String message = helperUtil.validateUpdateAnimalManagementPayload(payload,oldAnimalManagementEntity);
           if (message != null)
               return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE,message));

            UserEntity userEntity = userRepository.getOne(payload.getUserId());
            AnimalEntity animalEntity = animalRepository.getOne(payload.getAnimalId());
            oldAnimalManagementEntity.setAnimalEntity(animalEntity);
            oldAnimalManagementEntity.setUserEntity(userEntity);
            oldAnimalManagementEntity.setTrainingDescription(payload.getDescription());
            oldAnimalManagementEntity.setDateStart(payload.getDateStart());

            animalManagementRepository.save(oldAnimalManagementEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Update Success", payload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when Update AnimalManagement"));
        }
    }

    @Override
    public ResponseEntity<?> unAssignTrainer(int animalManagementId) {
        try {
            if (animalManagementRepository.findById(animalManagementId).isPresent()) {
                AnimalManagementEntity oldAnimalManagementEntity = animalManagementRepository.getOne(animalManagementId);
                oldAnimalManagementEntity.setAssignStatus(UN_ASSIGN);
                oldAnimalManagementEntity.setDateEnd(new Date(System.currentTimeMillis()));
                animalManagementRepository.save(oldAnimalManagementEntity);
            }
            return ResponseEntity.ok(new ApiResponse(true, "unAssignTrainer Success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when unAssignTrainer AnimalManagement"));
        }
    }

    @Override
    public ResponseEntity<?> getAllByAnimal(int animalId) {
        try {
            List<AnimalManagementEntity> animalManagementEntities = animalManagementRepository.getAllByAnimal(animalId);
            List<AnimalManagementResponse> animalManagementResponses = new ArrayList<>();
            if (animalManagementEntities != null) {
                animalManagementEntities.forEach(entity -> {
                    AnimalManagementResponse response = entityToDTOMapper.mapEntityToAnimalManagementResponse(entity);
                    animalManagementResponses.add(response);
                });
            }
            return ResponseEntity.ok(new ApiResponse(true, "Get Success", animalManagementResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get AnimalManagement"));
        }
    }

    @Override
    public ResponseEntity<?> getAllByTrainer(int userId) {
        try {
            List<AnimalManagementEntity> animalManagementEntities = animalManagementRepository.getAllByTrainer(userId);
            if (animalManagementEntities == null)
                return ResponseEntity.badRequest().body(new ApiResponse(false, NOT_FOUND));
            List<AnimalManagementResponse> animalManagementResponses =
                    animalManagementEntities.stream().map(entityToDTOMapper::mapEntityToAnimalManagementResponse).collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "Get Success", animalManagementResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get AnimalManagement"));
        }
    }

    @Override
    public ResponseEntity<?> deleteAnimalManagement(int animalManagementId) {
        try {
            if (animalManagementRepository.findById(animalManagementId).isPresent()) {
                AnimalManagementEntity oldAnimalManagementEntity = animalManagementRepository.getOne(animalManagementId);
                oldAnimalManagementEntity.setStatus(false);
                animalManagementRepository.save(oldAnimalManagementEntity);
            }
            return ResponseEntity.ok(new ApiResponse(true, "Delete Success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when Delete AnimalManagement"));
        }
    }
}
