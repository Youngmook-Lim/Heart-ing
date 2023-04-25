import React from 'react'
import { NavLink } from 'react-router-dom'

function NavbarSidebar() {
  return (
    <div className='flex flex-col'>
      <NavLink to='/receivedheart' className='block text-left'>마음 영구 보관함</NavLink>
      <NavLink to='/sentheart' className='block text-left'>내가 보낸 마음</NavLink>
      
      <NavLink to='/manual' className='block text-left'>매뉴얼</NavLink>
    </div>
  )
}

export default NavbarSidebar