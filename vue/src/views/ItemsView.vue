<template>
    <div>
      <h1>Items</h1>
      <ItemForm />
      <ItemList />
    </div>
  </template>
  
  <script>
  import ItemForm from '../components/ItemForm.vue';
  import ItemList from '../components/ItemList.vue';
  
  export default {
    components: {
      ItemForm,
      ItemList
    }
  };
  </script>
  