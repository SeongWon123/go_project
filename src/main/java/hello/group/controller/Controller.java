package hello.group.controller;
import hello.group.dto.*;
import hello.group.entity.Ad;
import hello.group.entity.User;
import hello.group.service.MakeImageService;
import hello.group.service.UserInfoService;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MakeImageService makeImageService;

    @Autowired
    private EntityManager em;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/signup")
    public String handleSignup(@RequestBody HelloUserinfo helloUserInfo, @Valid BindingResult bindingResult) {

        // user정보 저장
        HelloUserinfo save = userInfoService.save(helloUserInfo);
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
        String widthsize = bannerInfo.get("widthsize");
        String heightsize = bannerInfo.get("heightsize");
        int w = Integer.parseInt(widthsize);
        int h = Integer.parseInt(heightsize);
        String text = bannerInfo.get("text");
        String autoText = bannerInfo.get("autotext");
        String userId = bannerInfo.get("userid");

        // 예시로 받은 배너 설정 정보를 콘솔에 출력
        System.out.println("Subject: " + subject);
        System.out.println("widthSize: " + widthsize);
        System.out.println("heightSize: " + heightsize);
        System.out.println("Text: " + text);
        System.out.println("Auto Text: " + autoText);
        System.out.println("userId: " + userId);

        // 쿼리로 보내기 위해선 공백을 없애야함 그래서 replace를 활용해 공백을 어떠한 문자로 치환
        String replaceText = subject.replaceAll(" ", "/");
        System.out.println(replaceText);

        String image = makeImageService.getImage(replaceText, userId, subject, w, h);
        System.out.println(image);


        return ResponseEntity.ok(image);


    }
    @PostMapping("/editorPage")
    public ResponseEntity<String> handleBannerData(@RequestBody GetBannerImage getBannerImage) {
        // 받은 이미지 데이터 처리
        String image = getBannerImage.getFilename();
        String userId = getBannerImage.getUserId();
        String prompt = getBannerImage.getPrompt();
        System.out.println("이미지를 받아왔습니다: " + image);
        System.out.println("이미지를 받아왔습니다: " + userId);
        System.out.println("이미지를 받아왔습니다: " + prompt);

        String s = makeImageService.saveImg(userId, image, prompt);
        System.out.println(s);

        //String a = s.replaceAll("\\\\", "/");


        System.out.println("=================================");

        //Map<List<String>, List<String>> byId = userInfoService.findById2(userId);
        //String string = byId.toString();


        // 간단한 응답 생성
        String responseMessage = "이미지 데이터를 성공적으로 받았습니다.";
        return ResponseEntity.ok(image);

    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/resultPage")
    public ResponseEntity<String> handleBannerData2() {

        // 간단한 응답 생성
        String responseMessage = "이미지 데이터를 성공적으로 받았습니다.";
        return ResponseEntity.ok("ok");

    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/myPage")
    public List<Ad> userInfo (@RequestBody Hello getBannerImage){

        String userId = getBannerImage.getUserId();
        List<Ad> byId = userInfoService.findById2(userId);
        System.out.println(byId);

        return byId;
    }


    @GetMapping("hi")
    public List<Ad> a (){
        String userId = "a";
        List<Ad> byId = userInfoService.findById2(userId);

        return byId;
    }


}

