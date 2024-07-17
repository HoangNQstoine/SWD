package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePayload {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String gender;
    private String avatar;
}
