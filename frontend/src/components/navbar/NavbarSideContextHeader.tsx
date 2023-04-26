import React from 'react'
import { useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue } from 'recoil'
import { isLoginAtom, userNicknameAtom } from '../../atoms/userAtoms'
import { logout } from '../../features/api/userApi'
import { deleteUserInfo, getUserInfo } from '../../features/userInfo'

function NavbarSideContextHeader() {
  const navigate = useNavigate();

  const [isLogin, setIsLogin] = useRecoilState(isLoginAtom)
  const userNickname = useRecoilValue(userNicknameAtom)
  const userId = getUserInfo().userId

  const onLogoutHandler = async (e: React.MouseEvent<SVGSVGElement>) => {
    const status = await logout()
    if (status === 'success') {
      setIsLogin(false)
      deleteUserInfo()
      navigate('/manual')
    }
  }
  const onMyBoardHandler = (e: React.MouseEvent<HTMLSpanElement>) => {
    navigate(`/heartboard/user?id=${userId}`);
  } 
  return (
    <div>
        {isLogin ? 
          <div className='flex justify-between align-middle'>
            <span onClick={onMyBoardHandler}>{userNickname}</span>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6" onClick={onLogoutHandler}>
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0013.5 3h-6a2.25 2.25 0 00-2.25 2.25v13.5A2.25 2.25 0 007.5 21h6a2.25 2.25 0 002.25-2.25V15M12 9l-3 3m0 0l3 3m-3-3h12.75" />
            </svg>
          </div>
        : 
        <span>로그인이 필요합니다</span>}
    </div>
  )
}

export default NavbarSideContextHeader
