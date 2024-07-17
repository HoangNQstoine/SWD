package SWD.NET1704.services;

import SWD.NET1704.dtos.response.CatalogueDTO;

import java.util.List;

public interface CatalogueService {
    List<CatalogueDTO> getAllCatalogues();
    CatalogueDTO getCatalogueById(int catalogueId) throws Exception;
    CatalogueDTO createCatalogue(CatalogueDTO catalogueDTO);
    CatalogueDTO updateCatalogue(int catalogueId,CatalogueDTO catalogueDTO);
    Boolean deleteCatalogue(int catalogueId);
}
