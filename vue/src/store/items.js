import ItemService from '../services/ItemService';

const state = {
  items: []
};

const getters = {
  allItems: state => state.items
};

const actions = {
  async fetchItems({ commit }) {
    const response = await ItemService.getItems();
    commit('setItems', response.data);
  },
  async addItem({ commit }, item) {
    const response = await ItemService.addItem(item);
    commit('newItem', response.data);
  },
  async deleteItem({ commit }, id) {
    await ItemService.deleteItem(id);
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
