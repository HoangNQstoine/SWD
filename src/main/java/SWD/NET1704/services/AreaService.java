package SWD.NET1704.services;

import SWD.NET1704.dtos.response.AreaDTO;

import java.util.List;

public interface AreaService {
    List<AreaDTO> getAllAreas();
    AreaDTO getAreaById(int areaId) throws Exception;
    AreaDTO createArea(AreaDTO areaDTO);
    AreaDTO updateArea(int areaId,AreaDTO areaDTO);
    Boolean deleteArea(int areaId);
}
