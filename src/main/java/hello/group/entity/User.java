package hello.group.entity;

import hello.group.dto.HelloUserinfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num")
    private Long num;

    private String username;

    private String userid;

    private String userpassword;

    private String userCRN;

//    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(mappedBy = "userId")
    private List<Ad> adList = new ArrayList<>();

    public User(Long num, String username, String userid, String userpassword, String userCRN) {
        this.num = num;
        this.username = username;
        this.userid = userid;
        this.userpassword = userpassword;
        this.userCRN = userCRN;
    }
    public HelloUserinfo toDto(){
        return new HelloUserinfo(username, userid, userpassword, userCRN);
    }
}
