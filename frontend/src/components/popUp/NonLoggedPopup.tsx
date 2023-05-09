import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";

function NonLoggedPopup() {

    const navigate = useNavigate();

    const [isPopupShow, setIsPopupShow] = useState<boolean>(false)
    
    const setPopupClosed = (): void => {
        localStorage.setItem("popupNoShow", "true")
    }

    
    const checkPopupClose = (): boolean => {
        const popupNoShow = localStorage.getItem("popupNoShow")
        
        if (popupNoShow !== "true") {
            return true
        } else {
            return false
        }
    }
    
    const closePopupForever = (): void => {
        setPopupClosed()
        setIsPopupShow(false)
    }

    const goHome = () => {
        navigate('/')
    }

    useEffect(() => {
        checkPopupClose() ? setIsPopupShow(true) : setIsPopupShow(false)
    }, [])

    return (
        <>
            {isPopupShow && (
                <div className="h-screen w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center z-40">
                    <div className="flex flex-col justify-end h-full">
                        <div className="text-4xl textShadow">
                            <p className="purple text-hrtColorYellow">Let's Heart-ing!</p>
                        </div>
                        <div className="container bg-white mx-auto maxFullHeight">
                        <div className="h-8 bg-hrtColorNewPurple"></div>
                            <div className="leading-8 my-8">
                                <p>친구에게 하트가 전달 되었어요!<br></br>
                                나도 하트판을 만들어 볼까요?</p>
                            </div>
                            <div className="mx-auto my-auto mb-8 w-3/5 modal-button text-hrtColorOutline bg-hrtColorYellow border-hrtColorNewPurple"
                                onClick={goHome}>
                                <p>나의 하트판 만들러 가기</p>
                            </div>
                            <div className="flex items-center justify-between m-8">
                                <div className="flex" onClick={closePopupForever}>
                                    <label htmlFor="check" className="mr-2">다시 보지 않기</label>
                                    <input type="checkbox" id="check" />
                                </div>
                                <div onClick={() => setIsPopupShow(false)}>
                                    <span>닫기</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    )
}

export default NonLoggedPopup