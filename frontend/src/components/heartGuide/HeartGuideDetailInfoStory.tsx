import { useEffect } from "react";

interface HeartGuideDetailInfoStoryProps {
  heartStory: string;
}

function HeartGuideDetailInfoStory({ heartStory }:HeartGuideDetailInfoStoryProps) {

    return (
      <>
        <div>
          <p>스토리</p>
        </div>
        {/* <div>{ heartStory }</div> */}
        <div className="border-2">
          <p>두려움을 이길 용기를 전할 때 태어난 하트</p>
          <p>너의 하늘을 날아봐! 더 높은 세상을 꿈꾸는 상대에게 날개를 달아준다.</p>
        </div>
      </>
    )
    }

export default HeartGuideDetailInfoStory