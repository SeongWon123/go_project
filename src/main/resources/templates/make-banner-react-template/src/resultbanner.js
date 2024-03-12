import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import './static/css/resultbanner.css';

const Resultbanner = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const sessionSearch = sessionStorage.getItem("userid");

    const [imageData, setImageData] = useState(null);

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
    const GoMain = () => {
        navigate("/main");
    }
    // const GoLogin = () => {
    //     navigate("/login");
    // }
    // const GoSignup = () => {
    //     navigate("/signup");
    // }
    const GoSetting = () => {
        navigate("/setting");
    }

    useEffect(() => {
        // 이전 페이지에서 전달된 데이터 확인
        console.log("전달된 데이터:", location.state);
        const imageDataFromLocation = location.state?.image;
        if (imageDataFromLocation) {
            // base64 인코딩된 이미지 데이터 디코딩하여 설정
            const decodedImage = atob(imageDataFromLocation);
            setImageData(decodedImage);
        }
    }, [location.state]);

    return (
        <body>
        <header>
            <div className="banner-header">
                <h2 className="banner-title" onClick={GoMain}>MAKEBANNER</h2>
                <div className="charts-see-all">
                    <a className="go-login" onClick={GoLogout}>
                        로그아웃
                    </a>
                    {/*<a className="go-login" onClick={GoLogin}>*/}
                    {/*    로그인*/}
                    {/*</a>*/}
                    {/*<a className="go-login" onClick={GoSignup}>*/}
                    {/*    회원가입*/}
                    {/*</a>*/}
                    <button className="go-start">
                        <a className="go-go" onClick={GoSetting}>
                            시작하기
                        </a>
                    </button>
                </div>
            </div>
        </header>
        <section>
            <div className="result-container">
                <h1 className="login-title">생성된 배너 확인</h1>
                {imageData && (
                    <img
                        className="resultimage"
                        src={`data:image/png;base64,${imageData}`}
                        alt="Generated Banner"
                    />
                )}
            </div>
        </section>
        </body>
    );
};

export default Resultbanner;
