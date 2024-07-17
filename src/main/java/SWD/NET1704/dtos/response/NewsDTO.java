package SWD.NET1704.dtos.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private int newsId;
    private String content;
    private String title;
    private String image;
    private boolean status;
    private Date createdDate;
    private String newsType;
}
