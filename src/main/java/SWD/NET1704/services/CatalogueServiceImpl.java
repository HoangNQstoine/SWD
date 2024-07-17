package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.response.CatalogueDTO;
import SWD.NET1704.entities.CatalogueEntity;
import SWD.NET1704.repositories.CatalogueRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CatalogueServiceImpl implements CatalogueService {
    private final CatalogueRepository catalogueRepository;

    @Override
    public List<CatalogueDTO> getAllCatalogues() {
        List<CatalogueEntity> catalogueEntityList = catalogueRepository.getAllByStatusTrue();
        List<CatalogueDTO> catalogueDTOS = new ArrayList<>();
        if (catalogueEntityList != null) {

            catalogueEntityList.forEach(catalogue -> {
                CatalogueDTO catalogueDTO = new CatalogueDTO(catalogue.getCatalogueId(),
                        catalogue.getCatalogueName(), catalogue.getStatus());
                catalogueDTOS.add(catalogueDTO);
            });
        }
        return catalogueDTOS;
    }

    @Override
    public CatalogueDTO getCatalogueById(int catalogueId) throws Exception {
        CatalogueDTO catalogueDTO = new CatalogueDTO();
        if (catalogueRepository.findById(catalogueId).isPresent()) {
            CatalogueEntity catalogueEntity = catalogueRepository.findById(catalogueId).get();
            catalogueDTO.setCatalogueId(catalogueEntity.getCatalogueId());
            catalogueDTO.setCatalogueName(catalogueEntity.getCatalogueName());
            catalogueDTO.setStatus(catalogueEntity.getStatus());
            return catalogueDTO;
        }
        return catalogueDTO;
    }

    @Override
    public CatalogueDTO createCatalogue(CatalogueDTO catalogueDTO) {
        try {
            CatalogueEntity catalogueEntity = new CatalogueEntity();
            catalogueEntity.setCatalogueName(catalogueDTO.getCatalogueName());
            catalogueEntity.setStatus(true);
            catalogueRepository.save(catalogueEntity);
            return catalogueDTO;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public CatalogueDTO updateCatalogue(int catalogueId, CatalogueDTO catalogueDTO) {
        if (catalogueRepository.findById(catalogueId).isPresent()) {
            CatalogueEntity catalogueEntity = catalogueRepository.findById(catalogueId).get();
            catalogueEntity.setCatalogueName(catalogueDTO.getCatalogueName());
            catalogueRepository.save(catalogueEntity);
            return catalogueDTO;
        }
        return null;
    }

    @Override
    public Boolean deleteCatalogue(int catalogueId) {
        if (catalogueRepository.findById(catalogueId).isPresent()) {
            CatalogueEntity catalogueEntity = catalogueRepository.findById(catalogueId).get();
            catalogueEntity.setStatus(false);
            catalogueRepository.save(catalogueEntity);
            return true;
        }
        return false;
    }
}
