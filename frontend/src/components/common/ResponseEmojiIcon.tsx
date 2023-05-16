import { ReactComponent as EmojiLike } from "../../assets/images/pixel/emoji/emoji_like_1.svg";
import { ReactComponent as EmojiKiki } from "../../assets/images/pixel/emoji/emoji_zz_1.svg";
import { ReactComponent as EmojiBest } from "../../assets/images/pixel/emoji/emoji_best_1.svg";
import { ReactComponent as EmojiSad } from "../../assets/images/pixel/emoji/emoji_sad_1.svg";
import { ReactComponent as EmojiCheck } from "../../assets/images/pixel/emoji/emoji_check_1.svg";

type propType = { id: number };

function ResponseEmojiIcon(id: propType) {
  const index = id.id;
  const emojiList = [
    null,
    <>
      <EmojiLike />
    </>,
    <>
      <EmojiKiki />
    </>,
    <>
      <EmojiBest />
    </>,
    <>
      <EmojiSad />
    </>,
    <>
      <EmojiCheck />
    </>,
  ];
  return emojiList[index];
}

export default ResponseEmojiIcon;
