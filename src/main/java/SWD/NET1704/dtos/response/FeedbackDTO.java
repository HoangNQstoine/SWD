package SWD.NET1704.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private int feedbackId;
    private String title;
    private String content;
    private String fullName;
    private int rating;
    private String productName;
    private boolean status;
}
