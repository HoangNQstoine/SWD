package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalCageDetailPayload {
    private int animalCageId;
    private int animalId;
    private Date dateStart;
    private Date dateEnd;
    private String animalCageDetailName;
}
