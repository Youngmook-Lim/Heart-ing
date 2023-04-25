import React from 'react'

function nicknameValidation(value:string) {
  const nicknameREGEX = /^[a-zA-Zㄱ-힣0-9-_.]{2,8}$/
  return nicknameREGEX.test(value)
}

export default nicknameValidation
