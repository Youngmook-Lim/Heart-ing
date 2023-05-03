import React from "react";
import NavbarSideContextHeader from "./NavbarSideContextHeader";
import NavbarSideContextBody from "./NavbarSideContextBody";

function NavbarSideContext({...props}) {
  return (
    <div className="flex flex-col h-screen border-2 border-hrtColorOutline ">
      <div className="flex align-middle h-10 bg-hrtColorPink border-b-2 border-hrtColorOutline h-12">
        <NavbarSideContextHeader onOpenHandler={props.onOpenHandler}/>
      </div>
      <div className="flex-1 bg-white border-gray-700">
        <NavbarSideContextBody onOpenHandler={props.onOpenHandler}/>
      </div>
    </div>
  );
}

export default NavbarSideContext;
