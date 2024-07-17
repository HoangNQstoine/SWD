package SWD.NET1704.controllers;

import SWD.NET1704.dtos.request.AreaManagementPayload;
import SWD.NET1704.services.AreaManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo-server/api/v1/area-management")
@AllArgsConstructor
public class AreaManagementController {
    private final AreaManagementService areaManagementService;

    @GetMapping("/getAllAreaManagementResponses")
    public ResponseEntity<?> getAllAreaManagementResponses() {
        return areaManagementService.getAllAreaManagementResponses();
    }

    @GetMapping("/getAreaManagementResponse/{id}")
    public  ResponseEntity<?> getAreaManagementResponse(@PathVariable("id") int id) throws Exception {
        return areaManagementService.getAreaManagementResponse(id);
    }
    @GetMapping("/getAllByArea/{id}")
    public  ResponseEntity<?> getAllByArea(@PathVariable("id") int areaId) {
        return areaManagementService.getAllByArea(areaId);
    }
    @GetMapping("/getAllByStaff/{id}")
    public  ResponseEntity<?> getAllByStaff(@PathVariable("id") int staffId) {
        return areaManagementService.getAllByStaff(staffId);
    }

    @PostMapping("/createAreaManagement")
    public  ResponseEntity<?> createAreaManagement(@RequestBody AreaManagementPayload payload) {
        return areaManagementService.createAreaManagement(payload);
    }

    @PutMapping("/updateAreaManagement/{id}")
    public  ResponseEntity<?> updateAreaManagement(@PathVariable("id") int id, @RequestBody AreaManagementPayload payload) {
        return areaManagementService.updateAreaManagement(id, payload);
    }
    @PutMapping("/unAssignStaff/{id}")
    public  ResponseEntity<?> unAssignStaff(@PathVariable("id") int id) {
        return areaManagementService.unAssignStaff(id);
    }
    @DeleteMapping("/deleteAreaManagement/{id}")
    public  ResponseEntity<?> deleteAreaManagement(@PathVariable("id") int id) {
        return areaManagementService.deleteAreaManagement(id);
    }
}
