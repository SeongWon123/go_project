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

    private String width;

    private String height;


    public AdDto(){

    }

    public AdDto(String imagePath){
        this.imagePath = imagePath;
    }

    public AdDto(Long id, String createAd, String prompt, String seed, String width, String height){
        this.id = id;
        this.imagePath = createAd;
        this.prompt = prompt;
        this.seed = seed;
        this.width = width;
        this.height = height;
    }


    public Ad toEntity(){
        return new Ad(null, imagePath, prompt,seed, width, height);
    }
}
