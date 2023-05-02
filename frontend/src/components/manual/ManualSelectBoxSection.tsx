import heart_select from '../../assets/images/png/heart_select.png'

function ManualSelectBoxSection() {

  return (
    <div className="flex flex-col items-center">
      <div className="flex justify-center items-center">
        <img src={heart_select} alt="heart_select" className="w-3/5 mx-auto mt-6" />
      </div>
      <div>
        <div className='m-4'>
          <p className='text-lg font-bold'>하트전달</p>
          <p className='text-sm whitespace-pre-wrap'>상대에게 전달하고 싶은 하트를 선택해보세요.
            전달하고 싶은 메세지가 있다면 함께 보낼 수도 있어요.
            전달하는 하트는 24시간만 유지된다는 것을 주의하세요!
          </p>
        </div>
      </div>
    </div>
  )
}

export default ManualSelectBoxSection