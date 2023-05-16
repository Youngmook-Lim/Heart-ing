import { ReactComponent as HeartLock } from "../../assets/images/pixel/heart/heart_lock_1.svg";
import { ReactComponent as HeartYellow } from "../../assets/images/pixel/heart/heart_yellow_1.svg";
import { ReactComponent as HeartBlue } from "../../assets/images/pixel/heart/heart_blue_1.svg";
import { ReactComponent as HeartGreen } from "../../assets/images/pixel/heart/heart_green_1.svg";
import { ReactComponent as HeartPink } from "../../assets/images/pixel/heart/heart_pink_1.svg";
import { ReactComponent as HeartRed } from "../../assets/images/pixel/heart/heart_red_1.svg";
import { ReactComponent as HeartPlanet } from "../../assets/images/pixel/heart/heart_planet_1.svg";
import { ReactComponent as HeartRainbow } from "../../assets/images/pixel/heart/heart_rainbow_1.svg";

type propType = { id: number };

function HeartItemIcon(id: propType) {
  const index = id.id;
  const heartList = [
    <>
      <HeartLock />
    </>,
    <>
      <HeartYellow />
    </>,
    <>
      <HeartBlue />
    </>,
    <>
      <HeartGreen />
    </>,
    <>
      <HeartPink />
    </>,
    <>
      <HeartRed />
    </>,
    <>
      <HeartPlanet />
    </>,
    <>
      <HeartRainbow />
    </>,
  ];
  return heartList[index];
}

export default HeartItemIcon;
