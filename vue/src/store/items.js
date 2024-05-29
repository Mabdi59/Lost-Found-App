import axios from 'axios';

const state = {
  items: []
};

const getters = {
  allItems: state => state.items
};

const actions = {
  async fetchItems({ commit }) {
    const response = await axios.get('/api/items');
    commit('setItems', response.data);
  },
  async addItem({ commit }, item) {
    const response = await axios.post('/api/items', item);
    commit('newItem', response.data);
  },
  async deleteItem({ commit }, id) {
    await axios.delete(`/api/items/${id}`);
    commit('removeItem', id);
  }
};

const mutations = {
  setItems: (state, items) => (state.items = items),
  newItem: (state, item) => state.items.push(item),
  removeItem: (state, id) => state.items = state.items.filter(item => item.id !== id)
};

export default {
  state,
  getters,
  actions,
  mutations
};
