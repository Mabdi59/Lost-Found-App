import axios from 'axios';

const state = {
  categories: []
};

const getters = {
  allCategories: state => state.categories
};

const actions = {
  async fetchCategories({ commit }) {
    const response = await axios.get('/api/categories');
    commit('setCategories', response.data);
  },
  async addCategory({ commit }, category) {
    const response = await axios.post('/api/categories', category);
    commit('newCategory', response.data);
  },
  async deleteCategory({ commit }, id) {
    await axios.delete(`/api/categories/${id}`);
    commit('removeCategory', id);
  }
};

const mutations = {
  setCategories: (state, categories) => (state.categories = categories),
  newCategory: (state, category) => state.categories.push(category),
  removeCategory: (state, id) => state.categories = state.categories.filter(category => category.id !== id)
};

export default {
  state,
  getters,
  actions,
  mutations
};
