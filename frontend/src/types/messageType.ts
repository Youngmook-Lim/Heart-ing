export interface IMessageInfoTypes {
  messageId: number;
  title: string;
  heartId: number;
  heartName: string;
  heartUrl: string;
  emojiId: number;
  emojiName: string;
  emojiUrl: string;
  createdDate: string;
  expiredDate: string;
  isRead: boolean;
}

export interface IMessageDetailTypes {
  messageId: number;
  title: string;
  messageTitle?: string;
  heartId: number;
  heartName: string;
  heartUrl: string;
  emojiId: number;
  emojiName: string;
  emojiUrl: string;
  createdDate: string;
  expiredDate: string;
  isRead: boolean;
  isReported: boolean;
  isStored: boolean;
  content: string;
  shortDescription: string;
}

export interface IMessageSendTypes {
  heartId: number;
  senderId: string|null;
  receiverId: string;
  title: string;
  content: string;
}

export interface IMessageModalTypes {
  mode: string;
  isExpired?: boolean;
  isStored?: boolean;
}

export interface IHeartInfoTypes {
  heartId: number,
  name: string,
  heartUrl: string,
  type: string,
  isLocked: string,
}

export interface ITotalHeartPropsTypes {
  onGetTotalHeart: () => Promise<number>;
}

export interface IGetNotificationListTypes {
  notificationId: number;
  userId: number;
  content: string;
  createdDate: string;
  expiredDate: string;
  type: string;
  isChecked: boolean;
  isActive: boolean;
}

export interface IResponseHeartTypes {
  messageId: number,
  emojiId: number,
}