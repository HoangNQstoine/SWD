package SWD.NET1704.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackPayload {
    private String title;
    private String content;
    private int userId;
    private int productId;
    private int rating;
}
