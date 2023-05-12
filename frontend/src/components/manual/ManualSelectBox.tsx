import React, { useState } from "react";
import ManualSelectBoxSection from "./ManualSelectBoxSection";

function ManualSelectBox() {
  const [selectedOption, setSelectedOption] = useState(0);
  const [selectedTabIndex, setSelectedTabIndex] = useState(0);

  const tabArr = [
    [
      {
        imgId: 0,
        name: "하팅!은요~",
        content:
          "익명의 메세지 전달 서비스입니다. 하트에 전달고싶은 감정, 마음을 담아 상대방에게 전달해보세요!",
      },
      {
        imgId: 1,
        name: "건의사항",
        content:
          "하팅!을 이용하시면서 건의하시고 싶은 것이나 불편한 사항이 있다면 설문 조사나 하팅 공식 sns로 건의사항을 보내주세요. 하팅!의 개발진들은 여러분의 불편함을 개선하고자 항상 노력하겠습니다:)",
      },
    ],
    [
      {
        imgId: 2,
        name: "하트전달",
        content:
          "상대방을 향한 감정을 선택한 후, 전달하고 싶은 메세지가 있다면 함께 보내보세요. 전달한 하트는 24시간만 유지됩니다!",
      },
      {
        imgId: 3,
        name: "보낸하트",
        content:
          "내가 보낸 하트들을 확인해보세요! 상대가 내 하트에 이모지를 달았다면 보낸 하트에서 반응을 볼 수 있습니다. 그러나 보낸 메시지들도 24시간 뒤에 사라진다는 것을 주의하세요!",
      },
      {
        imgId: 4,
        name: "받은하트",
        content:
          "내가 받은 하트들을 볼 수 있습니다. 익명의 상대가 보낸 하트들을 눌러 하트의 메시지를 확인해보세요~! 이모티콘으로 메시지에 대한 반응도 남겨 줄 수 있습니다.",
      },
      {
        imgId: 5,
        name: "저장소",
        content:
          "24시간 뒤에도 사라지지 않았으면 하는 하트가 있다면 저장을 눌러보세요! 저장소의 하트는 영원히 간직 할 수 있습니다.",
      },
    ],
    [
      {
        imgId: 6,
        name: "공유하기",
        content:
          "간단하고 다양한 방법으로 친구들에게 나의 하트판을 공유할 수 있습니다. 카카오톡 공유하기, url을 공유하여 많은 사람들과 하트를 주고받아 보세요!",
      },
    ],
    [
      {
        imgId: 7,
        name: "스페셜하트",
        content:
          "하팅!에는 감정하트 외에도 스페셜 하트들이 있습니다. 획득한 스페셜 하트들은 전달 할 수 있습니다. 다양한 스페셜 하트를 모아보세요~!",
      },
      {
        imgId: 8,
        name: "획득과제",
        content:
          "스페셜 하트의 잠금을 해제하기 위해서는 획득 과제를 수행해야합니다. 잠금된 하트들을 눌러 스페셜 하트를 얻기 위한 획득과제를 확인해보세요! 하트들을 주고 받다보면 어느새 획득과제를 달성했을 수도 있어요~!",
      },
    ],
  ];

  const handleChangeOptioin = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedOption(Number(event.target.value));
    setSelectedTabIndex(0);
  };

  const handleTabClick = (index: number) => {
    setSelectedTabIndex(index);
  };

  return (
    <div className="flex flex-col items-center manualHeight">
      <div className="h-24 w-5/6 bg-hrtColorPink rounded-lg flex flex-col justify-center items-center">
        <div className="w-5/6 flex justify-center">
          <select
            className="w-2/3 bg-white text-center items-center mt-1 mb-2"
            value={selectedOption}
            onChange={handleChangeOptioin}
          >
            <option value={0}>하팅!?</option>
            <option value={1}>메시지</option>
            <option value={2}>공유하기</option>
            <option value={3}>도감</option>
          </select>
        </div>
        <div className="textShadow">
          {tabArr[selectedOption].map((el, index) => (
            <span
              className={`text-base mx-2 cursor-pointer text-white ${selectedTabIndex === index ? 'purple' : ''}`}
              key={index}
              onClick={() => handleTabClick(index)}
            >
              {el.name}
            </span>
          ))}
        </div>
      </div>
      <div className="h-5/6 w-5/6 mx-auto items-center m-2 bg-white bg-opacity-80 rounded-lg">
        <ManualSelectBoxSection
          name={tabArr[selectedOption][selectedTabIndex].name}
          content={tabArr[selectedOption][selectedTabIndex].content}
          manualImg={tabArr[selectedOption][selectedTabIndex].imgId}
        />
      </div>
    </div>
  );
}

export default ManualSelectBox;
