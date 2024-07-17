package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCagePayload {

    private String animalCageName;
    private String description;
    private int maxQuantity;
    private int areaId;
}
