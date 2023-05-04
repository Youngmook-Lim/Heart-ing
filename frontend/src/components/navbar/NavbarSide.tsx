import React, { useEffect, useRef, useState } from "react";
import NavbarSideContext from "./NavbarSideContent";

// interface SidebarProps {
//   width?: number;
//   children: React.ReactNode;
// }

function NavbarSide({...props}) {
  const refSidebar = useRef<HTMLDivElement | null>(null);

  const [isOpen, setIsOpen] = useState(false);
  const [xPosition, setXPosition] = useState(-props.width);

  // const onToggleHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
  //   setIsOpen(!isOpen)
  // }

  const onToggleHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
    // console.log(xPosition)
    if (xPosition < 0) {
      setXPosition(0);
      setIsOpen(true);
    } else {
      setXPosition(-props.width);
      setIsOpen(false);
    }
  };

  const onSidebarHandler = async (e: MouseEvent): Promise<void> => {
    // console.log('내자식', children)
    let sideArea = refSidebar.current;
    let sideChildren = refSidebar.current?.contains(e.target as Node);
    // console.log('isOpen은', isOpen, sideArea, sideChildren)
    if (isOpen && !sideArea) {
      await setXPosition(-props.width);
      await setIsOpen(false);
    }
  };

  const onOpenHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    setIsOpen(false)
  }

  useEffect(() => {
    window.addEventListener("click", onSidebarHandler);
    return () => {
      window.removeEventListener("click", onSidebarHandler);
    };
  });

  return (
    <nav>
      <div>
        <button
          // className={`${isOpen ? "hidden" : ""}`}
          onClick={onToggleHandler}
          className="absolute top-0 right-0 px-2 m-2 my-4"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={2.8}
            stroke="currentColor"
            className="w-6 h-6"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
            />
          </svg>
        </button>
        <div
          ref={refSidebar}
          className="flex items-start fixed top-0 bottom-0 right-0 transition duration-400 ease-in-out h-full z-50"
          style={{
            width: `${props.width}vw`,
            height: "100%",
            transform: `translatex(${-xPosition}vw)`,
          }}
        >
          <div className={`${isOpen ? "" : "hidden"} relative w-full`}>
            <NavbarSideContext onOpenHandler={onOpenHandler}/>
            <button
              className="absolute top-0 right-0 px-2"
              onClick={onToggleHandler}
            >
              <svg
                fill="currentColor"
                viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg"
                aria-hidden="true"
                className="w-8 h-8 my-2"
              >
                <path d="M6.28 5.22a.75.75 0 00-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 101.06 1.06L10 11.06l3.72 3.72a.75.75 0 101.06-1.06L11.06 10l3.72-3.72a.75.75 0 00-1.06-1.06L10 8.94 6.28 5.22z"></path>
              </svg>
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default NavbarSide;
