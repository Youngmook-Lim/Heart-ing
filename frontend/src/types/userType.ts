export interface IUserSavingInfoTypes {
  userId: string;
  accessToken : string;
}

export interface IUserProfileTypes {
  nickname : string,
  statusMessage : string,
  messageTotal : number,
}

export interface IUpdateProfileTypes {
  nickname : string,
  statusMessage : string,
}

export interface ITwitterUserTokenTypes {
  oauthToken : string,
  oauthVerifier : string,
}