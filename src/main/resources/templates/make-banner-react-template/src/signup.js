import React, { useEffect, useState } from 'react';
import {  useNavigate } from "react-router-dom";
import axios from 'axios';
import './static/css/signup.css';


const Signup = () => {
    const navigate = useNavigate();
    const GoLogin = () => {
        navigate("/login");
    }
    const GoSignup = () => {
        navigate("/signup");
    }
    const GoSetting = () =>{
        navigate("/setting");
    }
    const sessionSearch = sessionStorage.getItem("userid");
    useEffect(() => {
        if (sessionSearch) { // sessionSearch가 존재한다면
            alert("이미 로그인되어 있습니다"); // 이미 로그인되어 있다는 알림을 띄웁니다
            navigate("/setting");
        }
    }, []); // 의존성 배열 비워짐

    const [username, setUserName] = useState('');
    const [userid, setUserId] = useState('');
    const [userpassword, setUserPassword] = useState('');
    const [usercheckPW, setCheckPw] = useState('');
    const [userCRN, setUserCRN] = useState('');
    const userInfo = { username, userid, userpassword, userCRN };
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]+$/;

    const handleSignup = async(event) => {
        event.preventDefault();
        if (!passwordRegex.test(userpassword)) {
            alert('비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다.');
            return;
        }

        if (userpassword !== usercheckPW) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        try {
            const response = await axios.post('/api/signup', userInfo);

            console.log(response.data);// 성공한 경우 서버 응답을 콘솔에 출력
            if (response.data == 'fail'){
                alert("이미 존재하는 아이디입니다.");
                return;
            }else{
                alert('회원가입이 되었습니다');
                console.log('회원가입이 완료되었습니다.', userInfo);
                console.log(userInfo)
                // 회원가입 후 로그인 페이지로 이동 또는 다른 작업 수행
                return navigate('/login');

            }

        } catch (error) {
            console.error('Error signing up:', error);
        }
    };
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
                <form class= "login" onSubmit={handleSignup}>
                    <h1 class= "login-title">회원가입</h1>

                    <label>이름</label>
                    <input
                        name="username"
                        type="text"
                        placeholder="이름을 입력하세요."
                        onChange={(e) => setUserName(e.target.value)}
                    />

                    <label>아이디</label>
                    <input
                        name="userid"
                        type="text"
                        placeholder="아이디를 입력하세요."
                        onChange={(e) => setUserId(e.target.value)}
                    />
                    <label>비밀번호</label>
                    <input
                        name="userpassword"
                        type="password"
                        placeholder="비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다."
                        onChange={(e) => setUserPassword(e.target.value)}
                    />

                    <label>비밀번호 확인</label>
                    <input
                        name="usercheckPW"
                        type="password"
                        placeholder="다시 비밀번호를 입력하세요."
                        onChange={(e) => setCheckPw(e.target.value)}
                    />

                    <label>사업자등록번호</label>
                    <input
                        name="number"
                        type="text"
                        placeholder="사업자등록번호를 입력하세요."
                        onChange={(e) => setUserCRN(e.target.value)}
                    />

                    <button class="login-but" onClick={handleSignup}>회원가입</button>
                    <div class= "login-signup">
                        회원이신가요?
                        <a class="login-signup-link" href="#">로그인하기</a>
                    </div>
                </form>
            </div>
        </section>

        </body>
    );
};
export default Signup;