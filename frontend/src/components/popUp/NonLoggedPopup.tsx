import { useEffect, useState } from "react"

function NonLoggedPopup() {

    const [isPopupShow, setIsPopupShow] = useState<boolean>(true)
    
    const closePopup = (expireDays: number): void => {
        let expire = new Date()
        expire.setTime(expire.getTime() + (expireDays * 24 * 60 * 60 * 1000))
        localStorage.setItem("popupNoShow", expire.getTime().toString())
    }

    const checkPopupClose = (): boolean => {
        const expireDay = localStorage.getItem("popupNoShow")
        let today = new Date()

        if (today.getTime() > Number(expireDay)) {
             // 이렇게 하면 localStorage에 아무것도 저장되어 있지 않을 경우 null 이므로 0이 되어 true값인 false 반환한다.
            return false // popup을 띄운다. closePopup이 false
        } else {
            return true // popup을 닫는다 closePopup이 true
        }
    }

    const closePopupToday = (): void => {
        closePopup(1)
        setIsPopupShow(false)
    }

    useEffect(() => {
        checkPopupClose() ? setIsPopupShow(false) : setIsPopupShow(true)
    }, [])

    return (
        <>
            {isPopupShow && (
                <div className="h-screen w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center flex  items-center justify-center z-40">
                    <div className="container modal border-hrtColorOutline m-6 max-w-xs maxFullHeight">
                        <div className="modal-header bg-hrtColorOutline border-hrtColorOutline mb-4 flex">
                            <div className="flex-auto">정보</div>
                        </div>
                        <div className="felx justify-center">
                            <div onClick={closePopupToday}>
                                <input type="checkbox" id="check" />
                                <label htmlFor="check">오늘 하루 안 보기</label>
                            </div>
                            <div onClick={() => setIsPopupShow(false)}>
                                <span>닫기</span>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    )
}

export default NonLoggedPopup