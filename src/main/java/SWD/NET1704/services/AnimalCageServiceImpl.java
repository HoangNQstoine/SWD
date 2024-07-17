package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.AnimalCagePayload;
import SWD.NET1704.dtos.response.AnimalCageDTO;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.entities.AnimalCageEntity;
import SWD.NET1704.entities.AreaEntity;
import SWD.NET1704.repositories.AnimalCageRepository;
import SWD.NET1704.repositories.AreaRepository;
import SWD.NET1704.utils.Constants;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

import static SWD.NET1704.utils.Constants.*;

@AllArgsConstructor
@Service
public class AnimalCageServiceImpl implements AnimalCageService {
    private final AnimalCageRepository animalCageRepository;
    private final AreaRepository areaRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntity dtoToEntity;

    @Override
    public ResponseEntity<?> getAllAnimalCages() {
        try {
            List<AnimalCageEntity> animalCageEntityList = animalCageRepository.getAllByStatusIsTrue();

            if (animalCageEntityList != null) {
                List<AnimalCageDTO> animalCageDTOS =
                        animalCageEntityList.stream().map(entityToDTOMapper::mapAnimalCageEntityToDTO).collect(Collectors.toList());
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, animalCageDTOS));
            } else {
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getAllAnimalCagesNotUse() {
        try {
            List<AnimalCageEntity> animalCageEntityList = animalCageRepository.getAllAnimalCageIsNotUse();
            if (animalCageEntityList == null)
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));
            List<AnimalCageDTO> animalCageDTOS =
                    animalCageEntityList.stream().map(entityToDTOMapper::mapAnimalCageEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, animalCageDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getAnimalCageById(int animalCageId) {
        try {
            if (animalCageRepository.findById(animalCageId).isPresent()) {
                AnimalCageEntity animalCageEntity = animalCageRepository.findById(animalCageId).get();
                AnimalCageDTO animalCageDTO = entityToDTOMapper.mapAnimalCageEntityToDTO(animalCageEntity);
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, animalCageDTO));
            } else {
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> createNewAnimalCage(AnimalCagePayload animalCagePayload) {
        try {
            AnimalCageEntity animalCageEntity = dtoToEntity.mapPayloadToAnimalCageEntity(animalCagePayload);
            animalCageEntity.setStatus(STATUS_TRUE);
            animalCageEntity.setIsUse(STATUS_FALSE);
            if (areaRepository.findById(animalCagePayload.getAreaId()).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));
            AreaEntity areaEntity = areaRepository.getOne(animalCagePayload.getAreaId());
            animalCageEntity.setAreaEntity(areaEntity);
            animalCageRepository.save(animalCageEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, animalCagePayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updateAnimalCage(int animalCageId, AnimalCagePayload animalCagePayload) {
        try {
            if (animalCageRepository.findById(animalCageId).isPresent()) {
                AnimalCageEntity oldAnimalCageEntity = animalCageRepository.findById(animalCageId).get();

                if (areaRepository.findById(animalCagePayload.getAreaId()).isEmpty())
                    return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));

                oldAnimalCageEntity.setAnimalCageName(animalCagePayload.getAnimalCageName());
                oldAnimalCageEntity.setDescription(animalCagePayload.getDescription());
                oldAnimalCageEntity.setMaxQuantity(animalCagePayload.getMaxQuantity());
                AreaEntity areaEntity = areaRepository.getOne(animalCagePayload.getAreaId());
                oldAnimalCageEntity.setAreaEntity(areaEntity);
                animalCageRepository.save(oldAnimalCageEntity);
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, animalCagePayload));
            } else {
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> deleteAnimalCage(int animalCageId) {
        try {
            if (animalCageRepository.findById(animalCageId).isPresent()) {
                AnimalCageEntity animalCageEntity = animalCageRepository.findById(animalCageId).get();
                animalCageEntity.setStatus(false);
                animalCageRepository.save(animalCageEntity);
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.SUCCESS));
            } else {
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, Constants.NOT_FOUND));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }
}
