import React from 'react'
import NavbarSideContextHeader from './NavbarSideContextHeader'
import NavbarSideContextBody from './NavbarSideContextBody'

function NavbarSideContext() {
  return (
    <div className='flex flex-col h-screen'>
      <div className='flex align-middle h-10 bg-pink-500 border-x-4 border-gray-700 h-8'>
        <NavbarSideContextHeader />
      </div>
      <div className='flex-1 bg-white border-4 border-gray-700'>
        <NavbarSideContextBody />
      </div>
    </div>
  )
}

export default NavbarSideContext
