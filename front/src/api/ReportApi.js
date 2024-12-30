import ApiClient from "./ApiClient";

export const saveReport = async (article, type, content) => {
  try {
    const report = {articleSeq: article, reportType: type, reportContent: content}

    const response = await ApiClient.post(`report`, report);

    return response.data.message;
  } catch (error) {
    console.error("report error", error);
  }
};
