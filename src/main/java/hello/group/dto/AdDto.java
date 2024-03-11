package hello.group.dto;

import hello.group.entity.Ad;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdDto {

    private String createAd;

    private String prompt;

    public AdDto(){

    }

    public AdDto(String createAd){
        this.createAd = createAd;
    }

    public Ad toEntity(){
        return new Ad(null, createAd, prompt);
    }
}
