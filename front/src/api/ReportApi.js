import ApiClient from "./ApiClient";

export const saveReport = async (article, user, type, content) => {
  console.log("Payload:", { article, user, type, content }); // 확인용 로그

  try {
    const report = {
      articleSeq: article,
      userSeq: user,
      reportType: type,
      reportContent: content,
    };
    await ApiClient.post(`report`, report);
  } catch (error) {
    console.error("report error", error);
  }
};
