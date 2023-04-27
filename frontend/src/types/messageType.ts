export interface IMessageInfoTypes {
  messageId: string;
  title: string;
  heartId: number;
  heartName: string;
  heartUrl: string;
  emojiId: string;
  emojiName: string;
  emojiUrl: string;
  createdDate: string;
  expiredDate: string;
  isRead: boolean;
}

export interface IMessageDetailTypes {
  messageId: string;
  title: string;
  heartId: number;
  heartName: string;
  heartUrl: string;
  emojiId: string;
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
  senderId: string;
  receiverId: string;
  title: string;
  content: string;
}

export interface IMessageModalTypes {
  mode: string;
  isExpired?: boolean;
}
