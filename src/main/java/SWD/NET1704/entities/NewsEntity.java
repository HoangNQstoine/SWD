package SWD.NET1704.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news", schema = "swp_zoo_management", catalog = "")
public class NewsEntity {
    @Id
    @Column(name = "newsId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int newsId;

    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "image")
    private String image;
    @Column(name = "status")
    private boolean status;

    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "newsType")
    private String newsType;
}
