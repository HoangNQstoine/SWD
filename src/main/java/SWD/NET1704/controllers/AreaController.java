package SWD.NET1704.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import SWD.NET1704.dtos.response.AreaDTO;
import SWD.NET1704.services.AreaService;

import java.util.List;

@RestController
@RequestMapping("/zoo-server/api/v1/area")
@AllArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping("/getAllAreas")
    public List<AreaDTO> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("/getAreaById/{id}")
    public AreaDTO getAreaById(@PathVariable("id") int areaId) throws Exception {
        return areaService.getAreaById(areaId);
    }

    @PostMapping("/createNewArea")
    public AreaDTO createNewArea(@RequestBody AreaDTO areaDTO) {
        return areaService.createArea(areaDTO);
    }

    @PutMapping("/updateArea/{id}")
    public AreaDTO updateArea(@PathVariable("id") int areaId, @RequestBody AreaDTO areaDTO) {
        return areaService.updateArea(areaId, areaDTO);
    }

    @DeleteMapping("/deleteArea/{id}")
    public boolean deleteArea(@PathVariable("id") int areaId) {
        return areaService.deleteArea(areaId);
    }
}
