package hello.group.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.group.dto.*;
import hello.group.service.MakeImageService;
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
import java.util.*;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MakeImageService makeImageService;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/signup")
    public String signUp(@RequestBody UserInfo userInfo, @Valid BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return "fail";
        }
        boolean a = userInfoService.getAllList(userInfo);
        if(a == false){
            bindingResult.reject("Fail", "중복된 아이디 입니다.");
            return "중복된 아이디 입니다.";
        }
        userInfoService.save(userInfo);

        return "success";

    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserInfo userInfo, @Valid BindingResult bindingResult) {

        // 아이디 비번 일치 확인 후 db에서 정보 가져오기
        UserInfo byId = userInfoService.findById(userInfo.getUserId(), userInfo.getUserPassword());
        System.out.println(byId + "entity");

        //저장시 현재 시간 userId + 현재시간.png로 저장
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);

        if(bindingResult.hasErrors() || byId == null) { //에러 발생시 BindingResult 활용해서 글로벌 에러 띄우기

            System.out.println("Login failed for user: " + userInfo.getUserId() + userInfo.getUserPassword());
            return ResponseEntity.status(401).body("failure");

        }else{// 에러가 발생하지 않는다면 성공했다고 알려주기

            System.out.println("Login successful for user: " + userInfo.getUserId() + userInfo.getUserPassword());
            return ResponseEntity.ok("success");

        }
    }
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/update")
    public void updateUserInfo(@RequestBody UserInfo userInfo){

        String userId = userInfo.getUserId();
        String userName = userInfo.getUserName();
        String userPassword = userInfo.getUserPassword();
        String businessNumber = userInfo.getBusinessNumber();
        System.out.println(" ==>" + userId + "==" + userPassword + "==" + userName + "==" + businessNumber);
        userInfoService.updateUserInfo(userInfo);

    }

    @PostMapping("/delete")
    public void deleteUserInfo(){

    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/makebanner")
    public String makeBanner(@RequestBody Map<String, String> bannerInfo) {

        // 클라이언트로부터 받은 배너 설정 정보 처리
        String subject = bannerInfo.get("subject");
        String width = bannerInfo.get("width");
        String height = bannerInfo.get("height");
        int w = Integer.parseInt(width);
        int h = Integer.parseInt(height);
        String text = bannerInfo.get("text");
        String autoText = bannerInfo.get("autoText");
        String userId = bannerInfo.get("userId");


        // 쿼리로 보내기 위해선 공백을 없애야함 그래서 replace를 활용해 공백을 어떠한 문자로 치환
        String replaceText = subject.replaceAll(" ", "/");
        System.out.println(replaceText);

        Map<String, String> image = makeImageService.getImage(replaceText, userId, subject, w, h);

        ObjectMapper objectMapper = new ObjectMapper();

        // Map을 JSON 문자열로 변환
        String jsonString1 = null;
        try {
            jsonString1 = objectMapper.writeValueAsString(image);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("반환" + jsonString1);

        return jsonString1;
    }

    @PostMapping("/editorPage")
    public String saveBanner(@RequestBody BannerImgInfo bannerImgInfo) {

        // 받은 이미지 데이터 처리
        String imagePath = bannerImgInfo.getImgPath();
        String userId = bannerImgInfo.getUserId();
        String prompt = bannerImgInfo.getPrompt();
        String seed = bannerImgInfo.getSeed();
        String width = bannerImgInfo.getWidth();
        String height = bannerImgInfo.getHeight();

        String replaceText = imagePath.replaceAll("\\\\result\\\\", "");

        String s = makeImageService.saveImg(userId, replaceText, prompt, seed, width, height);



        System.out.println("=================================");
        System.out.println(imagePath);


        // 간단한 응답 생성
        String responseMessage = "이미지 데이터를 성공적으로 받았습니다.";
        return s;

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
    public String userBanner (@RequestBody UserId userIdByReq){

        String userId = userIdByReq.getUserId();

        Map<String, List<String>> byId = userInfoService.findByImg(userId);

        ObjectMapper objectMapper = new ObjectMapper();

        // Map을 JSON 문자열로 변환
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(byId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString.toString());

        return jsonString;
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/myPage2")
    public UserInfo userInfo (@RequestBody UserId userIdByReq){

        String userId = userIdByReq.getUserId();
        UserInfo byId = userInfoService.findByUserInfo(userId);
        return byId;

    }


    @PostMapping("/deleteBanner")
    public void deleteBanner(){

    }

}

