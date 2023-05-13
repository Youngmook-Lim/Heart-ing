export interface IQuestionTypes {
  id: number,
  option: string,
  question: string,
  answer: [{type:string, content: string}]
}