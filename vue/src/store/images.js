import axios from 'axios';

const state = {
  images: []
};

const getters = {
  allImages: state => state.images
};

const actions = {
  async fetchImages({ commit }, itemId) {
    const response = await axios.get(`/api/items/${itemId}/images`);
    commit('setImages', response.data);
  },
  async uploadImage({ commit }, { itemId, image }) {
    const formData = new FormData();
    formData.append('image', image);

    const response = await axios.post(`/api/items/${itemId}/images`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    commit('newImage', response.data);
  }
};

const mutations = {
  setImages: (state, images) => (state.images = images),
  newImage: (state, image) => state.images.push(image)
};

export default {
  state,
  getters,
  actions,
  mutations
};
