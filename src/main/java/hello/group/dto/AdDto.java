package hello.group.dto;

import hello.group.entity.Ad;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class AdDto {

    private Long id;

    private String imagePath;

    private String prompt;

    private String seed;


    public AdDto(){

    }

    public AdDto(String imagePath){
        this.imagePath = imagePath;
    }

    public AdDto(Long id, String createAd, String prompt, String seed){
        this.id = id;
        this.imagePath = createAd;
        this.prompt = prompt;
        this.seed = seed;
    }


    public Ad toEntity(){
        return new Ad(null, imagePath, prompt,seed);
    }
}
