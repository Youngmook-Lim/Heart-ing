import React from "react";
import NavbarSide from "./NavbarSide";
import NavbarSideContext from "./NavbarSideContext";

import Logo from "../../assets/images/logo/logo_line.png";

function Navbar() {
  return (
    <div>
      <nav className="">
        <div className={`flex justify-between w-[calc(100%-2.7rem)]`}>
          <div>
            <img src={Logo} alt="test" className="w-16 m-2" />
          </div>
          <div className="w-6 m-2 my-4 flex-none">
            <svg
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
              aria-hidden="true"
            >
              <path
                clipRule="evenodd"
                fillRule="evenodd"
                d="M10 2a6 6 0 00-6 6c0 1.887-.454 3.665-1.257 5.234a.75.75 0 00.515 1.076 32.91 32.91 0 003.256.508 3.5 3.5 0 006.972 0 32.903 32.903 0 003.256-.508.75.75 0 00.515-1.076A11.448 11.448 0 0116 8a6 6 0 00-6-6zM8.05 14.943a33.54 33.54 0 003.9 0 2 2 0 01-3.9 0z"
              ></path>
            </svg>
          </div>
        </div>

        <NavbarSide width={60}>
          <NavbarSideContext />
        </NavbarSide>
      </nav>
    </div>
  );
}

export default Navbar;
