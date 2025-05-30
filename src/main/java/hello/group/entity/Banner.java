package hello.group.entity;

import hello.group.dto.BannerDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long id;

    private String imagePath;

    private String prompt;

    private String seed;

    private String width;

    private String height;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User userNum;

    public Banner(Long createId, String createAd, String prompt, String seed, String width, String height) {
        this.id = createId;
        this.imagePath = createAd;
        this.prompt = prompt;
        this.seed = seed;
        this.width = width;
        this.height = height;
    }

    public BannerDto toDto(){
        return new BannerDto(id, imagePath, prompt, seed, width, height);
    }
}
