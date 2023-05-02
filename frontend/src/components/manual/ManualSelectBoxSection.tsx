import ManualSelectBoxSectionImg from "./ManualSelectBoxSectionImg"

interface ManualSelectBoxSectionProps {
  name: string,
  content: string,
  manualImg: number
}

function ManualSelectBoxSection({name, content, manualImg}: ManualSelectBoxSectionProps) {

  return (
    <div className="flex flex-col items-center">
      <div className="flex justify-center items-center">
        <ManualSelectBoxSectionImg manualImg={manualImg} />
      </div>
      <div>
        <div className='m-4'>
          <p className='text-lg font-bold mb-4'>{name}</p>
          <p className='text-sm w-full mx-auto px-8'>{content}
          </p>
        </div>
      </div>
    </div>
  )
}

export default ManualSelectBoxSection