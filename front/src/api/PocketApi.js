import ApiClient from "./ApiClient";

export const myPocketAddress = async () => {
  const response = await ApiClient.get(`pocket/address`);

  return response.data.data;
};

export const createPocket = async () => {
  try {
    const response = await ApiClient.post(`pocket`);

    return response.data.data;
  } catch (error) {
    console.error("pocket address error", error);
  }
};
