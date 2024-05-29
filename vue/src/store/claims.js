import axios from 'axios';

const state = {
  claims: []
};

const getters = {
  allClaims: state => state.claims
};

const actions = {
  async fetchClaims({ commit }) {
    const response = await axios.get('/api/claims');
    commit('setClaims', response.data);
  },
  async addClaim({ commit }, claim) {
    const response = await axios.post('/api/claims', claim);
    commit('newClaim', response.data);
  },
  async deleteClaim({ commit }, id) {
    await axios.delete(`/api/claims/${id}`);
    commit('removeClaim', id);
  }
};

const mutations = {
  setClaims: (state, claims) => (state.claims = claims),
  newClaim: (state, claim) => state.claims.push(claim),
  removeClaim: (state, id) => state.claims = state.claims.filter(claim => claim.id !== id)
};

export default {
  state,
  getters,
  actions,
  mutations
};
