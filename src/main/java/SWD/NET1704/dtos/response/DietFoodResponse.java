package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietFoodResponse {
    private int foodId;
    private String foodName;
    private int dietFoodQuantity;
    private String foodImage;
    private String nutriment;
    private String unit;
    private Date dateStart;
    private Date dateEnd;
}
