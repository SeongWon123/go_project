import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import './static/css/setting.css';



const Makebanner = () => {
    const navigate = useNavigate ();
    const sessionSearch = sessionStorage.getItem("userid");
    const [bannerSubject, setBannerSubject] = useState("");
    const [bannerSize, setBannerSize] = useState("");
    const [bannerText, setBannerText] = useState("");
    const [bannerautoText, setautoBannerText] = useState("");

    useEffect(() => {
        if (sessionSearch === null) {
            alert("로그인을 해야합니다")
            navigate("/login");
        }
    }, [sessionSearch, navigate]);

    const GoLogout = () => {
        sessionStorage.removeItem("userid");
        navigate("/login");
    }
    // const GoSignup = () => {
    //   navigate("/signup");
    // }
    const GoSetting = () =>{
        navigate("/setting")
    }
    const handleBannerSubmit = async (event) => {
        event.preventDefault(); // 페이지 리로딩 방지
        try {
            // 서버로 보낼 데이터를 객체로 만듦
            const data = {
                subject: bannerSubject,
                size: bannerSize,
                text: bannerText,
                autotext: bannerautoText
            };

            // POST 요청을 보내고 응답 받기
            const response = await axios.post('/api/makebanner', data);

            // 응답 데이터 출력
            console.log(response.data);
        } catch (error) {
            console.error('Error submitting banner data:', error);
        }
    }

    // JSX를 반환하여 화면을 구성합니다.
    return (
        <body>
        <header>
            <div class="banner-header">

                <h2 class="banner-title">MAKEBANNER</h2>

                <div class="charts-see-all">

                    <a class="go-login" onClick={GoLogout}>
                        로그아웃
                    </a>

                    {/* <a class="go-login" onClick={GoSignup}>
        회원가입
      </a> */}

                    <button class="go-start">
                        <a class="go-go" onClick={GoSetting}>
                            시작하기
                        </a>
                    </button>

                </div>

            </div>
        </header>

        <section>
            <div class="container">
                <form class= "login" onSubmit={handleBannerSubmit}>
                    <h1 class= "login-title">배너 제작 설정</h1>
                    <label class="la-t">배너 주제</label>
                    <input class="in-t"
                           name="subject"
                           type="text"
                           value={bannerSubject}
                           onChange={(e) => setBannerSubject(e.target.value)}
                           placeholder="배너 주제를 입력하세요."
                    />
                    <label class="la-t">배너 크기</label>
                    <input class="in-t"
                           name="size"
                           type="text"
                           value={bannerSize}
                           onChange={(e) => setBannerSize(e.target.value)}
                           placeholder="배너 크기를 입력해주세요."
                    />

                    <label class="la-t">배너 문구</label>

                    <div class="check_wrap">
                        <input type="checkbox" id="check_btn1"/>
                        <label for="check_btn1"><span>문구 직접입력</span></label>
                    </div>

                    <input class="in-t"
                           name="text"
                           type="text"
                           value={bannerText}
                           onChange={(e) => setBannerText(e.target.value)}
                           placeholder="추천 문구를 직접 입력해주세요."
                    />

                    <div class="check_wrap">
                        <input type="checkbox" id="check_btn2"/>
                        <label for="check_btn2"><span>문구 추천받기</span></label>
                    </div>

                    <button class="rec-but">문구 추천받기</button>

                    <div class="check">
                        <input class="in-t"
                               type="text"
                               name="text"
                               value={bannerautoText}
                               onChange={(e) => setautoBannerText(e.target.value)}
                               placeholder="배너 추천 문구"/>
                        <button>확인</button>
                    </div>


                    <button class="login-but" type='submit'>결과 보기</button>
                </form>
            </div>
        </section>

        </body>
    );
};
export default Makebanner;