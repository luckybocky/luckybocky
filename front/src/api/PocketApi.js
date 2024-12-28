import ApiClient from "./ApiClient";

export const myPocketAddress = async () => {
  try {
    const response = await ApiClient.get(`pocket/address?userSeq=52`);

    return response.data.address;
  } catch (error) {
    console.error("pocket address error", error);
  }
};
