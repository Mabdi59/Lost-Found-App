import axios from 'axios';

const API_URL = '/api/items';

export default {
  getItems() {
    return axios.get(API_URL);
  },
  addItem(item) {
    return axios.post(API_URL, item);
  },
  deleteItem(id) {
    return axios.delete(`${API_URL}/${id}`);
  }
};