import ImageService from '../services/ImageService';

const state = {
  images: []
};

const getters = {
  allImages: state => state.images
};

const actions = {
  async fetchImages({ commit }, itemId) {
    const response = await ImageService.getImages(itemId);
    commit('setImages', response.data);
  },
  async uploadImage({ commit }, { itemId, image }) {
    const response = await ImageService.uploadImage(itemId, image);
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
