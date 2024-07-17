package SWD.NET1704.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalPayload {
    private String animalName;
    private int catalogueId;
    private String image;
    private String country;
    private boolean isRare;
    private String gender;
}
