import React from "react";
import NavbarSideContextHeader from "./NavbarSideContextHeader";
import NavbarSideContextBody from "./NavbarSideContextBody";

function NavbarSideContext() {
  return (
    <div className="flex flex-col h-screen border-2 border-hrtColorOutline ">
      <div className="flex align-middle h-10 bg-hrtColorPink border-b-2 border-hrtColorOutline h-12">
        <NavbarSideContextHeader />
      </div>
      <div className="flex-1 bg-white border-gray-700">
        <NavbarSideContextBody />
      </div>
    </div>
  );
}

export default NavbarSideContext;
