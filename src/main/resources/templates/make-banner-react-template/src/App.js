import { BrowserRouter, Routes, Route } from 'react-router-dom';
import React from 'react';
import Login from './login';
import Setting from './setting';
import Mypage from './mypage';
import Signup from './signup'
import Makebanner from "./makebanner";
import Resultbanner from "./resultbanner";
import Main from"./main"
import EditInformation from "./editInformation";

export default function App() {
  return (
      <div className="App">
        <BrowserRouter>
          <Routes>
            <Route path={"/login"} element={<Login />}></Route>
            <Route path={"/setting"} element={<Setting />}></Route>
            <Route path={"/mypage"} element={<Mypage />}></Route>
            <Route path={"/signup"} element={<Signup />}></Route>
            <Route path={"/editorpage"} element={<Makebanner/>}></Route>
            <Route path={"/resultbanner"} element={<Resultbanner/>}></Route>
            <Route path={"/main"} element={<Main/>}></Route>
            <Route path={"editInformation"} element={<EditInformation/>}></Route>
          </Routes>
        </BrowserRouter>
      </div>
  );
};
