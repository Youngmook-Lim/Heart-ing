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