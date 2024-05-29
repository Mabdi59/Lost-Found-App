<template>
    <div>
      <h2>Categories</h2>
      <ul>
        <li v-for="category in categories" :key="category.id">
          {{ category.category_name }}
          <button @click="deleteCategory(category.id)">Delete</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import { mapState, mapActions } from 'vuex';
  
  export default {
    computed: {
      ...mapState(['categories'])
    },
    methods: {
      ...mapActions(['fetchCategories', 'deleteCategory'])
    },
    created() {
      this.fetchCategories();
    }
  };
  </script>
  