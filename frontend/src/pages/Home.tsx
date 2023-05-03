import ManualHome from "../components/Home/ManualHome";
import { getTotalHeartApi } from "../features/api/messageApi";

function Home() {
  console.log("매뉴얼 페이지 렌더링");

  async function onGetTotalHeart() {
    const totalCnt = await getTotalHeartApi();
    return totalCnt;
  }

  return (
    <div className="h-[calc((var(--vh, 1vh) * 100)-8rem)]">
      <ManualHome onGetTotalHeart={onGetTotalHeart} />
    </div>
  );
}

export default Home;
