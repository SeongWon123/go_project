package hello.group.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BannerImgInfo {

    private String userId;

    private String imgPath;

    private String prompt;

    private String seed;

    private String width;

    private String height;
}
