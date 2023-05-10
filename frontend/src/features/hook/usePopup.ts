import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { isPopupShowAtom, isUpdateShowAtom } from "../../atoms/popupAtoms";

function usePopup() {

    const navigate = useNavigate();
    const setIsPopupShow = useSetRecoilState(isPopupShowAtom);
    const setIsUpdateShow = useSetRecoilState(isUpdateShowAtom);

    const setPopupClosed = (): void => {
        localStorage.setItem("popupNoShow", "true")
    }

    const closePopupForever = (): void => {
        setPopupClosed()
        setIsPopupShow(() => false)
    }

    const goHome = async() => {
        setIsPopupShow(() => false)
        navigate('/')
    }

    const onClosePopup = () => {
        setIsPopupShow(() => false)
    }

    const closeUpdateForever = (): void => {
        localStorage.setItem("readUpdate", "true")
        setIsUpdateShow(() => false)
    }
    
    return {
        closePopupForever,
        goHome,
        onClosePopup,
        closeUpdateForever,
    }
}

export default usePopup
