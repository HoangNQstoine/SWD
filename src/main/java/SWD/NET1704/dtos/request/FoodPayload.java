package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodPayload {
    private String foodName;
    private String nutriment;
    private String unit;
    private String image;
    private Date dateStart;
    private Date  dateEnd;
    private int  quantity;
}
