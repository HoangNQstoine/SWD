package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCageResponse {
    private int animalCageId;
    private String animalCageName;
    private String description;
    private boolean isUse;
}
