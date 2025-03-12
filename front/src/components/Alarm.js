import React, { useEffect } from "react";

const Alarm = ({ message, visible, onClose, backgroundColor }) => {
  useEffect(() => {
    if (visible) {
      setTimeout(() => onClose(), 2000);
    }
  }, [visible, onClose]);

  if (!visible) return null;

  return (
    <div className="fixed inset-0 flex justify-center z-30">
      <div
        className={`fixed bottom-16 rounded-lg shadow-md py-2 px-4 text-center text-white whitespace-pre-line ${backgroundColor}`}
      >
        {message}
      </div>
    </div>
  );
};

export default Alarm;
