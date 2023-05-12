import usePopup from "../../features/hook/usePopup"

function NonLoggedPopup() {

    const { closePopupForever, goHome, onClosePopup } = usePopup()

    return (
        <>
            <div className="h-screen w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center z-40">
                <div className="flex flex-col justify-end h-screen">
                    <div className="container pb-10 bg-white mx-auto maxFullHeight">
                    <div className="h-12 bg-hrtColorNewPurple">
                        <div className="text-3xl">
                            <p className="text-hrtColorYellow pt-1">하팅! 시작하기</p>
                        </div>
                    </div>
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
                            <div onClick={onClosePopup}>
                                <span>닫기</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default NonLoggedPopup