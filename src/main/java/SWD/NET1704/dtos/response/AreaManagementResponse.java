package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaManagementResponse {
    private int areaManagementId;
    private int userId;
    private String fullName;
    private int areaId;
    private String areaName;
    private Date dateStart;
    private Date dateEnd;
    private boolean isManaging;
    private  boolean status;
}
