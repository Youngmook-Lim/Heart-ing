import React from 'react'
import NavbarSide from './NavbarSide'
import NavbarSideContext from './NavbarSideContext'

function Navbar() {
  return(
    <div>
      <nav>
      <div className={`flex justify-between w-5/6`}>
        <div>로고</div>
        <div>알림</div>
      </div>
          <NavbarSide width={60}>
            <NavbarSideContext />
          </NavbarSide>
      </nav>
    </div>
  )
}

export default Navbar
