import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import './static/css/resultbanner.css';
import axios from "axios";
import './App.css';
import base64 from "base-64";


// function App() {
//     // message 초기값 설정 (""로 설정)
//     const [message, setMessage] = useState("");
//
//     // useEffect(함수, 배열) : 컴포넌트가 화면에 나타났을 때 자동 실행
//     useEffect(() => {
//         // fetch(url, options) : Http 요청 함수
//         fetch("/api/hi")
//             .then(response => response.text())
//             .then(message => {
//                 setMessage(message);
//                 console.log(message);
//
//             });
//     }, [])
const Resultbanner = React.memo(() => {
    const navigate = useNavigate();
    const location = useLocation();
    const sessionSearch = sessionStorage.getItem("userid");
    const b = String(location.state?.path);

    const im = b ? `${b.toLowerCase()}` : '';
    const ab = require(`C:/git/group/src/main/resources/templates/make-banner-react-template/public/result/${im}`)

    const [adData, setAdData] = useState([]);

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
    const GoLogin = () => {
        navigate("/login");
    }
    const GoSignup = () => {
        navigate("/signup");
    }
    const GoSetting = () => {
        navigate("/setting");
    }

    const GOmy = () => {
        navigate("/mypage");
    }





    // function App() {
    //     // message 초기값 설정 (""로 설정)
    //     const [message, setMessage] = useState("");
    //
    //     // useEffect(함수, 배열) : 컴포넌트가 화면에 나타났을 때 자동 실행
    //     useEffect(() => {
    //         // fetch(url, options) : Http 요청 함수
    //         fetch("/api/hi")
    //             .then(response => response.text())
    //             .then(message => {
    //                 setMessage(message);
    //             });
    //     }, [])





    return (
        <body>
        <header>
            <div className="banner-header">

                <h2 className="banner-title">MAKEBANNER</h2>

                <div className="charts-see-all">

                    <a className="go-login" href="#">
                        로그인
                    </a>

                    <a className="go-login" href="#">
                        회원가입
                    </a>

                    <button className="go-start">
                        <a className="go-go" href="#">
                            시작하기
                        </a>
                    </button>

                </div>

            </div>
        </header>

        <section>
            <div className="container">
                <div>
                    <h2 className="res-title">배너 생성 완료</h2>
                </div>

                <div className="res-box">
                    <img  className="result-image" src={ab} alt=""/>
                </div>

                <button className="go-start">
                    <a className="go-go" onClick={GoMain}>
                        완료
                    </a>
                </button>
            </div>
        </section>

        </body>

    );
});
export default Resultbanner;
