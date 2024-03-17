import React, {useEffect, useState} from 'react';
import { useNavigate } from "react-router-dom";
import './static/css/mypage.css';
import axios from "axios";


const Mypage = () => {
    const navigate = useNavigate();
    const sessionSearch = sessionStorage.getItem("userid");
    const [jsonData, setUserData] = useState({});
    const objectData = {};

    const [imageList, setImageList] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {

                const data = {
                    userId: sessionSearch,
                };

                const response = await axios.post('/api/myPage', data);

                setUserData(response.data)

//                 const keys = Object.keys(response.data);
//                 const values = Object.values(response.data);
//
// // 배열을 순회하며 각 key와 value를 출력
//                 keys.forEach((key, index) => {
//                     console.log(`${key}: ${values[index]}`);
//                 });
//                 setTimeout(() => {
//                 }, 1000)

            } catch (error) {
                console.error('API 호출 에러:', error);
            }
        };

        fetchData();
    }, []);
    const GoLogin = () => {
        navigate("/login");
    }
    const GoSignup = () => {
        navigate("/signup");
    }
    const GoSetting = () => {
        navigate("/setting")
    }

    // if (!userData) return null;

    return (
        <body>
        <header>
            <div className="banner-header">
                <h2 className="banner-title">MAKEBANNER</h2>
                <div className="charts-see-all">
                    <a className="go-login" onClick={GoLogin}>로그인</a>
                    <a className="go-login" onClick={GoSignup}>회원가입</a>
                    <button className="go-start">
                        <a className="go-go" onClick={GoSetting}>시작하기</a>
                    </button>
                </div>
            </div>
        </header>
        <section>
            <div className="container">
                <div className="mypage-t">MY PAGE</div>
                <div className="img-con"></div>
                <div className="id">id_@</div>
                <div className="box"><div className="box-t">이름</div></div>
                <div className="box">
                    <div className="box-t">아이디</div>
                    <div className="con">id_@</div>
                </div>
                <div className="box"><div className="box-t">비밀번호</div></div>
                <div className="box"><div className="box-t">사업자등록번</div></div>
                <div className="box"><div className="box-t">쿠폰</div></div>
                <div className="but-box">
                    <button className="fix-but">회원정보수정</button>
                </div>

                <div className="use-con">
                    <h2 className="use-title">나의 배너리스트</h2>

                    <div className="list-box">

                        <ul>
                            {Object.keys(jsonData).map((key, index) => (
                                <li key={index}>
                                    <div className="list-name">
                                        {key}
                                    </div>
                                    <div className="list-img">
                                        <div className="ban-box">
                                            <img className="result-image" src={jsonData[key]} />
                                        </div>
                                    </div>
                                </li>
                            ))}
                        </ul>


                    </div>

                </div>


                </div>

                <div className="App">


                </div>

        </section>
        </body>
    );
};

export default Mypage;