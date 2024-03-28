import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import './static/css/editInformation.css';
import axios from "axios";

const EditInformation = () => {
    const navigate = useNavigate();
    const sessionSearch = sessionStorage.getItem("userid");
    const [showModal, setShowModal] = useState(false);

    const GoMain = () => {
        navigate("/main");
    }

    const GoLogout = () => {
        sessionStorage.removeItem("userid");
        navigate("/login");
    }

    const [name, setName] = useState("홍길동"); // 이름 상태
    const [password, setPassword] = useState("123456"); // 비밀번호 상태
    const [registrationNumber, setRegistrationNumber] = useState("1234596798"); // 사업자등록번호 상태

    const [isEditingName, setIsEditingName] = useState(false); // 이름 수정 상태
    const [isEditingPassword, setIsEditingPassword] = useState(false); // 비밀번호 수정 상태
    const [isEditingRegistrationNumber, setIsEditingRegistrationNumber] = useState(false); // 사업자등록번호 수정 상태

    const handleNameChange = (event) => {
        setName(event.target.value);
    }

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleRegistrationNumberChange = (event) => {
        setRegistrationNumber(event.target.value);
    }

    const handleNameEdit = () => {
        setIsEditingName(!isEditingName);
        if (!isEditingName) {
            setName(""); // 이름 값 비우기
        }
    }

    const handlePasswordEdit = () => {
        setIsEditingPassword(!isEditingPassword);
        if (!isEditingPassword) {
            setPassword(""); // 비밀번호 값 비우기
        }
    }

    const handleRegistrationNumberEdit = () => {
        setIsEditingRegistrationNumber(!isEditingRegistrationNumber);
        if (!isEditingRegistrationNumber) {
            setRegistrationNumber(""); // 사업자등록번호 값 비우기
        }
    }

    const handleConfirmation = () => {
        // 여기에 회원 정보 수정 완료 처리 로직을 추가할 수 있습니다.
        setShowModal(false); // 모달 닫기
    }

    return (
        <body>
        <header>
            <div className="banner-header">
                <h2 className="banner-title" onClick={GoMain}>MAKEBANNER</h2>
                <div className="charts-see-all">
                    <a className="go-login" onClick={GoLogout}>
                        로그아웃
                    </a>
                    <a className="go-login" href="/mypage">
                        마이페이지
                    </a>
                    <button className="go-start">
                        <a className="go-go" href="/setting">
                            시작하기
                        </a>
                    </button>
                </div>
            </div>
        </header>
        <section>
            <div className="container">
                <div className="mypage-t">MY PAGE</div>
                <div className="img-con">
                    <img src="/image/free-icon-user-2663969.png" alt="user avatar"></img>
                </div>
                <div className="id">id_@</div>

                <div className="box">
                    <div className="box-t">이름</div>
                    {isEditingName ? (
                        <input type="text" value={name} onChange={handleNameChange} />
                    ) : (
                        <div className="con">{name}</div>
                    )}
                    <button className="edit-but" onClick={handleNameEdit}>
                        {isEditingName ? '수정 완료' : '이름 수정'}
                    </button>
                </div>

                <div className="box">
                    <div className="box-t">비밀번호</div>
                    {isEditingPassword ? (
                        <input type="text" value={password} onChange={handlePasswordChange} />
                    ) : (
                        <div className="con">{password}</div>
                    )}
                    <button className="edit-but" onClick={handlePasswordEdit}>
                        {isEditingPassword ? '수정 완료' : '비밀번호 수정'}
                    </button>
                </div>

                <div className="box">
                    <div className="box-t">사업자등록번호</div>
                    {isEditingRegistrationNumber ? (
                        <input type="text" value={registrationNumber} onChange={handleRegistrationNumberChange} />
                    ) : (
                        <div className="con">{registrationNumber}</div>
                    )}
                    <button className="edit-but" onClick={handleRegistrationNumberEdit}>
                        {isEditingRegistrationNumber ? '수정 완료' : '사업자등록번호 수정'}
                    </button>
                </div>

                <div className="but-box">
                    <button className="fix-but" onClick={() => setShowModal(true)}>
                        회원정보 수정 완료
                    </button>
                </div>

                {/* 모달 */}
                {showModal && (
                    <div className="modal">
                        <div className="modal-content">
                            <p>수정을 다하셨습니까?</p>
                            <div>
                                <button onClick={handleConfirmation}>확인</button>
                                <button onClick={() => setShowModal(false)}>취소</button>
                            </div>
                        </div>
                    </div>
                )}

            </div>
        </section>
        </body>
    )
};

export default EditInformation;
