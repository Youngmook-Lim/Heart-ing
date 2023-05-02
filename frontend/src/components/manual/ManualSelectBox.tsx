import React, { useState } from 'react';
import ManualSelectBoxSection from "./ManualSelectBoxSection"

function ManualSelectBox() {

    const [selectedOption, setSelectedOption] = useState(0);

    const tabArr = [
        [{ name: '하팅!이 뭐에요?', content: ""},
        { name: '요구사항', content: "" },],
        [{ name: '하트전달', content: ""},
        { name: '보낸하트', content: "" },
        { name: '받은하트', content: "" },
        { name: '저장소', content: "" },],
        [{ name: '공유하기', content: "" },],
        [{ name: '스페셜하트', content: "" },
        { name: '획득과제', content: "" },],
    ]

    const handleChangeOptioin = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedOption(Number(event.target.value))
    }

    return (
        <div className="flex flex-col items-center">
            <div className="w-5/6 h-20 mx-auto my-auto bg-hrtColorPink rounded-lg flex flex-col justify-center items-center">
                <select className="w-2/3 h-6 bg-white text-center mt-1 mb-2"
                value={selectedOption} onChange={handleChangeOptioin}>
                    <option value={0}>하팅!?</option>
                    <option value={1}>메시지</option>
                    <option value={2}>공유하기</option>
                    <option value={3}>도감</option>
                </select>
                <div className="">
                    {tabArr[selectedOption].map((el, index) => (
                    <span className="text-white text-base mx-2" key={index}>{el.name}</span>))}
                </div>
            </div>
            <div className="w-5/6 h-[500px] mx-auto items-center m-2 bg-white bg-opacity-80 rounded-lg">
                <ManualSelectBoxSection />
            </div>
        </div>
    )
}

export default ManualSelectBox