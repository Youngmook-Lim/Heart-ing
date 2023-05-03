interface HeartGuideDetailInfoStoryProps {
  heartStory: string;
}

function HeartGuideDetailInfoStory({ heartStory }:HeartGuideDetailInfoStoryProps) {

    return (
      <>
        <div className="textShadow">
          <p className="purple text-white text-left tracking-wider mx-1">스토리</p>
        </div>
        <div className="border-2 border-hrtColorNewPurple p-4 mt-1 text-start rounded-sm">{ heartStory }</div>
      </>
    )
    }

export default HeartGuideDetailInfoStory