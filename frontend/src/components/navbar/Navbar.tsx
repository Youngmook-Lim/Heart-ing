import React from 'react'
import { NavLink } from 'react-router-dom'

function Navbar() {
  return (
    <div>
      사이드바가 계속 고정으로 있어서 넣었습니다...
      <NavLink to='/sentheart'>보낸메세지</NavLink>
      <NavLink to='/receivedheart'>받은메세지</NavLink>
      <NavLink to='/manual'>매뉴얼</NavLink>
    </div>
  )
}

export default Navbar
