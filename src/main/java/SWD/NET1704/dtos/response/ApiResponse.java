package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Boolean status;
    private String message;
    private Object data;

    public ApiResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
