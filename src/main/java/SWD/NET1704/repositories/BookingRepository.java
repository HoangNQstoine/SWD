package SWD.NET1704.repositories;

import SWD.NET1704.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    @Query( value = "SELECT b FROM BookingEntity b where  b.status= true")
    List<BookingEntity> findBookingEntities();
    @Query(value = "SELECT b from BookingEntity b where b.userEntity.userId=:id")
    List<BookingEntity>  getBookingByAccountEntity(int id);

    @Query(value = "SELECT * FROM swp_zoo_management.booking where order_date >= '2023-10-1' and order_date <= '2023-10-31'",nativeQuery = true)
    List<BookingEntity> getAllOrderOnOctober();
    @Query(value = "SELECT * FROM swp_zoo_management.booking where order_date >= '2023-11-1' and order_date <= '2023-11-30'",nativeQuery = true)
    List<BookingEntity> getAllOrderOnNovember();
    @Query(value = "SELECT * FROM swp_zoo_management.booking where order_date >= '2023-12-1' and order_date <= '2023-12-30'",nativeQuery = true)
    List<BookingEntity> getAllOrderOnDecember();

}
