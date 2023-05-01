import heart_select from '../../assets/images/png/heart_select.png'

function ManualSection() {
  const tabArr = [
    { name: '메세지', content: ""},
    { name: '공유하기', content: "" },
    { name: '마음도감', content: "" },
]

  return (
    <>
      <div className="text-3xl py-3 textShadow">
        <p className="text-hrtColorYellow">사용 설명서</p>
      </div>
      <div className="w-5/6 h-20 mx-auto my-auto bg-hrtColorPink rounded-lg flex justify-center items-center">
        <div className="flex flex-col items-center">
          <div className="w-52 h-6 bg-white text-center mt-1 mb-2">
            하팅!
          </div>
          <div className="">
            {tabArr.map((el, index) => (
                            <span className="text-white text-base mx-2" key={index}>{el.name}</span>))}
          </div>
        </div>
      </div>
      <div className="w-5/6 h-8/10 mx-auto items-center m-2 bg-white bg-opacity-80 rounded-lg">
        <div className="flex justify-center items-center">
          <img src={heart_select} alt="heart_select" className="w-3/5 mx-auto mt-6" />
        </div>
        <div className='m-4'>
          <p className='text-lg font-bold'>마음 전달하기</p>
          <p className='text-sm'>상대방에게 전달하고싶은 감정 하트를 선택해주세요.
            전달하고 싶은 메세지가 있다면 함께 보낼 수 있습니다.
            전달하는 마음은 24시간만 유지되며 수정,삭제 할 수 없다는 것을 주의하세요!
          </p>
        </div>
      </div>
    </>
  )
}

export default ManualSection