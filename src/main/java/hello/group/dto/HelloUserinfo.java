package hello.group.dto;

import hello.group.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
public class HelloUserinfo {
    @NotEmpty
    private String username;
    @NotEmpty
    private String userid;
    @NotEmpty
    private String userpassword;
    @NotEmpty
    private String userCRN;

    public HelloUserinfo(String username, String userid, String userpassword, String userCRN) {
        this.username = username;
        this.userid = userid;
        this.userpassword = userpassword;
        this.userCRN = userCRN;
    }
    public HelloUserinfo(){

    }


    public User toEntity(){
        return new User(null, username, userid, userpassword, userCRN);
    }
}
