package SWD.NET1704.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback", schema = "swp_zoo_management", catalog = "")
public class FeedbackEntity {

    @Id
    @Column(name = "feedbackId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "title")
    private String title;
    @Column(name = "rating")
    private int rating;
    @Column(name = "status")
    private boolean status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIgnoreProperties("feedbackEntityList")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    @JsonIgnoreProperties("feedbackEntityList")
    private ProductEntity productEntity;
}
