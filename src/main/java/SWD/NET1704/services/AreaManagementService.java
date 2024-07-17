package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.request.AreaManagementPayload;

public interface AreaManagementService {

    ResponseEntity<?> getAllAreaManagementResponses();

    ResponseEntity<?> getAreaManagementResponse(int areaManagementId) throws Exception;

    ResponseEntity<?> createAreaManagement(AreaManagementPayload payload);

    ResponseEntity<?> updateAreaManagement(int areaManagementId, AreaManagementPayload payload);

    ResponseEntity<?> unAssignStaff(int areaManagementId);

    ResponseEntity<?> getAllByArea(int areaId);

    ResponseEntity<?> getAllByStaff(int userId);

    ResponseEntity<?> deleteAreaManagement(int areaManagementId);
}
