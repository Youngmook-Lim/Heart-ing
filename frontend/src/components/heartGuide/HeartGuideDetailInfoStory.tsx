interface HeartGuideDetailInfoStoryProps {
  heartStory: string;
  heartId: number;
  isLocked: boolean;
}

function HeartGuideDetailInfoStory({ heartStory, heartId, isLocked }:HeartGuideDetailInfoStoryProps) {

    return (
      <>
        <div className="textShadow">
          <p className="purple text-white text-left tracking-wider mx-1 cursor-default">스토리</p>
        </div>
        <div className="border-2 border-hrtColorNewPurple p-4 mt-1 text-start rounded-sm cursor-default whitespace-pre-wrap">
          {heartId === 14 && isLocked ? "??"
          : heartStory }
        </div>
      </>
    )
  }

export default HeartGuideDetailInfoStory
