import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import './static/css/login.css';


const Login = () =>  {
    const [userId, setId] = useState('');
    const [userPassword, setPassword] = useState('');
    const loginInfo = {userId, userPassword};
    const navigate = useNavigate();
    const sessionSearch = sessionStorage.getItem("userId");
    useEffect(() => {
        if (sessionSearch) { // sessionSearch가 존재한다면
            alert("이미 로그인되어 있습니다"); // 이미 로그인되어 있다는 알림을 띄웁니다
            navigate("/setting");
        }
    }, []); // 의존성 배열 비워짐
    const GoMain = () => {
        navigate("/main");
    }
    const GoLogin = () => {
        navigate("/login");
    }
    const GoSignup = () => {
        navigate("/signup");
    }
    const GoSetting = () =>{
        navigate("/setting");
    }
    // 상태 훅을 사용하여 아이디와 비밀번호의 상태를 관리합니다.
    const handleLogin = async(event) => {
        event.preventDefault(); // 오타 수정
        try{
            const response = await axios.post('/api/login', loginInfo,{
                headers: {
                    'Content-Type': 'application/json' // JSON 형식으로 보내기 위한 헤더 설정
                }
            });
            if (response.data === 'success') {
                console.log("로그인 성공", userId, userPassword)
                console.log(response.data)
                sessionStorage.setItem('userId', loginInfo.userId);
                alert("로그인성공")
                navigate("/setting") // 로그인 후 이동할 페이지로 이동합니다.
            } else {
                alert('로그인에 실패했습니다. 다시 시도해주세요.');
            }

        }
        catch (error) {
            console.error('로그인중 오류:', error);
            alert("아이디와 비밀번호를 확인해주세요.")
        }
    };

    // JSX를 반환하여 화면을 구성합니다.
    return (
        <body>
        <header>
            <div class="banner-header">

                <h2 class="banner-title">MAKEBANNER</h2>

                <div class="charts-see-all">

                    <a class="go-login" onClick={GoLogin}>
                        로그인
                    </a>

                    <a class="go-login" onClick={GoSignup}>
                        회원가입
                    </a>

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
                <form class= "login" onSubmit={handleLogin}>
                    <h1 class= "login-title">로그인</h1>
                    <label>아이디</label>
                    <input
                        name="id"
                        type="text"
                        placeholder="Email 또는 전화번호"
                        onChange={(e) => setId(e.target.value)}
                    />
                    <label>비밀번호</label>
                    <input
                        name="userpassword"
                        type="password"
                        placeholder="비밀번호"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <div class = "login-forgot">
                        <a class="login-forgot-link" href="#">비밀번호를 잊으셨나요?</a>
                    </div>
                    <button class="login-but" onClick={handleLogin}>로그인</button>
                    <div class= "login-signup">
                        회원이 아니신가요?
                        <a class="login-signup-link" onClick={GoSignup}>가입하기</a>
                    </div>
                </form>
            </div>
        </section>
        </body>
    );
};
export default Login;
