package hello.group.controller;

import hello.group.HelloUserinfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class Controller  {

    private final Map<String, HelloUserinfo> userDatabase = new HashMap<>();

    @PostMapping("/signup")
    public String handleSignup(@RequestBody HelloUserinfo userInfo) {
        // 여기서 userInfo를 처리하고 응답을 반환
        System.out.println("Received user info: " + userInfo.toString());
//         실제로는 이곳에서 데이터를 저장하고 회원가입 처리를 해야 합니다.
        userDatabase.put(userInfo.getUserid(), userInfo);
        return "success";
    }

    @PostMapping("/login")
    public ResponseEntity<String> handleLogin(@RequestBody HelloUserinfo loginInfo) {
        String userid = loginInfo.getUserid();
        String userpassword = loginInfo.getUserpassword();

        // userDatabase에서 userId에 해당하는 사용자 정보 가져오기
        HelloUserinfo storedUserInfo = userDatabase.get(userid);

        if (storedUserInfo != null && storedUserInfo.getUserpassword().equals(userpassword)) {
            // 로그인 성공
            System.out.println("Login successful for user: " + userid + userpassword);
            return ResponseEntity.ok("success");

        } else {
            // 로그인 실패
            System.out.println("Login failed for user: " + userid + userpassword);
            return ResponseEntity.status(401).body("failure");
        }
    }
    @PostMapping("/makebanner")
    public ResponseEntity<String> makeBanner(@RequestBody Map<String, String> bannerInfo) {
        // 클라이언트로부터 받은 배너 설정 정보 처리
        String subject = bannerInfo.get("subject");
        String size = bannerInfo.get("size");
        String text = bannerInfo.get("text");
        String autoText = bannerInfo.get("autotext");

        // 예시로 받은 배너 설정 정보를 콘솔에 출력
        System.out.println("Subject: " + subject);
        System.out.println("Size: " + size);
        System.out.println("Text: " + text);
        System.out.println("Auto Text: " + autoText);

        // 실제로는 이곳에서 받은 설정 정보를 활용하여 필요한 작업을 수행할 수 있습니다.
        // 예를 들어, 데이터베이스에 저장하거나 다른 서비스로 전달할 수 있습니다.

        return ResponseEntity.ok("Received banner settings");
    }


}
