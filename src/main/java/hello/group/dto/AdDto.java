package hello.group.dto;

import hello.group.entity.Ad;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class AdDto {

    private Long createId;

    private String createAd;

    private String prompt;


    public AdDto(){

    }

    public AdDto(String createAd){
        this.createAd = createAd;
    }

    public AdDto(Long createId, String createAd, String prompt){
        this.createId = createId;
        this.createAd = createAd;
        this.prompt = prompt;
    }


    public Ad toEntity(){
        return new Ad(null, createAd, prompt);
    }
}
