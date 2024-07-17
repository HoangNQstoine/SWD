package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingProductResponse {
    private int productId;
    private int productPrice;
    private int bookingProductQuantity;
    private String productName;
}
