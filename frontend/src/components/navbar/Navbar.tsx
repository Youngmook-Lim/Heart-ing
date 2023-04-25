import React, { useEffect, useRef, useState } from "react";
import { NavLink } from 'react-router-dom'
import NavbarSidebar from "./NavbarSidebar";

function Navbar() {
  const ref = useRef()
  const [sidebarToggle, setSidebarToggle] = useState(false)

  const onToggleHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
    setSidebarToggle(!sidebarToggle)
  }

  return (
    <nav>
      <div className='flex justify-between'>
        <div>로고</div>
        <div className='flex justify-between'>
          <div>알림</div>
          <div>
            <button 
              className={`${sidebarToggle ? "hidden" : ""}`}
              onClick={onToggleHandler}>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
              </svg>
            </button>
            <div className={`${sidebarToggle ? "" : "hidden"}`}>
              <NavbarSidebar />
            </div>
          </div>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
