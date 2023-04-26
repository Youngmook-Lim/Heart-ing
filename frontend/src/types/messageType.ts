export interface IMessageInfoTypes {
  messageId: string;
  title: string;
  heartId: string;
  heartName: string;
  heartUrl: string;
  emojiId: string;
  emojiName: string;
  emojiUrl: string;
  isRead: boolean;
  createdDate: string;
  expiredDate: string;
}

export interface IMessageDetailTypes {
  messageId: string;
  title: string;
  heartId: string;
  heartName: string;
  heartUrl: string;
  emojiId: string;
  emojiName: string;
  emojiUrl: string;
  content: string;
  isRead: boolean;
  isStored: boolean;
  createdDate: string;
  expiredDate: string;
  isReported: boolean;
}

export interface IMessageModalTypes {
  mode: string;
  isExpired?: boolean;
}
