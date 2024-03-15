package hello.group.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetBannerImage {

    private String userId;

    private String filename;

    private String prompt;
}
