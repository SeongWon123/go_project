import { useNavigate } from "react-router-dom";
import './static/css/main.css';
import React from "react";
import mainImg1 from "./static/resultImg/main1.jpg"
import mainImg2 from "./static/resultImg/main2.png"
import mainImg3 from "./static/resultImg/main3.jpg"
const Main = () => {
    const navigate = useNavigate();

    const GoLogin = () => {
        navigate("/login");
    }

    const GoSignup = () => {
        navigate("/signup");
    }

    const GoSetting = () => {
        navigate("/setting");
    }

    return (
                <body>
                <header>
                    <div className="banner-header">
                        <h2 className="banner-title">MAKEBANNER</h2>
                        <div className="charts-see-all">
                            <a className="go-login" onClick={GoLogin}>
                                로그인
                            </a>
                            <a className="go-login" onClick={GoSignup}>
                                회원가입
                            </a>
                            <button className="go-start">
                                <a className="go-go" onClick={GoSetting}>
                                    시작하기
                                </a>
                            </button>
                        </div>
                    </div>
                </header>

                <section>
                    <div className="fir-con">
                        <h2 className="fir-text">
                            누구나 손쉽게<br/>배너 제작
                        </h2>
                        <button className="fir-but">
                            시작하기
                        </button>
                    </div>

                    <div className="sec-con">
                        <div className="sec-img">
                            <img className="avocado" src={mainImg1} alt="avocado"/>
                        </div>

                        <div className="sec-text-con">
                            <h2 className="sec-text">
                                마음에 드는 배너를<br/>골라보세요
                            </h2>
                            <p className="sec-s">
                                원하는 배너의 조건을 작성하고 자신이 마음에 드는<br/>배너를 선택해 볼 수 있습니다.
                            </p>
                            <div className="saerch">
                                <input type="text" placeholder="   배너 종류를 입력하세요."/>
                                <img className="s-icon" src={mainImg2} alt="search icon"/>
                            </div>
                        </div>
                    </div>

                    <div className="thr-con">
                        <div className="thr-text-con">
                            <h2 className="thr-text">
                                원하는 배너 추천 기능
                            </h2>
                            <p className="thr-s">
                                사진뿐만 아니라 문구까지 추천해줍니다.
                            </p>
                        </div>
                        <div className="thr-img">
                            <img className="icon-img" src={mainImg3} alt="icon"/>
                        </div>
                    </div>

                    <div className="four-con">
                        <h2 className="four-text">
                            지금 바로 시작해보세요.
                        </h2>
                        <div>
                            <button className="four-but">
                                시작하기
                            </button>
                        </div>
                    </div>
                </section>


                </body>
    );
};

export default Main;
