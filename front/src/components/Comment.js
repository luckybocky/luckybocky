import React, { useState } from "react";
import {saveComment} from "../api/CommentApi"

const Comment = ( articleInfo ) => {
    const [message, setMessage] = useState(null);
    const handleSubmit = () => {
        if (!message){
          alert("답장을 입력해주세요 :)");
          return;
        }

        alert(`${articleInfo.articleSeq}번 복에 답장하기 : ${message}`)
        saveComment(articleInfo.articleSeq, message);        
    }

    return (
        <div>
            <textarea 
            className="text-xl w-full h-24 p-2 border rounded-md mb-4 resize-none"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="나도 복 보내기"/>
        
            <button
              className="bg-green-500 text-white py-2 px-4 rounded-md"
              onClick={handleSubmit}
            >답장하기</button>
        </div>
    );
};

export default Comment;