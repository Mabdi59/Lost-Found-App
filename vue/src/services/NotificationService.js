import axios from 'axios';

const API_URL = '/api/notifications';

export default {
  getNotifications() {
    return axios.get(API_URL);
  },
  markAsRead(id) {
    return axios.put(`${API_URL}/${id}`);
  }
};
