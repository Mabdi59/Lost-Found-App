import CategoryService from '../services/CategoryService';

const state = {
  categories: []
};

const getters = {
  allCategories: state => state.categories
};

const actions = {
  async fetchCategories({ commit }) {
    const response = await CategoryService.getCategories();
    commit('setCategories', response.data);
  },
  async addCategory({ commit }, category) {
    const response = await CategoryService.addCategory(category);
    commit('newCategory', response.data);
  },
  async deleteCategory({ commit }, id) {
    await CategoryService.deleteCategory(id);
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
