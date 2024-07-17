package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalCageDetailResponse {
    private int animalCageDetailId;
    private int animalCageId;
    private String animalCageName;
    private int animalId;
    private String animalName;
    private Date dateStart;
    private Date dateEnd;
    private String animalCageDetailName;
    private boolean status;
    private String useStatus;
}
