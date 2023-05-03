import React from "react";
import { NavLink } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { isLoginAtom } from "../../atoms/userAtoms";

function NavbarSideContextBody({...props}) {
  const isLogin = useRecoilValue(isLoginAtom);
  return (
    <div>
      {isLogin ? (
        <div className="m-3 p-2">
          <NavLink to="/receivedheart" className="block text-left text-xl mb-6">
            받은 하트
          </NavLink>
          <NavLink to="/sentheart" className="block text-left  text-xl my-6">
            보낸 하트
          </NavLink>
          <NavLink to="/receivedheart" className="block text-left text-xl mb-6">
            하트저장소
          </NavLink>
          <NavLink to="/heartguide" className="block text-left text-xl my-6">
            하트 도감
          </NavLink>
          <NavLink to="/manual" className="block text-left text-xl my-6">
            사용설명서
          </NavLink>
        </div>
      ) : (
        <div>
          <NavLink to="/heartguide" className="block text-left text-xl my-6">
            하트 도감
          </NavLink>
          <NavLink to="/manual" className="block text-left text-xl my-6">
            사용설명서
          </NavLink>
        </div>
      )}
    </div>
  );
}

export default NavbarSideContextBody;
