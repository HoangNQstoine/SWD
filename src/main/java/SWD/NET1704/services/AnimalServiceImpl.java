package SWD.NET1704.services;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.request.AnimalPayload;
import SWD.NET1704.dtos.response.AnimalDTO;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.entities.AnimalEntity;
import SWD.NET1704.entities.CatalogueEntity;
import SWD.NET1704.repositories.AnimalRepository;
import SWD.NET1704.repositories.CatalogueRepository;
import SWD.NET1704.utils.EntityToDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final CatalogueRepository catalogueRepository;
    private final AnimalRepository animalRepository;
    private final EntityToDTOMapper entityToDTOMapper;

    @Override
    public ResponseEntity<?> getAllAnimal() {
        try {
            List<AnimalEntity> animalEntityList = animalRepository.getAllByStatusIsTrue();
            if (animalEntityList == null || animalEntityList.isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Not found any Animal"));
            List<AnimalDTO> animalDTOList = animalEntityList.stream().map(entityToDTOMapper::mapAnimalEntityToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "Get All Animal Success", animalDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getAnimal(int animalId) {
        try {
            if (animalRepository.findById(animalId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Not found  Animal"));
            AnimalEntity animalEntity = animalRepository.findById(animalId).get();
            AnimalDTO animalDTO = entityToDTOMapper.mapAnimalEntityToDTO(animalEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Get Animal Success", animalDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when get Animal"));
        }
    }


    @Override
    public ResponseEntity<?> createNewAnimal(AnimalPayload animalPayload) {
        try {
            if (Strings.isNullOrEmpty(animalPayload.getAnimalName()))
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Animal Name cannot null or empty"));
            if (Strings.isNullOrEmpty(animalPayload.getImage()))
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Image cannot null or empty"));
            if(catalogueRepository.findById(animalPayload.getCatalogueId()).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Not found Catalogue"));
            AnimalEntity animalEntity = new AnimalEntity();
            animalEntity.setAnimalName(animalPayload.getAnimalName());
            animalEntity.setImage(animalPayload.getImage());
            animalEntity.setGender(animalPayload.getGender());
            animalEntity.setCountry(animalPayload.getCountry());
            animalEntity.setIsRare(animalPayload.isRare());
            setCatalogueEntity(animalPayload, animalEntity);
            animalEntity.setStatus(true);
            animalRepository.save(animalEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Create Animal Success", animalPayload));
        } catch (Exception e) {
            e.getMessage();
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when Create Animal"));
        }
    }

    /**
     * @param animalPayload - AnimalPayload
     * @param animalEntity  - AnimalEntity
     */
    private void setCatalogueEntity(AnimalPayload animalPayload, AnimalEntity animalEntity) {

        if (animalPayload.getCatalogueId() != 0 && catalogueRepository.findById(animalPayload.getCatalogueId()).isPresent()) {
            CatalogueEntity catalogueEntity = catalogueRepository.getOne(animalPayload.getCatalogueId());
            animalEntity.setCatalogueEntity(catalogueEntity);
        }
    }

    @Override
    public ResponseEntity<?> updateAnimal(int animalId, AnimalPayload animalPayload) {
        try {
            if (animalRepository.findById(animalId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Not found Animal"));
            if (Strings.isNullOrEmpty(animalPayload.getAnimalName()))
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Animal Name cannot null or empty"));
            if (Strings.isNullOrEmpty(animalPayload.getImage()))
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Image cannot null or empty"));
            if(catalogueRepository.findById(animalPayload.getCatalogueId()).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Not found Catalogue"));
            AnimalEntity oldAnimalEntity = animalRepository.findById(animalId).get();
            oldAnimalEntity.setAnimalName(animalPayload.getAnimalName());
            oldAnimalEntity.setImage(animalPayload.getImage());
            oldAnimalEntity.setGender(animalPayload.getGender());
            oldAnimalEntity.setCountry(animalPayload.getCountry());
            oldAnimalEntity.setIsRare(animalPayload.isRare());
            setCatalogueEntity(animalPayload, oldAnimalEntity);
            animalRepository.save(oldAnimalEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Update Animal Success", animalPayload));
        } catch (Exception e) {
            e.getMessage();
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when Update Animal"));
        }
    }

    @Override
    public ResponseEntity<?> deleteAnimal(int animalId) {
        try {
            if (animalRepository.findById(animalId).isEmpty())
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Not found Animal"));
                AnimalEntity animalEntity = animalRepository.findById(animalId).get();
                animalEntity.setStatus(false);
                animalRepository.save(animalEntity);
                return ResponseEntity.ok(new ApiResponse(true, "Delete Animal Success"));
        } catch (Exception e) {
            e.getMessage();
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error when Delete Animal"));
        }
    }
}
