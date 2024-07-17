package SWD.NET1704.dtos.request;

import SWD.NET1704.dtos.response.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePayload {
    private String password;
    private ProfileDTO profileDTO;
    private int roleId;
}
