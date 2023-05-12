import { useState } from "react"
import close_yellow from "../../assets/images/png/close_yellow.png"
import { isOpenReportingAtom } from "../../atoms/messageAtoms"
import { useSetRecoilState } from "recoil";

interface propsType {
    onReportMessage: (content: string) => Promise<void>,
}

function Reporting({ onReportMessage }: propsType) {

    const [content, setContent] = useState("");
    const [countContent, setCountContent] = useState(0);
    const setIsOpenReporting = useSetRecoilState(isOpenReportingAtom)

    const onContentHandler = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        if (e.currentTarget.value.length > 500) {
            const currentContent = e.currentTarget.value.substr(0, 500);
            setContent(currentContent);
            setCountContent(500);
        } else {
            const currentContent = e.currentTarget.value;
            setContent(currentContent);
            setCountContent(e.currentTarget.value.length);
        }
    };

    const onCloseReporting = () => {
        setIsOpenReporting(false)
    }

    const onReportMessageHandler = async() => {
        if (content !== null) {
            if (window.confirm("정말 신고하시겠습니까?")) {
                await onReportMessage(content)
            } else {
                onCloseReporting()
            }
        } else {
            alert("신고사유를 입력해주세요.")
        }
    }

    return (
        <>
        <div className="h-screen w-full fixed left-0 top-0 bg-black bg-opacity-30 text-center flex items-center justify-center z-40">
            <div className="container bg-white border-2 border-hrtColorNewRed m-10 max-w-xs maxFullHeight">
                <div className="bg-hrtColorNewRed border-hrtColorNewRed flex">
                    <div className="flex-auto text-left text-white text-xl mx-2 my-1 cursor-default">신고</div>
                    <button className="flex-none w-6 m-1 mr-2 cursor-pointer" onClick={onCloseReporting}>
                        <img src={close_yellow} alt="close button" />
                    </button>
                    </div>
                    <div className="mx-7 mt-5">
                        <div className="flex text-left cursor-default">신고사유</div>
                        <div className="pt-2 text-right">
                            <textarea
                                value={content}
                                className="block w-full h-40 text-center mx-auto p-2 border-2 border-hrtColorOutline outline-none rounded-md resize-none"
                                onChange={onContentHandler}
                                placeholder="신고 사유를 입력해주세요"
                            />
                            <span className="text-gray-400 mr-1 cursor-default">{countContent}/500</span>
                        </div>
                            <div className="text-left mb-2">
                            <svg
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            strokeWidth="1.5"
                            stroke="currentColor"
                            className="inline w-4 h-4 text-hrtColorNewRed"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z"
                                />
                                </svg>
                            <span className="text-hrtColorNewRed cursor-default">주의</span>
                            <div className="text-2xs text-hrtColorOutline my-1 cursor-default">
                                <p>신고 누적될 경우, 해당 계정은 정지됩니다.<br></br>
                                    접수 후에는 취소할 수 없으니 신중히 사용해주세요.</p>
                            </div>
                            </div>
                    </div>
                    <div className="flex mx-7 mb-2">
                        <div className="mx-auto my-auto mt-5 mb-4 mr-4 modal-button text-hrtColorOutline cursor-pointer" onClick={onCloseReporting}>
                            취소
                            </div>
                            <div className="mx-auto my-auto mt-5 mb-4 modal-button bg-hrtColorNewRed text-white cursor-pointer" onClick={onReportMessageHandler}>
                            신고
                        </div>
                    </div>
            </div>
        </div>
        </>
    )
}

export default Reporting