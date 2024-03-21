package hello.group.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.group.dto.*;
import hello.group.service.MakeImageService;
import hello.group.service.UserInfoService;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public String makeBanner(@RequestBody Map<String, String> bannerInfo) {

        // 클라이언트로부터 받은 배너 설정 정보 처리
        String subject = bannerInfo.get("subject");
        String widthsize = bannerInfo.get("widthsize");
        String heightsize = bannerInfo.get("heightsize");
        int w = Integer.parseInt(widthsize);
        int h = Integer.parseInt(heightsize);
        String text = bannerInfo.get("text");
        String autoText = bannerInfo.get("autotext");
        String userId = bannerInfo.get("userid");


        // 쿼리로 보내기 위해선 공백을 없애야함 그래서 replace를 활용해 공백을 어떠한 문자로 치환
        String replaceText = subject.replaceAll(" ", "/");
        System.out.println(replaceText);

        List<String> lis = new ArrayList<>();
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
    public String handleBannerData(@RequestBody GetBannerImage getBannerImage) {
        // 받은 이미지 데이터 처리
        String image = getBannerImage.getFilename();
        String userId = getBannerImage.getUserId();
        String prompt = getBannerImage.getPrompt();
        String seed = getBannerImage.getSeed();

        String replaceText = image.replaceAll("\\\\result\\\\", "");

        String s = makeImageService.saveImg(userId, replaceText, prompt, seed);



        System.out.println("=================================");
        System.out.println(image);


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
    public String userInfo (@RequestBody Hello getBannerImage){

        String userId = getBannerImage.getUserId();

        Map<String, List<String>> byId = userInfoService.findById2(userId);

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
    public HelloUserinfo userInfo2 (@RequestBody Hello getBannerImage){

        String userId = getBannerImage.getUserId();
        HelloUserinfo byId1 = userInfoService.a(userId);
        return byId1;
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("/trueOrFalse")
    public String trueOrFalse(@RequestBody Map<String, String> a){

        System.out.println("호출");
        System.out.println("받아왔나?" + a);

        Iterator<String> iterator = a.keySet().iterator();
        String please = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\";
        while(iterator.hasNext()){
            String please1 = "";
            String key = iterator.next();
            please1 = please + key;
            boolean exists = Files.exists(Paths.get(please1));
            if (exists){
                break;
            }
        }

        return "ok";


    }

//    @PostMapping("hi")
//    public String aaa(@RequestBody String m){
//
//        Map<String, String> resultMap = new LinkedHashMap<>();
//        List<String> lis = new ArrayList<>();
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            resultMap = mapper.readValue(m, new TypeReference<Map<String, String>>(){});
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        for (String imagePath : resultMap.values()) {
//            // 이미지 파일의 절대 경로를 생성합니다. (여기서는 상대 경로로 가정하고 있습니다.)
//            String absolutePath = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public" + imagePath; // 이미지 디렉토리 경로를 적절히 수정해야 합니다.
//            lis.add(absolutePath);
//
//            // 파일의 존재 여부를 확인합니다.
//            if (Files.exists(Paths.get(absolutePath))) {
//                System.out.println("이미지 파일이 존재합니다: " + absolutePath);
//            } else {
//                System.out.println("이미지 파일이 존재하지 않습니다: " + absolutePath);
//            }
//        }
//
//        makeImageService.aa(lis);
//
//        return "success";
//
//        // JSON 문자열을 Map<String, String> 객체로 변환합니다.
//
//
//    }

}

