package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDietManagementResponse {
    private int animalDietManagementId;
    private String animalDietManagementName;
    private String animalName;
    private int animalId;
    private int dietId;
    private String dietName;
    private Date dateStart;
    private Date dateEnd;
    private boolean status;
    private String useStatus;
}
