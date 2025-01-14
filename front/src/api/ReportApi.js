import ApiClient from "./ApiClient";

export const saveReport = async (article, type, content) => {
  console.log("Payload:", { article, type, content }); // 확인용 로그

  try {
    const report = {
      articleSeq: article,
      reportType: type,
      reportContent: content,
    };
    await ApiClient.post(`report`, report);
  } catch (error) {
    console.error("report error", error);
  }
};