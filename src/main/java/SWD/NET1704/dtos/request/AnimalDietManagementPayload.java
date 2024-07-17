package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDietManagementPayload {
    private String animalDietManagementName;
    private int animalId;
    private int dietId;
    private Date dateStart;
    private Date dateEnd;
}
