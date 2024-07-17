package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalManagementResponse {
    private int animalTrainingId;
    private int userId;
    private String fullName;
    private int animalId;
    private String animalName;
    private Date dateStart;
    private Date dateEnd;
    private  String description;
    private String assignStatus;
    private  boolean status;
}
