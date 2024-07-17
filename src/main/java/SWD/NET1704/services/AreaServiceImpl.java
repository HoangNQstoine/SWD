package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.response.AreaDTO;
import SWD.NET1704.entities.AreaEntity;
import SWD.NET1704.repositories.AreaRepository;
import SWD.NET1704.utils.EntityToDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areaRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    @Override
    public List<AreaDTO> getAllAreas() {
        List<AreaEntity> areaEntityList = areaRepository.getAllByStatusIsTrue();

        if (areaEntityList.isEmpty())
            return null;
        return areaEntityList.stream().map(entityToDTOMapper::mapAreaEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public AreaDTO getAreaById(int areaId) throws Exception {
        if (areaRepository.findById(areaId).isPresent()) {
            AreaEntity areaEntity = areaRepository.findById(areaId).get();

            return entityToDTOMapper.mapAreaEntityToDTO(areaEntity);
        }
        return null;
    }

    @Override
    public AreaDTO createArea(AreaDTO areaDTO) {
        try {
            AreaEntity areaEntity = new AreaEntity();
            areaEntity.setAreaName(areaDTO.getAreaName());
            areaEntity.setDescription(areaDTO.getDescription());
            areaEntity.setStatus(true);
            areaRepository.save(areaEntity);
            return areaDTO;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public AreaDTO updateArea(int areaId, AreaDTO areaDTO) {
        if (areaRepository.findById(areaId).isPresent()) {
            AreaEntity areaEntity = areaRepository.findById(areaId).get();
            areaEntity.setAreaName(areaDTO.getAreaName());
            areaEntity.setDescription(areaDTO.getDescription());
            areaRepository.save(areaEntity);
            return areaDTO;
        }
        return null;
    }

    @Override
    public Boolean deleteArea(int areaId) {
        if (areaRepository.findById(areaId).isPresent()) {
            AreaEntity areaEntity = areaRepository.findById(areaId).get();
            areaEntity.setStatus(false);
            areaRepository.save(areaEntity);
            return true;
        }
        return false;
    }
}
