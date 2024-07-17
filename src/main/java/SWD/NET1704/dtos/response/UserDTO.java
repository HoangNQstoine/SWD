package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userId;
    private String email;
    private String password;
    private String fullName;
    private boolean status;
    private ProfileDTO profileDTO;
    private RoleDTO roleDTO;
}
