import React, { useState, useEffect } from 'react';
import {json, useLocation, useNavigate} from "react-router-dom";
import axios from 'axios';
import './static/css/setting.css';



const Makebanner = () => {
    const navigate = useNavigate ();
    const sessionSearch = sessionStorage.getItem("userId");
    const [bannerSubject, setBannerSubject] = useState("");
    const [bannerwidthSize, setwidthSize] = useState("");
    const [bannerheightSize, setheightSize] = useState("");
    const [bannerText, setBannerText] = useState("");
    const [bannerautoText, setautoBannerText] = useState("");
    const [bannerAutoText2, setAutoBannerText2] = useState("");
    const [isDirectInput, setIsDirectInput] = useState(false); // 직접 입력 체크 여부
    const [isAutoInput, setIsAutoInput] = useState(false); // 추천 문구 입력 체크 여부



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
        sessionStorage.removeItem("userId");
        navigate("/login");
    }
    const GoMyPage = () => {
        navigate("/mypage");
    }

    const GoSetting = () =>{
        navigate("/setting")
    }

    const handleDirectInputChange = (e) => {
        setIsDirectInput(e.target.checked);
        if (e.target.checked) {
            setIsAutoInput(false); // 직접 입력 체크 시 추천 문구 입력 체크 해제
            setautoBannerText(""); // 추천 문구 입력값 초기화
        }
    }

    const handleAutoInputChange = (e) => {
        setIsAutoInput(e.target.checked);
        if (e.target.checked) {
            setIsDirectInput(false); // 추천 문구 입력 체크 시 직접 입력 체크 해제
            setBannerText(""); // 직접 입력값 초기화
        }
    }
    async function delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    const handleBannerSubmit = async (event) => {
        event.preventDefault(); // 페이지 리로딩 방지
        try {

            // 서버로 보낼 데이터를 객체로 만듦
            const data = {
                subject: bannerSubject,
                width: bannerwidthSize,
                height: bannerheightSize,
                userId: sessionSearch
            };

            const response = await axios.post('/api/makebanner', data)

            console.log("delay");


            navigate('/editorpage', {
                state: {
                    response: response.data,
                    prompt: bannerSubject,
                    text: bannerText,
                    bannerauto: bannerautoText,
                    width: bannerwidthSize,
                    height: bannerheightSize
                }
            });

        } catch (error) {
            console.error('Error submitting banner data:', error);
        }

    }

    const handleButton = async (event) => {
        event.preventDefault(); // 페이지 리로딩 방지
        try {

            // 서버로 보낼 데이터를 객체로 만듦
            const data = {
                subject: bannerSubject,
                autoText: bannerautoText,
                userId: sessionSearch
            };

            const response = await axios.post('/api/recommendation', data)
            console.log(response.data);
            const res = response.data
            const res2 = res[Object.keys(res)[0]];
            console.log(res2)
            setautoBannerText(String(res2))

        } catch (error) {
            console.error('Error submitting banner data:', error);
        }

    }

    // JSX를 반환하여 화면을 구성합니다.
    return (
        <body>
        <header>
            <div className="banner-header">

                <h2 className="banner-title" onClick={GoMain}>MAKEBANNER</h2>
                <div className="charts-see-all">

                    <a className="go-login" onClick={GoLogout}>
                        로그아웃
                    </a>

                    <a className="go-login" onClick={GoMyPage}>
                        마이페이지
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
            <div className="container1">
                <form className="login" onSubmit={handleBannerSubmit}>
                    <h1 className="login-title">배너 제작 설정</h1>
                    <label className="la-t">광고 주제</label>
                    <input className="in-t"
                           name="subject"
                           type="text"
                           value={bannerSubject}
                           onChange={(e) => setBannerSubject(e.target.value)}
                           placeholder="배너 주제를 입력하세요."
                    />
                    <label className="la-t">배너 크기</label>
                    {/*<input class="in-t"*/}
                    {/*       name="size"*/}
                    {/*       type="text"*/}
                    {/*       value={bannerSize}*/}
                    {/*       onChange={(e) => setBannerSize(e.target.value)}*/}
                    {/*       placeholder="배너 크기를 입력해주세요."*/}
                    {/*/>*/}
                    <div className="size-int-box">
                        <input className="in-t2"
                               name="size"
                               type="text"
                               value={bannerwidthSize}
                               onChange={(e) => setwidthSize(e.target.value)}
                               placeholder="가로길이 입력"
                        />
                        <input className="in-t2"
                               name="size"
                               type="text"
                               value={bannerheightSize}
                               onChange={(e) => setheightSize(e.target.value)}
                               placeholder="세로길이 입력"
                        />
                    </div>


                    <label className="la-t">배너 문구</label>
                    <div className="check_wrap">
                        <input type="checkbox" id="check_btn1" checked={isDirectInput} onChange={handleDirectInputChange}/>
                        <label htmlFor="check_btn1"><span>문구 직접입력</span></label>
                    </div>
                    {isDirectInput && (
                        <textarea className="in-t3"
                               name="text"
                               value={bannerText}
                               onChange={(e) => setBannerText(e.target.value)}
                               placeholder="추천 문구를 직접 입력해주세요."
                        />
                    )}

                    <div className="check_wrap">
                        <input type="checkbox" id="check_btn2" checked={isAutoInput} onChange={handleAutoInputChange}/>
                        <label htmlFor="check_btn2"><span>문구 추천받기</span></label>
                    </div>
                    {isAutoInput && (
                        <div className="check">
                            <div className="in-t4">
                                <p>
                                    {Object.values(bannerautoText)}
                                </p>
                            </div>
                            <button onClick={handleButton}>추천 받기</button>
                        </div>
                    )}




                    <button className="login-but" type='submit' onClick={handleBannerSubmit}>결과 보기</button>
                </form>


                <div className="note">
                    <div className="n-t-l">
                        <p className="n-t">광고 주제 작성법</p>
                    </div>

                    <div className="lines">
                        <p className="s-t">1. 문장이 아니라 키워드로 표시합니다.</p>
                    </div>

                   <div className="lines">
                       <p className="n-e"> ex) sliced,avocado in the box</p>
                   </div>

                    <div className="lines">
                        <p className="s-t">2. , 대신 AND를 사용하면 원하는 이미지 생성 가능성이 높아집니다.</p>
                    </div>

                    <div className="lines">
                        <p className="n-e">ex) sliced AND avocado in the box</p>
                    </div>

                    <div className="lines">
                        <p className="s-t">3. 원하는 단어나 구에 가중치를 부여할 수 있습니다. 가중치를 입력하지 않는다면 1의 가중치를 가지고 있습니다. 본인이 이미지에서 중요하다고 생각되는 단어나 구에 높은 가중치를 부여하면 됩니다.(0.8~1.5)</p>
                    </div>

                    <div className="lines">
                        <p className="n-e">ex) (sliced avocado in the box : 1.5),(on the table : 0.5) </p>

                    </div>
                </div>

            </div>
        </section>

        </body>
    );
};
export default Makebanner;