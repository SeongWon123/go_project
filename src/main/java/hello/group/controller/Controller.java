package hello.group.controller;
import hello.group.dto.HelloUserinfo;
import hello.group.entity.User;
import hello.group.service.UserInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    @Autowired
    private UserInfoService userInfoService;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/signup")
    public String handleSignup(@RequestBody HelloUserinfo helloUserInfo, @Valid BindingResult bindingResult) {

        // user정보 저장
        User save = userInfoService.save(helloUserInfo);
        System.out.println(save);

        if (save == null){
            return "fail";
        }

        System.out.println("Received user info: " + helloUserInfo);

        return "success";

    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<String> handleLogin(@RequestBody HelloUserinfo helloUserInfo, @Valid BindingResult bindingResult) {

        // 아이디 비번 일치 확인 후 db에서 정보 가져오기
        HelloUserinfo byId = userInfoService.findById(helloUserInfo.getUserid(), helloUserInfo.getUserpassword());
        System.out.println(byId + "entity");

        //저장시 현재 시간 userId + 현재시간.png로 저장
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);

        if(bindingResult.hasErrors() || byId == null) { //에러 발생시 BindingResult 활용해서 글로벌 에러 띄우기

            System.out.println("Login failed for user: " + helloUserInfo.getUserid() + helloUserInfo.getUserpassword());
            return ResponseEntity.status(401).body("failure");

        }else{// 에러가 발생하지 않는다면 성공했다고 알려주기

            System.out.println("Login successful for user: " + helloUserInfo.getUserid() + helloUserInfo.getUserpassword());
            return ResponseEntity.ok("success");

        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/makebanner")
    public ResponseEntity<String> makeBanner(@RequestBody Map<String, String> bannerInfo) {

        // 클라이언트로부터 받은 배너 설정 정보 처리
        String subject = bannerInfo.get("subject");
        String size = bannerInfo.get("size");
        String text = bannerInfo.get("text");
        String autoText = bannerInfo.get("autotext");
        String userId = bannerInfo.get("userid");

        // 예시로 받은 배너 설정 정보를 콘솔에 출력
        System.out.println("Subject: " + subject);
        System.out.println("Size: " + size);
        System.out.println("Text: " + text);
        System.out.println("Auto Text: " + autoText);
        System.out.println("userId: " + userId);

        // 쿼리로 보내기 위해선 공백을 없애야함 그래서 replace를 활용해 공백을 어떠한 문자로 치환
        String replaceText = subject.replaceAll(" ", "/");
        System.out.println(replaceText);

        userInfoService.getImage(replaceText, userId, subject);

        return ResponseEntity.ok("Received banner settings");

    }

}

