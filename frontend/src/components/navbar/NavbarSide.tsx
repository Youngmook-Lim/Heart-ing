import React, { useEffect, useRef, useState } from "react";

interface SidebarProps {
  width?: number;
  children: React.ReactNode;
}

function NavbarSide({width=48, children}:SidebarProps) {
  const refSidebar = useRef<HTMLDivElement | null>(null);
  
  const [isOpen, setIsOpen] = useState(false)
  const [xPosition, setXPosition] = useState(-width)

  // const onToggleHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
  //   setIsOpen(!isOpen)
  // }
  
  const onToggleHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
    // console.log(xPosition)
    if (xPosition < 0) {
      setXPosition(0)
      setIsOpen(true)
    } else {
      setXPosition(-width)
      setIsOpen(false)
    }
  }

  const onSidebarHandler = async(e: MouseEvent): Promise<void> => {
    // console.log('내자식', children)
    let sideArea = refSidebar.current
    let sideChildren = refSidebar.current?.contains(e.target as Node)
    // console.log('isOpen은', isOpen, sideArea, sideChildren)
    if (isOpen && !sideArea) {
      await setXPosition(-width)
      await setIsOpen(false)
    }
  }
  
  useEffect(() => {
    window.addEventListener('click', onSidebarHandler)
    return () => {
      window.removeEventListener('click', onSidebarHandler)
    }
  })

  return (
    <nav>
      <div>
          <div ref={refSidebar} className="flex justify-between items-start fixed top-0 bottom-0 right-0 transition duration-400 ease-in-out h-full z-50" style={{width: `${width}vw`, height: '100%', transform: `translatex(${-xPosition}vw)`}}>
            <button 
              // className={`${isOpen ? "hidden" : ""}`}
              onClick={onToggleHandler}
              className="relative left-[-40px] z-50 transition duration-800 ease-in-out overflow-hidden">
                {isOpen ? 
              <span>X</span> :  
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
              </svg>
              }
            </button>
            <div className={`${isOpen ? "" : "hidden"} relative w-full`}>
              {children}
            </div>
          </div>
        </div>
    </nav>
  )
}

export default NavbarSide