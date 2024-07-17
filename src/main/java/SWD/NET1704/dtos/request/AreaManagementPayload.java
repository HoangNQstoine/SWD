package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaManagementPayload {
    private int userId;
    private int areaId;
    private Date dateStart;
    private Date dateEnd;
}
