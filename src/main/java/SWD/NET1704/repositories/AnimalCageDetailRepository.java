package SWD.NET1704.repositories;

import SWD.NET1704.entities.AnimalCageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import SWD.NET1704.entities.*;

import java.util.List;

@Repository
public interface AnimalCageDetailRepository  extends JpaRepository<AnimalCageDetail, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.animal_cage_detail a where a.status = true order by animal_cage_detail_id desc",nativeQuery = true)
    List<AnimalCageDetail> getAllByStatusIsTrue();

    @Query(value = "SELECT * FROM swp_zoo_management.animal_cage_detail a where animal_id =:animalId and a.status = true order by animal_cage_detail_id desc",nativeQuery = true)
    List<AnimalCageDetail> getAllByAnimal(int animalId);

    @Query(value = "SELECT * FROM swp_zoo_management.animal_cage_detail a where animal_cage_id =:animal_cage_id and a.status = true and a.use_status='CURRENT USE' order by animal_cage_detail_id desc",nativeQuery = true)
    List<AnimalCageDetail> getAllByCage(int animal_cage_id);
    @Query(value = "select a from AnimalCageDetail  a where a.animalEntity.animalId=:animalId and a.animalCageEntity.animalCageId=:animalCageId and a.useStatus='CURRENT USE' and a.status= true")
    AnimalCageDetail getByAnimalAndCage(int animalId, int animalCageId );

    @Query(value = "SELECT a from AnimalCageDetail a where a.animalEntity.animalId=:animalId and a.status = true and a.useStatus='CURRENT USE'")
    AnimalCageDetail findByAnimalIdCurrentUse(int animalId);
}
