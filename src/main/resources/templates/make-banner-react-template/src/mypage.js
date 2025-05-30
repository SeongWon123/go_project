import React, {useEffect, useState} from 'react';
import { useNavigate } from "react-router-dom";
import './static/css/mypage.css'
import axios from "axios";


const Mypage = () => {
    const navigate = useNavigate();
    const sessionSearch = sessionStorage.getItem("userId");
    const [jsonData, setUserData] = useState({});
    const [userData, setUser] = useState({});
    const [imgSeed, setImageSeed] = useState();
    const [imgPath, setImgPath] = useState();
    const [showModal, setShowModal] = useState(false);
    const [showModal2, setShowModal2] = useState(false);
    const [showModal3, setShowModal3] = useState(false);
    const [showModal4, setShowModal4] = useState(false);
    const [width, setWidth] = useState("2");
    const [height, setHeight] = useState("2");
    const [height2, setHeight2] = useState("2");


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

    const updateUser = (event) => {
        event.preventDefault();
        navigate('/editInformation', {state : {data :userData}});

    }

    const deleteUser = async (event) => {
        event.preventDefault();

        try {

            const data = {
                userId : sessionSearch,
            };

            await axios.post('/api/delete', data);
            sessionStorage.removeItem("userId");
            navigate('/login')


        } catch (error) {
            console.error('API 호출 에러:', error);
        }

    }

    const recycle = async (e, data) => {

        const getImgSrc = `${e}`;
        console.log(getImgSrc)
        setImgPath(getImgSrc)
        console.log(getImgSrc)
        const prompt = data.key;
        console.log(prompt)

        try {

            const data2 = {
                imgPath : getImgSrc,
                width : width,
                height : height,
            };

            const response =await axios.post('/api/recycleImg', data2);
            navigate('/editorpage', {state: {
                    response: response.data,
                    text : height2,
                    width : width,
                    height : height,
                    prompt : prompt,
                }})


        } catch (error) {
            console.error('API 호출 에러:', error);
        }

    }

    const deleteBanner = async (e) => {
        const getImgSrc = `${e}`;
        console.log(getImgSrc)

        try {

            const data = {
                imgPath : getImgSrc,
            };

            await axios.post('/api/deleteBanner', data);
            navigate('/mypage')
            window.location.reload()


        } catch (error) {
            console.error('API 호출 에러:', error);
        }

    }

    const handleWidth = (event) => {
        setWidth(event.target.value)
    }

    const handleHeight = (event) => {
        setHeight(event.target.value)
    }

    const handleContent = (event) => {
        setHeight2(event.target.value)
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
                <div className="big-box">
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
                    {/*<div className="box"><div className="box-t">쿠폰</div></div>*/}
                    <div className="but-box">
                        <button className="fix-but2" onClick={deleteUser}>회원탈퇴</button>
                        <button className="fix-but2" onClick={updateUser}>회원정보수정</button>
                    </div>
                </div>


                <div className="use-con">
                    <h2 className="use-title">나의 배너리스트</h2>

                    <div className="list-box">

                        <ul>
                            {Object.keys(jsonData).map((key) => (
                                <li className="li-box" key={key}>
                                    <div className="list-name" >
                                        <h2 className="name">{key}</h2>
                                    </div>
                                    {jsonData[key].map((imgSrc, index) => (
                                        <div className="list-img">
                                            <div key={index} className="ban-box">
                                                <img className="result-imagee" src={imgSrc} alt={`Image ${index}`}/>
                                            </div>
                                            <div className="list-con">
                                                <button className="fix-but1" onClick={() => setShowModal(true)}>재사용</button>
                                                {showModal && (
                                                    <div className="modal">
                                                        <div className="modal-content">
                                                            <p>이미지를 사이즈를 변경하시겠습니까?</p>
                                                            <div>
                                                                <button className="fix-but" onClick={() => setShowModal2(true)}>네</button>
                                                                <button onClick={() => setShowModal3(true)}>아니요</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                )}
                                                {showModal2 && (
                                                    <div className="modal">
                                                        <div className="modal-content">
                                                            <p>이미지 사이즈를 입력해주세요.</p>
                                                            <div>
                                                                <input type="text" placeholder={"가로"} onChange={event => handleWidth(event)}></input>
                                                                <p>px</p>
                                                                <input type="text" placeholder={"세로"} onChange={event => handleHeight(event)}></input>
                                                                <p>px</p>
                                                                {/*<button onClick={e =>recycle(e)} id={"2"} value={jsonData[key]}>확인</button>*/}
                                                                <button onClick={() => setShowModal3(true)}>확인</button>
                                                                {/*onClick={e => handleClick(imageData[key], {key})*/}

                                                                <button onClick={() => setShowModal2(false)}>취소</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                )}

                                                {showModal3 && (
                                                    <div className="modal">
                                                        <div className="modal-content">
                                                            <h3>이미지 문구 작성</h3>
                                                            <div>
                                                                <textarea placeholder="직접 입력하세요" onChange={event => handleContent(event)}></textarea>
                                                                {/*onClick={e => handleClick(imageData[key], {key})*/}
                                                            </div>
                                                            <button>자동 추천</button>
                                                            <button onClick={e => recycle(jsonData[key], {key})}>확인</button>
                                                        </div>
                                                    </div>
                                                )}

                                                <button className="fix-but1" onClick={() => setShowModal4(true)}>삭제</button>
                                                {showModal4 && (
                                                    <div className="modal">
                                                        <div className="modal-content">
                                                            <p>이미지를 삭제하시겠습니까?</p>
                                                            <div>
                                                                <button className="fix-but" onClick={e => deleteBanner(imgSrc)}>네</button>
                                                                <button onClick={() => setShowModal4(false)}>아니요</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                )}
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