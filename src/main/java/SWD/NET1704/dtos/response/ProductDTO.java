package SWD.NET1704.dtos.response;

import java.util.List;

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
public class ProductDTO {
    private int productId;
    private String productName;
    private int price;
    private int quantity;
    private double rating;
    private String image;
    private String description;
    private List<FeedbackDTO> feedbackDTOList;
}
