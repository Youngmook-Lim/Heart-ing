import { useRecoilState } from "recoil";
import ResponseEmojiIcon from "../common/ResponseEmojiIcon"
import { isSelectedEmojiIdAtom } from "../../atoms/messageAtoms"

interface propsType {
  id: number,
}

function HeartResponseEmojiListItem({ id }: propsType) {

  const [ isSelectedEmojiId, setIsSelectedEmojiId ] = useRecoilState(isSelectedEmojiIdAtom)

  const getEmojiId = () => {
    setIsSelectedEmojiId(id)
  }

  return (
    <>
      <div className="relative flex justify-center" onClick={getEmojiId}>
        <div className="absolute z-50">
          <ResponseEmojiIcon id={id} />
        </div>
        { isSelectedEmojiId === id ? <div className="w-11 h-11 bg-hrtColorLightPink rounded-full transform"></div> : <div className="w-11 h-11"></div> }
      </div>
    </>
  )
}

export default HeartResponseEmojiListItem