package SWD.NET1704.services;

import SWD.NET1704.entities.AnimalCageDetail;
import SWD.NET1704.entities.AnimalCageEntity;
import SWD.NET1704.entities.AnimalEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.AnimalCageDetailPayload;
import SWD.NET1704.dtos.response.AnimalCageDetailResponse;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.entities.*;
import SWD.NET1704.repositories.AnimalCageDetailRepository;
import SWD.NET1704.repositories.AnimalCageRepository;
import SWD.NET1704.repositories.AnimalRepository;
import SWD.NET1704.utils.DTOToEntity;
import SWD.NET1704.utils.EntityToDTOMapper;
import SWD.NET1704.utils.HelperUtil;

import java.util.List;
import java.util.stream.Collectors;

import static SWD.NET1704.utils.Constants.*;

@Service
@AllArgsConstructor
public class AnimalCageDetailServiceImpl implements AnimalCageDetailService {
    private final AnimalRepository animalRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final AnimalCageRepository animalCageRepository;
    private final DTOToEntity dtoToEntity;
    private final AnimalCageDetailRepository animalCageDetailRepository;
    private final HelperUtil helperUtil;

    @Override
    public ResponseEntity<?> getAllAnimalCageDetail() {
        try {
            List<AnimalCageDetail> animalCageDetailList = animalCageDetailRepository.getAllByStatusIsTrue();

            if (animalCageDetailList.isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));

            List<AnimalCageDetailResponse> animalCageDetailResponses =
                    animalCageDetailList.stream().map(entityToDTOMapper::mapEntityToAnimalCageDetailResponse).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, animalCageDetailResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<?> getAnimalCageDetailById(int animalCageDetailId) {
        try {
            new AnimalCageDetailResponse();
            if (animalCageDetailRepository.findById(animalCageDetailId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            AnimalCageDetail animalCageDetail = animalCageDetailRepository.findById(animalCageDetailId).get();
            AnimalCageDetailResponse animalCageDetailResponse = entityToDTOMapper.mapEntityToAnimalCageDetailResponse(animalCageDetail);
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, animalCageDetailResponse));
        } catch (Exception e) {
            e.getMessage();
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getAllByAnimal(int animalId) {
        try {
            List<AnimalCageDetail> animalCageDetailList = animalCageDetailRepository.getAllByAnimal(animalId);

            if (animalCageDetailList.isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            List<AnimalCageDetailResponse> animalCageDetailResponses =
                    animalCageDetailList.stream().map(entityToDTOMapper::mapEntityToAnimalCageDetailResponse).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, animalCageDetailResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> createNewAnimalCageDetail(AnimalCageDetailPayload animalCageDetailPayload) {
        try {
            AnimalCageDetail animalCageDetail = dtoToEntity.mapPayloadToAnimalCageDetail(animalCageDetailPayload);
            String message = helperUtil.validateAnimalCageDetailPayload(animalCageDetailPayload,animalCageDetail);
            if(message != null){
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, message));
            }
            if (animalCageDetailRepository.getByAnimalAndCage(animalCageDetailPayload.getAnimalId(),animalCageDetailPayload.getAnimalCageId()) != null)
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, "Animal is current  in this Cage cannot assign again"));

            if (animalCageRepository.getOne(animalCageDetailPayload.getAnimalCageId()).getMaxQuantity() <= animalCageDetailRepository.getAllByCage(animalCageDetailPayload.getAnimalCageId()).size())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, ANIMAL_CAGE_FULL_ERROR));
            if  (animalCageDetailRepository.findByAnimalIdCurrentUse(animalCageDetailPayload.getAnimalId())!=null){
                AnimalCageDetail oldAnimalCageDetail = animalCageDetailRepository.findByAnimalIdCurrentUse(animalCageDetailPayload.getAnimalId());
                oldAnimalCageDetail.setUseStatus(NO_LONGER_AVAILABLE);
                oldAnimalCageDetail.setDateEnd(animalCageDetailPayload.getDateStart());
                animalCageDetailRepository.save(oldAnimalCageDetail);
            }
            AnimalCageEntity animalCageEntity = animalCageRepository.getOne(animalCageDetailPayload.getAnimalCageId());
            AnimalEntity animalEntity = animalRepository.getOne(animalCageDetailPayload.getAnimalId());
            animalCageDetail.setStatus(STATUS_TRUE);
            animalCageDetail.setAnimalEntity(animalEntity);
            animalCageDetail.setAnimalCageEntity(animalCageEntity);
            animalCageDetail.setUseStatus(CURRENT_USE);

            animalCageEntity.setIsUse(STATUS_TRUE);
            animalCageRepository.save(animalCageEntity);
            animalCageDetailRepository.save(animalCageDetail);
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, animalCageDetailPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updateAnimalCageDetail(int animalCageDetailId, AnimalCageDetailPayload animalCageDetailPayload) {
        try {
            if (animalCageDetailRepository.findById(animalCageDetailId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            AnimalCageDetail animalCageDetail = animalCageDetailRepository.getOne(animalCageDetailId);
            String message = helperUtil.validateAnimalCageDetailPayload(animalCageDetailPayload,animalCageDetail);
            if(message != null){
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, message));
            }
          if  (animalCageDetail.getAnimalCageEntity().getAnimalCageId() != animalCageDetailPayload.getAnimalCageId()){
              if (animalCageRepository.getOne(animalCageDetailPayload.getAnimalCageId()).getMaxQuantity() <= animalCageDetailRepository.getAllByCage(animalCageDetailPayload.getAnimalCageId()).size())
                  return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, ANIMAL_CAGE_FULL_ERROR));
          }

            AnimalCageEntity animalCageEntity = animalCageRepository.getOne(animalCageDetailPayload.getAnimalCageId());

            animalCageEntity.setIsUse(STATUS_TRUE);
            animalCageRepository.save(animalCageEntity);
            animalCageDetail.setAnimalCageEntity(animalCageEntity);
            AnimalCageEntity currentAnimalCage = animalCageDetail.getAnimalCageEntity();
            currentAnimalCage.setIsUse(STATUS_FALSE);
            animalCageRepository.save(currentAnimalCage);
            AnimalEntity animalEntity = animalRepository.getOne(animalCageDetailPayload.getAnimalId());
            animalCageDetail.setAnimalEntity(animalEntity);
            animalCageDetail.setDateStart(animalCageDetailPayload.getDateStart());
            animalCageDetail.setAnimalCageDetailName(animalCageDetailPayload.getAnimalCageDetailName());
            animalCageDetailRepository.save(animalCageDetail);
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS, animalCageDetailPayload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> deleteAnimalCageDetail(int animalCageDetailId) {
        try {
            if (animalCageDetailRepository.findById(animalCageDetailId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, NOT_FOUND));
            AnimalCageDetail animalCageDetail = animalCageDetailRepository.findById(animalCageDetailId).get();
            animalCageDetail.setStatus(STATUS_FALSE);
            animalCageDetailRepository.save(animalCageDetail);
            return ResponseEntity.ok().body(new ApiResponse(STATUS_TRUE, SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }
}
