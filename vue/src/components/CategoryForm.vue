<template>
    <div>
      <h2>Add Category</h2>
      <form @submit.prevent="onSubmit">
        <div>
          <label for="name">Category Name:</label>
          <input type="text" v-model="categoryName" required />
        </div>
        <button type="submit">Add Category</button>
      </form>
    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  
  export default {
    data() {
      return {
        categoryName: ''
      };
    },
    methods: {
      ...mapActions(['addCategory']),
      onSubmit() {
        const category = {
          category_name: this.categoryName
        };
        this.addCategory(category);
        this.categoryName = '';
      }
    }
  };
  </script>
  