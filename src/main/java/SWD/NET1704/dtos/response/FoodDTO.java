package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {

    private int foodId;
    private String foodName;
    private String nutriment;
    private String unit;
    private Date dateStart;
    private Date  dateEnd;
    private int  quantity;
    private String image;
    private Boolean status;
}
