import axios from 'axios';

const API_URL = '/api/categories';

export default {
  getCategories() {
    return axios.get(API_URL);
  },
  addCategory(category) {
    return axios.post(API_URL, category);
  },
  deleteCategory(id) {
    return axios.delete(`${API_URL}/${id}`);
  }
};
