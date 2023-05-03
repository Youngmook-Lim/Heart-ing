import heart_select from "../../assets/images/png/heart_select.png";

interface ManualSelectBoxSectionImgProps {
  manualImg: number;
}

function ManualSelectBoxSectionImg({
  manualImg,
}: ManualSelectBoxSectionImgProps) {
  const index = manualImg;
  const manualImgList = [
    heart_select,
    heart_select,
    heart_select,
    heart_select,
    heart_select,
    heart_select,
    heart_select,
    heart_select,
    heart_select,
    heart_select,
  ];
  return (
    <>
      <div className="flex justify-center items-center h-full">
        <img
          src={manualImgList[index]}
          alt="설명서 이미지"
          className="h-full"
        />
      </div>
    </>
  );
}

export default ManualSelectBoxSectionImg;
