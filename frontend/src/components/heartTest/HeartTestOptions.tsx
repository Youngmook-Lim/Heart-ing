import React, { useEffect, useState } from 'react';
import Questions from '../../assets/questions.json'
import LogoEffect from '../../assets/images/logo/logo_effect.png'

function HeartTestOptions({...props}) {
  const [opsNum, setOpsNum] = useState(0)
  const [MBTI, setMBTI] = useState(['A',])

  const onNextHandler = (e:React.MouseEvent<HTMLDivElement>) => {
    const optionType = e.currentTarget.id
    const newMBTI = [...MBTI]
    newMBTI.push(optionType)
    setMBTI(newMBTI)
    if (opsNum > 10) {
      let e=0, n=0, f=0, j=0;
      for (let i=0; i<13; i++) {
        if (newMBTI[i] === 'E') {
          e++;
        } else if (newMBTI[i] === 'N') {
          n++;
        } else if (newMBTI[i] === 'F') {
          f++;
        } else if (newMBTI[i] === 'J') {
          j++;
        }
      }
      let result = [
        e >= 2 ? 'E' : 'I',
        n >= 2 ? 'N' : 'S',
        f >= 2 ? 'F' : 'T',
        j >= 2 ? 'J' : 'P',
      ]
      let resultStr = result.join('')
      props.setResult(resultStr)
      props.setTestMode('result')
    }
    setOpsNum(opsNum+1)
  }

  return (
    <div className="container mx-auto px-6 fullHeight">
      <img src={LogoEffect} alt="test" className="w-full px-14 py-8" />
      <div className=" mx-auto">
        <div className="bg-hrtColorWhiteTrans border-2 border-hrtColorPink rounded-lg relative ">
          <div className="overflow-auto guideHeight scrollbar-hide">
              <div className='mb-12 mt-8 mx-4'>
              <p className='text-right mr-8 mb-4 text-hrtColorOutline700'>{opsNum+1}/12</p>
                <p className='whitespace-pre-wrap text-xl'>{Questions[opsNum].question}</p>
              </div>
              <div>
                {Questions[opsNum].answers.map((answer) => {
                  return <div id={answer.type} className={`mx-auto h-auto w-64 my-4 py-2 flex justify-center items-center border-2 cursor-pointer ${answer.id === 1 ? 'bg-hrtColorYellow border-hrtColorPurple' : 'bg-hrtColorPurple border-hrtColorYellow'}`} onClick={onNextHandler}><p className='whitespace-pre-wrap'>{answer.content}</p></div>;
                })}
              </div>
              </div>
          <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute left-1 top-1"></div>
          <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute right-1 top-1"></div>
          <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute left-1 bottom-1"></div>
          <div className="w-2 h-2 rounded-xl border-2 border-hrtColorPink absolute right-1 bottom-1"></div>
        </div>
      </div>
            </div>
  )
}

export default HeartTestOptions
