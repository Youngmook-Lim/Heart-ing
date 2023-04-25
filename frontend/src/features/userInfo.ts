import React from 'react'
import { IUserSavingInfoTypes } from './../types/userType'

export const savingAccessToken = (userInfo: IUserSavingInfoTypes) => {
  window.localStorage.setItem('userType', userInfo.userId)
  window.localStorage.setItem('accessToken', userInfo.accessToken)
};