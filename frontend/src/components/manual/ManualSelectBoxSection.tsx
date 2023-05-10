import ManualSelectBoxSectionImg from "./ManualSelectBoxSectionImg";

interface ManualSelectBoxSectionProps {
  name: string;
  content: string;
  manualImg: number;
}

function ManualSelectBoxSection({
  name,
  content,
  manualImg,
}: ManualSelectBoxSectionProps) {
  return (
    <div className="flex flex-col h-full overflow-auto p-4">
      <div className="flex items-center h-1/2 m-3">
        <ManualSelectBoxSectionImg manualImg={manualImg} />
      </div>
      <div className="h-fit">
        <div className="m-4">
          <p className="text-lg font-bold mb-4">{name}</p>
          <p className="text-sm w-full mx-auto px-4">{content}</p>
        </div>
      </div>
    </div>
  );
}

export default ManualSelectBoxSection;
