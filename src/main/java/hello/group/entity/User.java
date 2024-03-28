package hello.group.entity;

import hello.group.dto.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num")
    private Long num;

    private String userName;

    private String userId;

    private String userPassword;

    private String businessNumber;

//    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(mappedBy = "userNum")
    private List<Ad> adList = new ArrayList<>();

    public User(Long num, String userName, String userId, String userPassword, String businessNumber) {
        this.num = num;
        this.userName = userName;
        this.userId = userId;
        this.userPassword = userPassword;
        this.businessNumber = businessNumber;
    }
    public UserInfo toDto(){
        return new UserInfo(userName, userId, userPassword, businessNumber);
    }
}
