package SWD.NET1704.dtos.response;

import SWD.NET1704.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = Constants.TOKEN_TYPE_BEARER;
    private long userId;
    private String username;
    private String role;

    public JwtAuthenticationResponse(String accessToken, long userId, String username, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.role = role;
    }
}
