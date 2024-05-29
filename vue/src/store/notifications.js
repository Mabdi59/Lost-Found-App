import axios from 'axios';

const state = {
  notifications: []
};

const getters = {
  allNotifications: state => state.notifications
};

const actions = {
  async fetchNotifications({ commit }) {
    const response = await axios.get('/api/notifications');
    commit('setNotifications', response.data);
  },
  async markAsRead({ commit }, id) {
    await axios.put(`/api/notifications/${id}`);
    commit('markNotificationAsRead', id);
  }
};

const mutations = {
  setNotifications: (state, notifications) => (state.notifications = notifications),
  markNotificationAsRead: (state, id) => {
    const notification = state.notifications.find(notification => notification.id === id);
    if (notification) notification.is_read = true;
  }
};

export default {
  state,
  getters,
  actions,
  mutations
};
