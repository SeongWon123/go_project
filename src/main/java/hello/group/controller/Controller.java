package hello.group.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller { // 메인 컨트롤러

    @GetMapping("/logIn") // 로그인 페이지 매핑
    public String logInPage(){
        return "/login";
    }

    @GetMapping("/myPage") // 마이 페이지 매핑
    public String myPage(){
        return "/mypage";
    }

    @GetMapping("/signUp") // 회원가입 페이지 매핑
    public String signUpPage(){
        return "/signup";
    }

    @GetMapping("/makeBanner") //배너 생성 페이지
    public String makeBannerPage(){
        return "setting";
    }

}
