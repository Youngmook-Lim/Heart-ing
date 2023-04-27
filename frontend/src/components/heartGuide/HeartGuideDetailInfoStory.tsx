import { useEffect } from "react";

interface HeartGuideDetailInfoStoryProps {
  heartStory: string | null;
}

function HeartGuideDetailInfoStory({ heartStory }:HeartGuideDetailInfoStoryProps) {

    return (
      <>
        <div>
          <p>스토리</p>
        </div>
        <div>{ heartStory }</div>
      </>
    )
    }

export default HeartGuideDetailInfoStory