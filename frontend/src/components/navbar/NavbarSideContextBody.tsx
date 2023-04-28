import React from "react";
import { NavLink } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { isLoginAtom } from "../../atoms/userAtoms";

function NavbarSideContextBody() {
  const isLogin = useRecoilValue(isLoginAtom);
  return (
    <div>
      {isLogin ? (
        <div className="m-3 p-2">
          <NavLink to="/receivedheart" className="block text-left text-xl mb-6">
            마음 영구 보관함
          </NavLink>
          <NavLink to="/sentheart" className="block text-left  text-xl my-6">
            내가 보낸 마음
          </NavLink>
          {/* <div className="block text-left  text-xl my-6">내가 보낸 마음</div> */}
          <NavLink to="/heartguide" className="block text-left text-xl my-6">
            마음도감
          </NavLink>
          <NavLink to="/manual" className="block text-left text-xl my-6">
            매뉴얼
          </NavLink>
        </div>
      ) : (
        <div>
          <NavLink to="/heartguide" className="block text-left">
            마음도감
          </NavLink>
          <NavLink to="/manual" className="block text-left">
            매뉴얼
          </NavLink>
        </div>
      )}
    </div>
  );
}

export default NavbarSideContextBody;
