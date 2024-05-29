import axios from 'axios';

const API_URL = '/api/items';

export default {
  getImages(itemId) {
    return axios.get(`${API_URL}/${itemId}/images`);
  },
  uploadImage(itemId, image) {
    const formData = new FormData();
    formData.append('image', image);

    return axios.post(`${API_URL}/${itemId}/images`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  }
};
