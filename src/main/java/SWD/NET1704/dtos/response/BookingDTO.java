package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private int bookingId;
    private int userId;
    private String fullName;
    private boolean status;
    private Date bookingDate;
    private List<BookingProductResponse> productDTOS;
    private double totalPrice;
}
