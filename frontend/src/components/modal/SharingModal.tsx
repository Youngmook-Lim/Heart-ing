import React from 'react'
import ButtonIcon from "../common/ButtonIcon";
import { useSetRecoilState } from 'recoil';
import { sharingAtom } from '../../atoms/userAtoms';
import SharingModalList from './SharingModalList';

function SharingModal() {
  const setSharingAtom = useSetRecoilState(sharingAtom)
  const closeModal = () => {
    setSharingAtom(false);
  };
  
  return (
    <div className="App w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center flex  items-center justify-center z-40">
      <div className="container modal border-hrtColorOutline m-6 max-w-xs">
        <div className="modal-header bg-hrtColorOutline border-hrtColorOutline mb-4 flex">
          <div className="flex-auto">공유하기</div>
          <button onClick={() => closeModal()} className="flex-none">
            <ButtonIcon id={0} />
          </button>
        </div>
        <div className="relative mx-6 flex flex-col items-center">
          <SharingModalList setSharingAtom={setSharingAtom} shareMode={'board'}/>
        </div>
      </div>
    </div>
  )
}

export default SharingModal
