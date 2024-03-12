import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'tui-image-editor/dist/tui-image-editor.css';
import { ImageEditor as TuiImageEditor } from '@toast-ui/react-image-editor';
import imgLogo from './static/images/chun__20240309214553.png';
import './static/css/makebanner.css'

const Makebanner = () => {
    const navigate = useNavigate ();
    const sessionSearch = sessionStorage.getItem("userid");

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
        navigate("/setting")
    }
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

    const editorRef = useRef();
    const [editorInstance, setEditorInstance] = useState(null);
    const [originalImageData, setOriginalImageData] = useState(null);

    const ImageEditor = require('tui-image-editor')
    const onUploadImage = async (blob, callback) => {
        // 이미지를 Blob 형식으로 받아와서 처리할 수 있습니다.

        try {
            const fileReader = new FileReader();
            fileReader.onload = function() {
                const base64data = fileReader.result.split(',')[1]; // 데이터의 첫 부분이 헤더인데, 이를 제거합니다.

                // 이미지 데이터를 서버로 전송
                sendImageToServer(base64data);
            };
            fileReader.readAsDataURL(blob);
        } catch (error) {
            console.error('이미지 업로드 중 오류 발생:', error);
        }
    };

    const sendImageToServer = async (base64Image) => {
        try {
            // 이미지 데이터를 서버로 전송
            const response = await axios.post('/api/editorPage', { image: base64Image });
            const imageData = response.data;
            // 응답 데이터 출력
            console.log(response.data);
            navigate("/resultbanner", {originalImageData, additionalData: 'Hello World'});
        } catch (error) {
            console.error('배너 데이터를 제출하는 중 에러 발생:', error);
        }
    };

    useEffect(() => {
        const instance = new ImageEditor(document.querySelector('#tui-image-editor'), {
            includeUI: {
                loadImage: {
                    path: imgLogo,
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
    }, []);

    const handleSave = (event) => {
        event.preventDefault(); // 기본 동작 방지
        try {
            if (editorInstance) {
                // 이미지를 base64로 인코딩하지 않고, 이미지 데이터를 editorInstance에서 직접 가져와서 서버로 전송합니다.
                const imageData = editorInstance.toDataURL();
                setOriginalImageData(imageData);
                sendImageToServer(imageData);
            } else {
                console.error("에디터 인스턴스를 찾을 수 없습니다.");
            }
        } catch (error) {
            console.error('배너 데이터를 제출하는 중 에러 발생:', error);
        }
    };

    return(
        <body>
        <header>
            <div class="banner-header">

                <h2 class="banner-title" onClick={GoMain}>MAKEBANNER</h2>

                <div class="charts-see-all">
                    <a className="go-login" onClick={GoLogout}>
                        로그아웃
                    </a>
                    {/*<a class="go-login" onClick={GoLogin}>*/}
                    {/*    로그인*/}
                    {/*</a>*/}

                    {/*<a class="go-login" onClick={GoSignup}>*/}
                    {/*    회원가입*/}
                    {/*</a>*/}

                    <button class="go-start">
                        <a className="go-go" onClick={GoSetting}>
                            시작하기
                        </a>
                    </button>

                </div>

            </div>
        </header>

        <section>
            <div class="make-banner-container">
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
                <button class="login-but"
                    onClick={handleSave}>결과 보기</button>
            </div>




        </section>

        </body>
    );
};
export default Makebanner;