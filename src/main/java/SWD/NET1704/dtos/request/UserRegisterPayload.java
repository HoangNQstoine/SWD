package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterPayload {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
