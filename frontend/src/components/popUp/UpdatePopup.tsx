import usePopup from "../../features/hook/usePopup"


function UpdatePopup() {

    const { closeUpdateForever } = usePopup()

    return (
        <>
            <div className="h-screen w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center flex items-center justify-center z-40">
                <div className="container bg-white border-hrtColorNewPurple m-6 max-w-lg maxFullHeight">
                    <div className="bg-hrtColorNewPurple border-hrtColorNewPurple mb-4 flex">
                        <div className="flex-auto text-xl text-hrtColorOutline font-bold py-2">⭐ 업 데 이 트 &nbsp; 알 림 ⭐</div>
                    </div>
                    <div className="text-left px-16 py-2">
                        <p className="text-xl">v2.0.10 업데이트 사항</p>
                        <br></br>
                        <p>• 메세지 보내기 버그 해결</p>
                        <p>• 행성하트 지급 완료</p>
                        <p>• 알람 기능 추가</p>
                        <p>• 프로필 수정 페이지 추가</p>
                        <p>• SNS 공유하기 추가</p>
                    </div>
                    <div className="mx-auto my-auto mt-5 mb-4 modal-button text-hrtColorOutline"
                        onClick={() => closeUpdateForever()}>
                        닫기
                    </div>
                </div>
            </div>
        </>
    )
}


export default UpdatePopup
