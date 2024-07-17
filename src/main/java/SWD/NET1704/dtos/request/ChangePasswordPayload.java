package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordPayload {
    private String currentPassword;
    private String newPassword;
}
