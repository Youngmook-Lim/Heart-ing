import React from "react";
import { useRecoilValue } from "recoil";
import { isOpenReportingAtom } from "../../atoms/messageAtoms"
import Reporting from "../reporting/Reporting";

function MessageModalTextbox({ ...props }) {

  const isOpenReporting= useRecoilValue(isOpenReportingAtom)

  return (
    <div className="bg-hrtColorLightPurple p-4 mt-2 text-start rounded-sm">
      <div className="flex justify-between">
        <div className="text-xl pb-2">{props.title}</div>
      </div>
      { isOpenReporting ? <Reporting onReportMessage={props.onReportMessage} /> : null }
      <div className="text-base	leading-5	whitespace-pre-wrap">{props.content}</div>
    </div>
  );
}

export default MessageModalTextbox;
