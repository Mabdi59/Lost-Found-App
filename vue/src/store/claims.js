import ClaimService from '../services/ClaimService';

const state = {
  claims: []
};

const getters = {
  allClaims: state => state.claims
};

const actions = {
  async fetchClaims({ commit }) {
    const response = await ClaimService.getClaims();
    commit('setClaims', response.data);
  },
  async addClaim({ commit }, claim) {
    const response = await ClaimService.addClaim(claim);
    commit('newClaim', response.data);
  },
  async deleteClaim({ commit }, id) {
    await ClaimService.deleteClaim(id);
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
