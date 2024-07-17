package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietDTO {
    private int dietId;
    private String dietName;
    private List<DietFoodResponse> dietFoodResponses;
    private boolean status;
}
