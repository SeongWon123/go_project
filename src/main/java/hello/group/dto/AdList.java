package hello.group.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
 public class AdList {
    private  String field1;
    private  String field2;

    public AdList(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
