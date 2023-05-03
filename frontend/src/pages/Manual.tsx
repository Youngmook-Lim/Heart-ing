import ManualSelectBox from "../components/manual/ManualSelectBox";

function Manual() {
  return (
    <div className="fullHeight">
      <div className="text-3xl textShadow h-22 p-2">
        <p className="text-hrtColorYellow">사용 설명서</p>
      </div>
      <ManualSelectBox />
    </div>
  );
}

export default Manual;
