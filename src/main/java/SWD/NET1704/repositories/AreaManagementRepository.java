package SWD.NET1704.repositories;

import SWD.NET1704.entities.AreaManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaManagementRepository extends JpaRepository<AreaManagementEntity, Integer> {

    @Query(value = "SELECT a FROM AreaManagementEntity a where a.status = true order by a.areaManagementId desc")
    List<AreaManagementEntity> getAllByStatusIsTrue();

    @Query(value = "SELECT a FROM AreaManagementEntity a where a.areaEntity.areaId=:areaId and  a.status = true order by a.areaManagementId desc")
    List<AreaManagementEntity> getAllByAreaEntity(int areaId);

    @Query(value = "SELECT a FROM AreaManagementEntity a where a.userEntity.userId=:userId and  a.status = true order by a.areaManagementId desc")
    List<AreaManagementEntity> getAllByUserEntity(int userId);

    @Query(value = "SELECT a FROM AreaManagementEntity a where a.userEntity.userId=:userId and  a.status = true and a.isManaging=true order by a.areaManagementId desc")
    AreaManagementEntity getAreaManagementEntityByUserEntity(int userId);

    @Query(value = "SELECT a FROM AreaManagementEntity a where a.areaEntity.areaId=:areaId and  a.status = true and a.isManaging=true order by a.areaManagementId desc")
    AreaManagementEntity getAreaManagementEntityByAreaEntity(int areaId);
}
