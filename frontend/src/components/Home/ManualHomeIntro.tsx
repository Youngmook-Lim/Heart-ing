import google_login_button from "../../assets/images/social/google.png";

function ManualHomeIntro() {
  return (
    <div className="flex flex-col ">
      <div className="h-1/2">
        <div className="pt-2">
          <span className="flex justify-center items-center my-2">
            <p className="border-b border-hrtColorPink w-1/4 my-4"></p>
            <p className="mx-4 text-sm text-hrtColorPink textShadow-none">
              하팅! 알아보기
            </p>
            <p className="border-b border-hrtColorPink w-1/4 my-4"></p>
          </span>
        </div>
        <span className="text-xl flex justify-center text-white items-center textShadow ">
          <p className="mr-1 white text-hrtColorPink pr-2">
            익명 메시지 서비스
          </p>
          <p className="mr-1 white text-hrtColorPink">하팅!</p>
        </span>
        <span className="text-lg flex justify-center text-white items-center textShadow my-2 ">
          <p className="mr-1 purple pr-2">보낸 마음은</p>
          <p className="mr-1 white text-hrtColorPink  ">24시간</p>
          <p className="mr-1 purple">이 지나면 사라져요</p>
        </span>
      </div>
      <div className="flex flex-col items-center mx-8">
        익명 메시지이고 24시간임을 알려주는 이미지
        <img
          src={google_login_button}
          alt="google_login_button"
          className="p-2"
        />
      </div>

      <div className="h-1/2">
        <span className="text-lg flex justify-center text-white items-center textShadow my-2 ">
          <p className="mr-1 purple pr-2">나의 하트 수신함을 만들어</p>
        </span>
        <span className="text-lg flex justify-center text-white items-center textShadow my-2 ">
          <p className="mr-1 purple pr-2">친구들에게</p>
          <p className="mr-1 white text-hrtColorPink  ">공유</p>
          <p className="mr-1 purple">해보세요!</p>
        </span>
      </div>
      <div className="flex flex-col items-center mx-8">
        다양한 하트가 모여진 개발팀 페이지 이미지
        <img
          src={google_login_button}
          alt="google_login_button"
          className="p-2"
        />
      </div>
      <a
        className="bg-white mx-8 rounded-xl border-2 border-hrtColorPink"
        href="https://heart-ing.com/heartboard/user?id=3yqolax1ee"
      >
        <p className="py-3 text-hrtColorPink">개발팀의 하트 수신함 보러가기</p>
      </a>
    </div>
  );
}

export default ManualHomeIntro;
