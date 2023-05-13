import React from 'react'

function HeartTestOptionsItem({...props}) {
  return (
    <div>
      <div>
        {props.optionInfo.map((answer:{type:string, content:string}) => {
          <div 
            id={answer.type}
            className='mx-auto h-12 w-40 flex justify-center items-center rounded-xl border-2 bg-hrtColorYellow border-hrtColorPink shadow-[0_4px_4px_rgba(251,139,176,1)]'
            onClick={props.onNextHandler(props.optionInfo)}
          >
            <p>{answer.content}</p>
          </div>;
        })}
      </div>
    </div>
  )
}

export default HeartTestOptionsItem
