package hello.group.entity;

import hello.group.dto.AdDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Ad {

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

    public Ad(Long createId, String createAd, String prompt, String seed, String width, String height) {
        this.id = createId;
        this.imagePath = createAd;
        this.prompt = prompt;
        this.seed = seed;
        this.width = width;
        this.height = height;
    }

    public AdDto toDto(){
        return new AdDto(id, imagePath, prompt, seed, width, height);
    }
}
