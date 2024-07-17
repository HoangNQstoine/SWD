package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.AreaManagementPayload;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.AreaManagementResponse;
import SWD.NET1704.entities.AreaEntity;
import SWD.NET1704.entities.AreaManagementEntity;
import SWD.NET1704.entities.UserEntity;
import SWD.NET1704.repositories.AreaManagementRepository;
import SWD.NET1704.repositories.AreaRepository;
import SWD.NET1704.repositories.UserRepository;
import SWD.NET1704.utils.EntityToDTOMapper;
import SWD.NET1704.utils.HelperUtil;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static SWD.NET1704.utils.Constants.*;

@Service
@AllArgsConstructor
public class AreaManagementServiceImpl implements AreaManagementService {
    private final AreaManagementRepository areaManagementRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;
    private final HelperUtil helperUtil;



    @Override
    public ResponseEntity<?> getAllAreaManagementResponses() {
        try {
            List<AreaManagementEntity> areaManagementEntities = areaManagementRepository.getAllByStatusIsTrue();
            if (areaManagementEntities == null)
                return ResponseEntity.ok(new ApiResponse(true, "Not found"));
            List<AreaManagementResponse> areaManagementResponses =
                    areaManagementEntities.stream().map(entityToDTOMapper::mapEntityToAreaManagementResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "Get All Success", areaManagementResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get  All AreaManagementEntity"));
        }
    }

    @Override
    public ResponseEntity<?> getAreaManagementResponse(int areaManagementId) throws Exception {
        try {
            if (areaManagementRepository.findById(areaManagementId).isPresent()) {
                AreaManagementEntity areaManagementEntity = areaManagementRepository.findById(areaManagementId).get();
                return ResponseEntity.ok(new ApiResponse(true, "Success", entityToDTOMapper.mapEntityToAreaManagementResponse(areaManagementEntity)));
            } else {
                return ResponseEntity.ok(new ApiResponse(true, "Not found!!!"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get AreaManagementEntity"));
        }
    }

    @Override
    public ResponseEntity<?> createAreaManagement(AreaManagementPayload payload) {
        try {
            AreaManagementEntity areaManagementEntity = new AreaManagementEntity();
            String message = helperUtil.validateAreaManagementPayload(payload, areaManagementEntity);
            if (message != null)
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE,message));
            if ( areaManagementRepository.getAreaManagementEntityByAreaEntity(payload.getAreaId()) != null){
                AreaManagementEntity oldAreaManagementEntity = areaManagementRepository.getAreaManagementEntityByAreaEntity(payload.getAreaId());
                oldAreaManagementEntity.setManaging(false);
                areaManagementRepository.save(oldAreaManagementEntity);
            }
            UserEntity userEntity = userRepository.getOne(payload.getUserId());
            AreaEntity areaEntity = areaRepository.getOne(payload.getAreaId());

            areaManagementEntity.setAreaEntity(areaEntity);
            areaManagementEntity.setUserEntity(userEntity);
            areaManagementEntity.setDateStart(payload.getDateStart());
            areaManagementEntity.setManaging(true);
            areaManagementEntity.setStatus(true);
            areaManagementRepository.save(areaManagementEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, payload));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updateAreaManagement(int areaManagementId, AreaManagementPayload payload) {
        try {
            AreaManagementEntity areaManagementEntity = areaManagementRepository.getOne(areaManagementId);



            String message = helperUtil.validateUpdateAreaManagementPayload(payload,areaManagementEntity);
            if (message != null)
                return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE,message));
            UserEntity userEntity = userRepository.getOne(payload.getUserId());
            AreaEntity areaEntity = areaRepository.getOne(payload.getAreaId());
            areaManagementEntity.setAreaEntity(areaEntity);
            areaManagementEntity.setUserEntity(userEntity);
            areaManagementEntity.setDateStart(payload.getDateStart());
            areaManagementRepository.save(areaManagementEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Update Success", payload));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when Update AnimalManagement"));
        }
    }

    @Override
    public ResponseEntity<?> unAssignStaff(int areaManagementId) {
        try {
            if (areaManagementRepository.findById(areaManagementId).isPresent()) {
                AreaManagementEntity areaManagementEntity = areaManagementRepository.getOne(areaManagementId);
                areaManagementEntity.setManaging(false);
                areaManagementEntity.setDateEnd(new Date(System.currentTimeMillis()));
                areaManagementRepository.save(areaManagementEntity);
            }
            return ResponseEntity.ok(new ApiResponse(true, "unAssignStaff Success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when unAssign Staff ArealManagement"));
        }
    }

    @Override
    public ResponseEntity<?> getAllByArea(int areaId) {
        try {
            List<AreaManagementEntity> areaManagementEntities = areaManagementRepository.getAllByAreaEntity(areaId);
            if (areaManagementEntities == null)
                return ResponseEntity.ok(new ApiResponse(true, "Not found"));
            List<AreaManagementResponse> areaManagementResponses =
                    areaManagementEntities.stream().map(entityToDTOMapper::mapEntityToAreaManagementResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "Get All Success", areaManagementResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get  All AreaManagementEntity"));
        }
    }

    @Override
    public ResponseEntity<?> getAllByStaff(int userId) {
        try {
            List<AreaManagementEntity> areaManagementEntities = areaManagementRepository.getAllByUserEntity(userId);
            if (areaManagementEntities == null)
                return ResponseEntity.ok(new ApiResponse(true, "Not found"));
            List<AreaManagementResponse> areaManagementResponses =
                    areaManagementEntities.stream().map(entityToDTOMapper::mapEntityToAreaManagementResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "Get All Success", areaManagementResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get  All AreaManagementEntity"));
        }
    }

    @Override
    public ResponseEntity<?> deleteAreaManagement(int areaManagementId) {
        try {
            if (areaManagementRepository.findById(areaManagementId).isPresent()) {
                AreaManagementEntity areaManagementEntity = areaManagementRepository.getOne(areaManagementId);
                areaManagementEntity.setStatus(false);
                areaManagementRepository.save(areaManagementEntity);
            }
            return ResponseEntity.ok(new ApiResponse(true, "Delete Success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when Delete AreaManagement"));
        }
    }
}
