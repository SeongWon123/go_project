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
    @Column(name = "create_id")
    private Long createId;

    private String createAd;

    private String prompt;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User userNum;

    public Ad(Long createId, String createAd, String prompt) {
        this.createId = createId;
        this.createAd = createAd;
        this.prompt = prompt;
    }

    public AdDto toDto(){
        return new AdDto(createId, createAd, prompt);
    }
}
