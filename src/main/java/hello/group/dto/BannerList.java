package hello.group.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
 public class BannerList {
    private  String field1;
    private  String field2;

    public BannerList(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
