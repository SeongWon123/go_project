package hello.group.dto;

import hello.group.entity.Banner;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class BannerDto {

    private Long id;

    private String imagePath;

    private String prompt;

    private String seed;

    private String width;

    private String height;


    public BannerDto(){

    }

    public BannerDto(String imagePath){
        this.imagePath = imagePath;
    }

    public BannerDto(Long id, String createAd, String prompt, String seed, String width, String height){
        this.id = id;
        this.imagePath = createAd;
        this.prompt = prompt;
        this.seed = seed;
        this.width = width;
        this.height = height;
    }


    public Banner toEntity(){
        return new Banner(null, imagePath, prompt,seed, width, height);
    }
}
