import React, {useEffect, useState} from 'react';
import { useNavigate } from "react-router-dom";
import './static/css/mypage.css'
import axios from "axios";


const Mypage = () => {
    const navigate = useNavigate();
    const sessionSearch = sessionStorage.getItem("userId");
    const [jsonData, setUserData] = useState({});
    const [userData, setUser] = useState({});


    useEffect(() => {
        if (sessionSearch === null) {
            alert("로그인을 해야합니다")
            navigate("/login");
        }

    const fetchData = async () => {
            try {

                const data = {
                    userId: sessionSearch,
                };

                const response = await axios.post('/api/myPage', data);
                const response2 = await axios.post('/api/myPage2', data);
                setUser(response2.data)
                setUserData(response.data)
                console.log(response2.data)
                console.log(response.data)

                setTimeout(() => {
                    console.log("Delayed for 1 second.");
                }, 3000)


            } catch (error) {
                console.error('API 호출 에러:', error);
            }
        };

        fetchData();
    }, [sessionSearch, navigate]);

    const GoEditInformationPage = (event) => {
        event.preventDefault();
        navigate('/editInformation', {state : {data :userData}});

    }

    const deleteUser = async (event) => {
        event.preventDefault();
        await axios.post('/api/delete', sessionSearch)
        navigate('/main');

    }

    const GoSetting = () => {
        navigate("/setting")
    }
    const GoLogout = () => {
        sessionStorage.removeItem("userId");
        navigate("/login");
    }

    const GoMyPage = () => {
        navigate("/mypage");
    }


    // if (!userData) return null;

    return (
        <body>
        <header>
            <div className="banner-header">
                <h2 className="banner-title">MAKEBANNER</h2>
                <div className="charts-see-all">
                    <a className="go-login" onClick={GoLogout}>로그아웃</a>
                    <a className="go-login" onClick={GoMyPage}>마이페이지</a>
                    <button className="go-start">
                        <a className="go-go" onClick={GoSetting}>시작하기</a>
                    </button>
                </div>
            </div>
        </header>
        <section>
            <div className="containerrr">
                <div className="mypage-t">MY PAGE</div>
                <div className="img-con"></div>
                {/*<div className="id">id_@</div>*/}
                <div className="box">
                    <div className="box-t">이름</div>
                    <div className="con">{userData.userName}</div>
                </div>
                <div className="box">
                    <div className="box-t">아이디</div>
                    <div className="con">{userData.userId}</div>
                </div>
                <div className="box">
                    <div className="box-t">비밀번호</div>
                    <div className="con">{userData.userPassword}</div>
                </div>
                <div className="box">
                    <div className="box-t">사업자등록번호</div>
                    <div className="con">{userData.businessNumber}</div>
                </div>
                <div className="box"><div className="box-t">쿠폰</div></div>
                <div className="but-box">
                    <button className="fix-but1" onClick={GoEditInformationPage}>회원탈퇴</button>
                    <button className="fix-but1" onClick={GoEditInformationPage}>회원정보수정</button>
                </div>

                <div className="use-con">
                    <h2 className="use-title">나의 배너리스트</h2>

                    <div className="list-box">

                        <ul>
                            {Object.keys(jsonData).map((key) => (
                                <li key={key}>
                                    <div className="list-name" >
                                        {key}
                                    </div>
                                    {jsonData[key].map((imgSrc, index) => (
                                        <div className="list-img">
                                            <div key={index} className="ban-box">
                                                <img className="result-imagee" src={imgSrc} alt={`Image ${index}`}/>
                                            </div>
                                        </div>
                                    ))}
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