package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCageDTO {
    private int animalCageId;
    private String animalCageName;
    private String description;
    private int maxQuantity;
    private boolean isUse;
    private Boolean status;
    private String areaName;
    private String areaDescription;
}
