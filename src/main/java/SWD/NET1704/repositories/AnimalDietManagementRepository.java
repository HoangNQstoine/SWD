package SWD.NET1704.repositories;


import SWD.NET1704.entities.AnimalDietManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AnimalDietManagementRepository extends JpaRepository<AnimalDietManagementEntity, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.animal_diet_management a where a.status = true",nativeQuery = true)
    List<AnimalDietManagementEntity> getAllByStatusIsTrue();

    @Query(value = "SELECT a from AnimalDietManagementEntity a where a.animalEntity.animalId=:animalId and a.status = true ORDER BY a.animalDietManagementId DESC")
    List<AnimalDietManagementEntity> findByAnimalId(int animalId);

    @Query(value = "SELECT a from AnimalDietManagementEntity a where a.animalEntity.animalId=:animalId and a.status = true and a.useStatus='CURRENT USE'")
    AnimalDietManagementEntity findByAnimalIdCurrentUse(int animalId);
    @Query(value = "SELECT a from AnimalDietManagementEntity a where a.animalEntity.animalId=:animalId and a.status = true and ((:startDate < a.dateEnd) and (:endDate > a.dateStart))")
    List<AnimalDietManagementEntity> findByAnimalOverlap(int animalId, Date startDate, Date endDate);
}
