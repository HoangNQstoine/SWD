package SWD.NET1704.dtos.request;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsPayload {
    private String title;
    private String content;
    private String image;
    private Date dateCreated;
    private String newsType;
}
