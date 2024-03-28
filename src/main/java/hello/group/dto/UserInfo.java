package hello.group.dto;
import hello.group.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;


@Getter
public class UserInfo {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private String businessNumber;

    public UserInfo(String userName, String userId, String userPassword, String businessNumber) {
        this.userName = userName;
        this.userId = userId;
        this.userPassword = userPassword;
        this.businessNumber = businessNumber;
    }
    public UserInfo(){

    }


    public User toEntity(){
        return new User(null, userName, userId, userPassword, businessNumber);
    }
}
