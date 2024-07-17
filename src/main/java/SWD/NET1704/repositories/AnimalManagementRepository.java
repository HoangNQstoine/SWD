package SWD.NET1704.repositories;

import SWD.NET1704.entities.AnimalManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AnimalManagementRepository extends JpaRepository<AnimalManagementEntity, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.animal_management a where a.status = true order by animal_management_id desc",nativeQuery = true)
    List<AnimalManagementEntity> getAllByStatusIsTrue();
    @Query(value = "SELECT * FROM swp_zoo_management.animal_management a where animal_id =:animalId and a.status = true order by animal_management_id desc",nativeQuery = true)
    List<AnimalManagementEntity> getAllByAnimal(int animalId);

    @Query(value = "SELECT * FROM swp_zoo_management.animal_management a where user_id =:userId and a.status = true order by animal_management_id desc",nativeQuery = true)
    List<AnimalManagementEntity> getAllByTrainer(int userId);
    @Query(value = "SELECT a FROM AnimalManagementEntity a where a.animalEntity.animalId =:animalId and a.status = true and :dateStart < a.dateEnd and a.assignStatus='Assigned'")
    List<AnimalManagementEntity> checkAnimalOverlapTime(int animalId, Date dateStart);

    @Query(value = "SELECT * FROM swp_zoo_management.animal_management a where a.user_id =:userId and a.status = true and ((:dateStart < a.date_end) and (:dateEnd > a.date_start))",nativeQuery = true)
    List<AnimalManagementEntity> checkTrainerOverlapTime(int userId, Date dateStart, Date dateEnd);

    @Query(value = "SELECT a FROM AnimalManagementEntity a where a.userEntity.userId=:userId and  a.status = true and a.assignStatus='Assigned'")
    List<AnimalManagementEntity> getAssignByTrainer(int userId);


    @Query(value = "SELECT a FROM AnimalManagementEntity a where a.userEntity.userId=:userId and a.animalEntity.animalId=:animalId and  a.status = true and a.assignStatus='Assigned'")
    AnimalManagementEntity getHistoryByAnimalAndUser(int userId, int animalId);
    @Query(value = "SELECT a FROM AnimalManagementEntity a where  a.animalEntity.animalId=:animalId and  a.status = true and a.assignStatus='Assigned'")
    AnimalManagementEntity getHistoryByAnimal( int animalId);
}
