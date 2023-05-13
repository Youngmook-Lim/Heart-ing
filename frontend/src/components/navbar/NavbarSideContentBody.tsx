import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { isLoginAtom } from "../../atoms/userAtoms";
import { deleteUserInfo, getUserInfo } from "../../features/userInfo";
import { logout } from "../../features/api/userApi";

function NavbarSideContentBody({ ...props }) {
  const navigate = useNavigate();

  const [isLogin, setIsLogin] = useRecoilState(isLoginAtom);

  const onLogoutHandler = async (e: React.MouseEvent<HTMLDivElement>) => {
    const status = await logout();
    if (status === "success") {
      setIsLogin(false);
      deleteUserInfo();
      navigate("/");
      props.onOpenHandler();
    }
  };

  return (
    <div>
      {isLogin ? (
        <div className="m-3 p-2">
          <NavLink
            to={`/heartboard/user?id=${getUserInfo().userId}`}
            className="block text-left text-xl mb-4"
            onClick={props.onOpenHandler}
          >
            받은 하트
          </NavLink>
          <NavLink
            to="/sentheart"
            className="block text-left  text-xl my-4"
            onClick={props.onOpenHandler}
          >
            보낸 하트
          </NavLink>
          <NavLink
            to="/receivedheart"
            className="block text-left text-xl mb-4"
            onClick={props.onOpenHandler}
          >
            하트저장소
          </NavLink>
          <NavLink
            to="/heartguide"
            className="block text-left text-xl my-4"
            onClick={props.onOpenHandler}
          >
            하트 도감
          </NavLink>
          <NavLink
            to="/manual"
            className="block text-left text-xl my-4"
            onClick={props.onOpenHandler}
          >
            사용설명서
          </NavLink>
          <NavLink
            to="/hearttest"
            className="block text-left text-xl my-4"
            onClick={props.onOpenHandler}
          >
            하트테스트
          </NavLink>
          <hr />
          <NavLink
            to="/profilesettings"
            className="block text-left text-xl my-4"
            onClick={props.onOpenHandler}
          >
            프로필설정
          </NavLink>
          <div
            className="block text-left text-xl my-4 cursor-pointer"
            onClick={onLogoutHandler}
          >
            로그아웃
          </div>
        </div>
      ) : (
        <div className="mb-3 mx-3 p-2">
          <NavLink
            to="/"
            className="block text-left text-xl my-4 cursor-pointer"
            onClick={props.onOpenHandler}
          >
            로그인
          </NavLink>
          <NavLink
            to="/heartguide"
            className="block text-left text-xl my-6"
            onClick={props.onOpenHandler}
          >
            하트 도감
          </NavLink>
          <NavLink
            to="/manual"
            className="block text-left text-xl my-6"
            onClick={props.onOpenHandler}
          >
            사용설명서
          </NavLink>
          <NavLink
            to="/hearttest"
            className="block text-left text-xl my-4"
            onClick={props.onOpenHandler}
          >
            하트테스트
          </NavLink>
        </div>
      )}
      <div className="m-3 p-2"></div>
    </div>
  );
}

export default NavbarSideContentBody;
