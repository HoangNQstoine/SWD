package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDTO {
    private int areaId;
    private String areaName;
    private String description;
    private boolean status;
    List<AnimalCageResponse> animalCageResponseList;
}
