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
                    <div className="ml-5">
                        <div className="flex">
                            <div className="text-left py-2 leading-8">
                                <p className="text-xl pb-2">v3.0 업데이트 사항</p>
                                <p>• 스페셜하트 업데이트</p>
                                <p>• 실시간 알람</p>
                                <p className="text-sm ml-2">(메세지 수신, 반응하기, 스페셜하트 획득)</p>
                                <p>• 악성 메세지 신고하기</p>
                                <p>• 기본 감정 하트별 컬러템플릿 적용</p>
                                <p>• 프로필 수정 페이지 추가</p>
                                <p>• 행성하트 지급 완료</p>
                            </div>
                        </div>
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
