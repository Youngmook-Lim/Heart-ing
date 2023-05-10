import hearting_logo_line from "../../assets/images/logo/logo_line.png"
import suggestion from "../../assets/images/png/suggestion.png"
import heart_send from "../../assets/images/png/heart_select.png";
import heart_sent from "../../assets/images/png/heart_send.png";
import heart_received from "../../assets/images/png/heart_receive.png";
import heart_save from "../../assets/images/png/heart_save.png";
import share from "../../assets/images/png/share.png";
import heart_rainbow_1 from "../../assets/images/png/heart_guide.png";
import condition from "../../assets/images/png/acqConditon.png";

interface ManualSelectBoxSectionImgProps {
  manualImg: number;
}

function ManualSelectBoxSectionImg({
  manualImg,
}: ManualSelectBoxSectionImgProps) {
  const index = manualImg;
  const manualImgList = [
    hearting_logo_line,
    suggestion,
    heart_send,
    heart_sent,
    heart_received,
    heart_save,
    share,
    heart_rainbow_1,
    condition, 
  ];
  return (
    <>
      <div className="text-center mx-auto flex justify-center h-full">
        <div className="h-18">
          <img src={manualImgList[index]} alt="설명서 이미지" className="h-40" />
        </div>
      </div>
    </>
  );
}

export default ManualSelectBoxSectionImg;
