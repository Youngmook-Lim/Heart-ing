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
             // 이렇게 하면 localStorage에 아무것도 저장되어 있지 않을 경우 undefined 거나 null 이므로 true를 반환한다.
            console.log("뭐가 찍히지?", Number(expireDay)) // 0
            console.log("그럼 이건", expireDay) // null
            console.log("NaN 이야?", isNaN(Number(expireDay))) //false
            console.log("NaN 이야?", isNaN(Number(undefined))) //false
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
                <div>
                    <div onClick={closePopupToday}>
                        <input type="checkbox" id="check" />
                        <label htmlFor="check">오늘 하루 안 보기</label>
                    </div>
                    <div onClick={() => setIsPopupShow(false)}>
                        <span>닫기</span>
                    </div>
                </div>
            )}
        </>
    )
}

export default NonLoggedPopup