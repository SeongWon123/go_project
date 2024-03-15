import React, { useState, useEffect } from 'react';
import {useLocation, useNavigate } from "react-router-dom";
import axios from 'axios';
import './static/css/setting.css';



const Makebanner = () => {
    const navigate = useNavigate ();
    const sessionSearch = sessionStorage.getItem("userid");
    const [bannerSubject, setBannerSubject] = useState("");
    const [bannerwidthSize, setwidthSize] = useState("");
    const [bannerheightSize, setheightSize] = useState("");
    const [bannerText, setBannerText] = useState("");
    const [bannerautoText, setautoBannerText] = useState("");

    useEffect(() => {
        if (sessionSearch === null) {
            alert("로그인을 해야합니다")
            navigate("/login");
        }
    }, [sessionSearch, navigate]);

    const GoMain = () => {
        navigate("/main");
    }
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
                widthsize: bannerwidthSize,
                heightsize: bannerheightSize,
                text: bannerText,
                autotext: bannerautoText,
                userid : sessionSearch
            };
            console.log(data)

            // POST 요청을 보내고 응답 받기
            const response = await axios.post('/api/makebanner', data);
            const res = response.data
            console.log(res)

            // 응답 데이터 출력
            console.log(response.data);
            setTimeout(() => {
                console.log("Delayed for 1 second.");
                navigate('/makebanner', {state : {path: res, prompt : bannerSubject }});
            }, 1000)
            // navigate('/makebanner', {state : res});
        } catch (error) {
            console.error('Error submitting banner data:', error);
        }
    }

    // JSX를 반환하여 화면을 구성합니다.
    return (
        <body>
        <header>
            <div class="banner-header">

                <h2 className="banner-title" onClick={GoMain}>MAKEBANNER</h2>
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
                <form class="login" onSubmit={handleBannerSubmit}>
                    <h1 class="login-title">배너 제작 설정</h1>
                    <label class="la-t">광고 주제</label>
                    <input className="in-t"
                           name="subject"
                           type="text"
                           value={bannerSubject}
                           onChange={(e) => setBannerSubject(e.target.value)}
                           placeholder="배너 주제를 입력하세요."
                    />
                    <label class="la-t">배너 크기</label>
                    {/*<input class="in-t"*/}
                    {/*       name="size"*/}
                    {/*       type="text"*/}
                    {/*       value={bannerSize}*/}
                    {/*       onChange={(e) => setBannerSize(e.target.value)}*/}
                    {/*       placeholder="배너 크기를 입력해주세요."*/}
                    {/*/>*/}
                    <div className="size-int-box">
                        <input className="in-t"
                               name="size"
                               type="text"
                               value={bannerwidthSize}
                               onChange={(e) => setwidthSize(e.target.value)}
                               placeholder="가로길이 입력"
                        />
                        <input className="in-t"
                               name="size"
                               type="text"
                               value={bannerheightSize}
                               onChange={(e) => setheightSize(e.target.value)}
                               placeholder="세로길이 입력"
                        />
                    </div>


                    <label class="la-t">배너 문구</label>

                    <div className="check_wrap">
                        <input type="checkbox" id="check_btn1"/>
                        <label for="check_btn1"><span>문구 직접입력</span></label>
                    </div>

                    <input className="in-t"
                           name="text"
                           type="text"
                           value={bannerText}
                           onChange={(e) => setBannerText(e.target.value)}
                           placeholder="추천 문구를 직접 입력해주세요."
                    />

                    <div className="check_wrap">
                        <input type="checkbox" id="check_btn2"/>
                        <label for="check_btn2"><span>문구 추천받기</span></label>
                    </div>

                    <button class="rec-but">문구 추천받기</button>

                    <div className="check">
                        <input className="in-t"
                               type="text"
                               name="text"
                               value={bannerautoText}
                               onChange={(e) => setautoBannerText(e.target.value)}
                               placeholder="배너 추천 문구"/>
                        <button>확인</button>
                    </div>


                    <button class="login-but" type='submit' onClick={handleBannerSubmit}>결과 보기</button>
                </form>
            </div>
        </section>

        </body>
    );
};
export default Makebanner;