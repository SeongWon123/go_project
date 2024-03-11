package hello.group.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MakeBannerContents {

    private String subject;
    private String size;
    private String text;
    private String autoText;

}
