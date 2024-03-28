import React, { useState, useEffect, useRef } from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import axios from 'axios';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'tui-image-editor/dist/tui-image-editor.css';
import './static/css/makebanner.css'
import {GridLoader} from 'react-spinners';


const Makebanner = () => {
    const navigate = useNavigate ();
    const [loding, setLoding] = useState(true);

    const sessionSearch = sessionStorage.getItem("userId");
    const location = useLocation();
    const prompt = location.state?.prompt;
    const text = location.state?.text;
    const autoText = location.state?.autotext;
    const imageData = location.state?.response || {};
    const width = location.state?.width;
    const height = location.state?.height;
    const [imageSrc, setImageSrc] = useState();
    const [seed, setSeed] = useState();
    const [imagesLoaded, setImagesLoaded] = useState(false);
    console.log(imageData)


    const GoMain = () => {
        navigate("/main");
    }
    const GoSetting = () =>{
        navigate("/setting")
    }
    useEffect(() => {
        if (sessionSearch === null) {
            alert("로그인을 해야합니다")
            navigate("/login");
        }
    }, [sessionSearch, navigate]);

    const GoLogout = () => {
        sessionStorage.removeItem("userId");
        navigate("/main");
    }

    const editorRef = useRef();
    const [editorInstance, setEditorInstance] = useState(null);

    const ImageEditor = require('tui-image-editor')

    const handleClick = (e,data) => {
        console.log(e)
        const getImgSrc = `${e}`;
        setImageSrc(getImgSrc);
        console.log(data.key)
        setSeed(data.key)
    };

    async function delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    const onUploadImage = async (blob, callback) => {//없어도됨
        // 이미지를 Blob 형식으로 받아와서 처리할 수 있습니다.

        try {
            const fileReader = new FileReader();
            fileReader.onload = function() {
                const base64data = fileReader.result.split(',')[1]; // 데이터의 첫 부분이 헤더인데, 이를 제거합니다.

                // 이미지 데이터를 서버로 전송
                sendImageToServer(base64data.json);
            };
            fileReader.readAsDataURL(blob);
        } catch (error) {
            console.error('이미지 업로드 중 오류 발생:', error);
        }
    };//없어도됨

    const sendImageToServer = async (e) => {

        try {

            const data = {
                userId: sessionSearch,
                prompt: prompt,
                filename: imageSrc,
                seed :  seed,
                width: width,
                height: height
            };

            // 이미지 데이터를 서버로 전송
            const response = await axios.post('/api/editorPage', data);
            const res = response.data

            setTimeout(() => {
                console.log(res);
            }, 5000)

            navigate('/resultbanner', {state : {path : res}});


        } catch (error) {
            console.error('배너 데이터를 제출하는 중 에러 발생:', error);
        }
    };

    const loadImageCallback = () => {
        const instance = new ImageEditor(document.querySelector('#tui-image-editor'), {
            includeUI: {
                loadImage: {
                    path: imageSrc,
                    name: 'dkfl',
                },
                menuBarPosition: 'bottom',
            },
            cssMaxWidth: 700,
            cssMaxHeight: 500,
            selectionStyle: {
                cornerSize: 20,
                rotatingPointOffset: 70,
            },
        });
        setEditorInstance(instance);
    };

    async function delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }



    useEffect(() => {

        if (Object.keys(imageData).length === 0) {
            return;
        }



        loadImageCallback();
        setImagesLoaded(true);


    }, [imageData,imageSrc]);

    const handleSave = (event) => {
        event.preventDefault(); // 기본 동작 방지
        try {
            if (editorInstance) {
                // 이미지를 base64로 인코딩하지 않고, 이미지 데이터를 editorInstance에서 직접 가져와서 서버로 전송합니다.
                const imageData = editorInstance.toDataURL();
                dowmloadImage(imageData);
                sendImageToServer(imageData);
            }
        } catch (error) {
            console.error('배너 데이터를 제출하는 중 에러 발생:', error);
        }
    };
    const dowmloadImage =(base64Image) => {
        const link = document.createElement('a')
        const resultPath = String(imageSrc).replace('\\result\\', '')
        link.href = base64Image; //
        link.download = resultPath;
        link.setAttribute('target', '_blank');
        link.setAttribute('download', resultPath);

        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    const handleImageLoad = () => {
        setImagesLoaded(true);
    }

    return(
        <body>
            {!imagesLoaded && <div>Loading...</div>}
        <header>
            <div className="banner-header">

                <h2 className="banner-title" onClick={GoMain}>MAKEBANNER</h2>

                <div className="charts-see-all">
                    <a className="go-login" onClick={GoLogout}>
                        로그아웃
                    </a>
                    {/*<a class="go-login" onClick={GoLogin}>*/}
                    {/*    로그인*/}
                    {/*</a>*/}

                    {/*<a class="go-login" onClick={GoSignup}>*/}
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
            <div className="make-banner-container">
                <p className="login-title" > {text} {autoText}</p>
                {/* <Editor
                    initialValue="hello react editor world!"
                    previewStyle="vertical"
                    height="600px"
                    initialEditType="wysiwyg"
                    useCommandShortcut={false}
                /> */}
                <div id="tui-image-editor"
                     ref={editorRef}
                     hooks={{
                         addImageBlobHook: onUploadImage
                          }}
                         // initialValue={initContents}
                         // onChange={(e) => setUsermake(e.target.value)}
                     >
                </div>
                <button className="login-but"
                    onClick={handleSave}>결과 보기</button>

                <div className="img-list">

                    <ul>
                        {Object.keys(imageData).map((key, index) => (
                                <li>
                                    <div className="result-image">

                                        <img className="img-zip" src={imageData[key]} loading="lazy" onLoad={handleImageLoad} onClick={e => handleClick(imageData[key], {key})}/>

                                    </div>

                                </li>
                        ))}
                    </ul>

                </div>


            </div>


        </section>

        </body>
    );
};
export default Makebanner;