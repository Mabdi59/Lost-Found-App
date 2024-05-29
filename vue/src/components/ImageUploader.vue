<template>
    <div>
      <h2>Upload Image</h2>
      <input type="file" @change="onFileChange" />
      <button @click="uploadImage">Upload</button>
    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  
  export default {
    data() {
      return {
        image: null
      };
    },
    methods: {
      ...mapActions(['uploadImage']),
      onFileChange(event) {
        this.image = event.target.files[0];
      },
      uploadImage() {
        const itemId = this.$route.params.itemId;
        this.uploadImage({ itemId, image: this.image });
      }
    }
  };
  </script>
  