import { axios } from './https'

export async function loginKakao(code: string) {
  try{
    const res = await axios.get(`api/v1/auth/guests/social/${code}`);
    const data = res.data
    return data
  } catch(err) {
    console.log('카카오 안됐단다')
    console.log(err)
    return null
  }
}

export async function modifyNickname(nickname: object) {
  try{
    const res = await axios.patch('api/v1/auth/users/nickname', nickname)
    const status = res.data.status
    return status
  } catch(err) {
    console.log('닉넴 못바꿧어용')
    return err
  }
}

export async function logout() {
  try{
    const res = await axios.patch('api/v1/auth/users/logout')
    const status = res.data.status
    return status
  } catch(err) {
    console.log('로그아웃 안됐단다')
    return null
  }
}
