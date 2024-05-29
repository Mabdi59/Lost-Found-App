<template>
    <div>
      <h1>Categories</h1>
      <CategoryForm />
      <CategoryList />
    </div>
  </template>
  
  <script>
  import CategoryForm from '../components/CategoryForm.vue';
  import CategoryList from '../components/CategoryList.vue';
  
  export default {
    components: {
      CategoryForm,
      CategoryList
    }
  };
  </script>
  