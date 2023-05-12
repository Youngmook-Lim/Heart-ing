import { useRecoilState } from "recoil";
import { isUpdateShowAtom } from "../atoms/popupAtoms"
import { getTotalHeartApi } from "../features/api/messageApi";
import ManualHome from "../components/Home/ManualHome";
import UpdatePopup from "../components/popUp/UpdatePopup";
import { useEffect } from "react";

function Home() {
  
  const [isUpdateShow, setIsUpdateShow] = useRecoilState(isUpdateShowAtom)

  async function onGetTotalHeart() {
    const totalCnt = await getTotalHeartApi();
    return totalCnt;
  }

  const checkUpdateRead = () => {
    const readUpdate = localStorage.getItem("readNewUpdate")
    
    if (readUpdate === "true") {
      setIsUpdateShow(false)
    } else {
      setIsUpdateShow(true)
    }
  }

  useEffect(() => {
    checkUpdateRead()
  }, [])
  

  return (
    <>
      {isUpdateShow ? <UpdatePopup /> : null }
      <div className="h-[calc((var(--vh, 1vh) * 100)-8rem)]">
        <ManualHome onGetTotalHeart={onGetTotalHeart} />
      </div>
    </>
  );
}

export default Home;
