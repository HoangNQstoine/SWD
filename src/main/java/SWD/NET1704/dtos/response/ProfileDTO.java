package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private int profileId;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String gender;
    private String avatar;
    private Boolean isDeleted;
}
