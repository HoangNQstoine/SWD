package SWD.NET1704.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPayload {
    private String productName;
    private  String description;
    private int price;
    private int quantity;
    private String image;
}
