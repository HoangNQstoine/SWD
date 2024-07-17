package SWD.NET1704.controllers;

import SWD.NET1704.dtos.response.CatalogueDTO;
import SWD.NET1704.services.CatalogueService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zoo-server/api/v1/catalogue")
@AllArgsConstructor
public class CatalogueController {

    private final CatalogueService catalogueService;

    @GetMapping("/getAllCatalogues")
    public List<CatalogueDTO> getAllCatalogues() {
        return catalogueService.getAllCatalogues();
    }

    @GetMapping("/getCatalogueById/{id}")
    public CatalogueDTO getCatalogueById(@PathVariable("id") int catalogueId) throws Exception {
        return catalogueService.getCatalogueById(catalogueId);
    }

    @PostMapping("/createNewCatalogue")
    public CatalogueDTO createNewCatalogue(@RequestBody CatalogueDTO catalogueDTO) {
        return catalogueService.createCatalogue(catalogueDTO);
    }

    @PutMapping("/updateCatalogue/{id}")
    public CatalogueDTO updateCatalogue(@PathVariable("id") int catalogueId, @RequestBody CatalogueDTO catalogueDTO) {
        return catalogueService.updateCatalogue(catalogueId, catalogueDTO);
    }

    @DeleteMapping("/deleteCatalogue/{id}")
    public boolean deleteCatalogue(@PathVariable("id") int catalogueId) {
        return catalogueService.deleteCatalogue(catalogueId);
    }
}
