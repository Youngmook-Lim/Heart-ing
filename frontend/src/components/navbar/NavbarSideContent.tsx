import React from "react";
import NavbarSideContentHeader from "./NavbarSideContentHeader";
import NavbarSideContentBody from "./NavbarSideContentBody";

function NavbarSideContent({...props}) {
  return (
    <div className="flex flex-col h-screen border-2 border-hrtColorOutline ">
      <div className="flex align-middle h-10 bg-hrtColorPink border-b-2 border-hrtColorOutline h-12">
        <NavbarSideContentHeader onOpenHandler={props.onOpenHandler}/>
      </div>
      <div className="flex-1 bg-white border-gray-700">
        <NavbarSideContentBody onOpenHandler={props.onOpenHandler}/>
      </div>
    </div>
  );
}

export default NavbarSideContent;
