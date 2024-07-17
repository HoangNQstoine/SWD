package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private int animalId;
    private String animalName;
    private CatalogueDTO catalogueDTO;
    private boolean status;
    private String image;
    private String country;
    private boolean isRare;
    private String gender;
}
