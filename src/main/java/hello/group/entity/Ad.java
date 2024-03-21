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

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User userNum;

    public Ad(Long createId, String createAd, String prompt, String seed) {
        this.id = createId;
        this.imagePath = createAd;
        this.prompt = prompt;
        this.seed = seed;
    }

    public AdDto toDto(){
        return new AdDto(id, imagePath, prompt, seed);
    }
}
