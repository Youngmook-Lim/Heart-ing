import React from "react";
import NavbarSideContentHeader from "./NavbarSideContentHeader";
import NavbarSideContentBody from "./NavbarSideContentBody";
import NavbarSideContentFooter from "./NavbarSideContentFooter";

function NavbarSideContent({...props}) {
  return (
    <div className="flex flex-col h-screen border-2 border-hrtColorOutline ">
      <div className="flex align-middle bg-hrtColorPink border-b-2 border-hrtColorOutline h-12">
        <NavbarSideContentHeader onOpenHandler={props.onOpenHandler}/>
      </div>
      <div className="flex-1 bg-white border-gray-700 relative">
        <NavbarSideContentBody onOpenHandler={props.onOpenHandler} setIsSetting={props.setIsSetting}/>
        <div className="absolute w-full">
          <NavbarSideContentFooter />
        </div>
      </div>
    </div>
  );
}

export default NavbarSideContent;
