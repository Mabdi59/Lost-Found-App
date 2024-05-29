import axios from 'axios';

const API_URL = '/api/claims';

export default {
  getClaims() {
    return axios.get(API_URL);
  },
  addClaim(claim) {
    return axios.post(API_URL, claim);
  },
  deleteClaim(id) {
    return axios.delete(`${API_URL}/${id}`);
  }
};
