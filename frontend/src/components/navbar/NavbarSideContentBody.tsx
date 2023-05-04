import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import { isLoginAtom } from "../../atoms/userAtoms";
import { deleteUserInfo, getUserInfo } from "../../features/userInfo";
import NavbarSideContentHeaderProfile from "./NavbarSideContentHeaderProfile";
import { logout } from "../../features/api/userApi";

function NavbarSideContentBody({...props}) {
  const navigate = useNavigate();

  const [isLogin, setIsLogin] = useRecoilState(isLoginAtom);
  const [isSetting, setIsSetting] = useState(false)

  const onSettingHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    setIsSetting(!isSetting)
  }

  const onLogoutHandler = async (e: React.MouseEvent<HTMLDivElement>) => {
    const status = await logout();
    if (status === "success") {
      setIsLogin(false);
      deleteUserInfo();
      navigate("/");
    }
  };

  return (
    <div>
      {isLogin ? (
        <div className="m-3 p-2">
          <NavLink to={`/heartboard/user?id=${getUserInfo().userId}`} className="block text-left text-xl mb-6" onClick={props.onOpenHandler}>
            받은 하트
          </NavLink>
          <NavLink to="/sentheart" className="block text-left  text-xl my-6" onClick={props.onOpenHandler}>
            보낸 하트
          </NavLink>
          <NavLink to="/receivedheart" className="block text-left text-xl mb-6" onClick={props.onOpenHandler}>
            하트저장소
          </NavLink>
          <NavLink to="/heartguide" className="block text-left text-xl my-6" onClick={props.onOpenHandler}>
            하트 도감
          </NavLink>
          <NavLink to="/manual" className="block text-left text-xl my-6" onClick={props.onOpenHandler}>
            사용설명서
          </NavLink>
          <div className="block text-left text-xl my-6" onClick={onLogoutHandler}>
          로그아웃
        </div>
        <div className="block text-left text-xl my-6" onClick={onSettingHandler}>
          프로필수정
        </div>
        { isSetting ?
          <NavbarSideContentHeaderProfile setIsSetting={setIsSetting} />
          :
          null
        }
        </div>
      ) : (
        <div className="m-3 p-2">
          <NavLink to="/heartguide" className="block text-left text-xl my-6" onClick={props.onOpenHandler}>
            하트 도감
          </NavLink>
          <NavLink to="/manual" className="block text-left text-xl my-6" onClick={props.onOpenHandler}>
            사용설명서
          </NavLink>
        </div>
      )}
      <div className="m-3 p-2">

        </div>
    </div>
  );
}

export default NavbarSideContentBody;
