<template>
    <div>
      <h2>Add Item</h2>
      <form @submit.prevent="onSubmit">
        <div>
          <label for="name">Item Name:</label>
          <input type="text" v-model="itemName" required />
        </div>
        <div>
          <label for="description">Description:</label>
          <textarea v-model="itemDescription" required></textarea>
        </div>
        <div>
          <label for="category">Category:</label>
          <select v-model="itemCategory" required>
            <option v-for="category in categories" :key="category.id" :value="category.id">
              {{ category.category_name }}
            </option>
          </select>
        </div>
        <button type="submit">Add Item</button>
      </form>
    </div>
  </template>
  
  <script>
  import { mapState, mapActions } from 'vuex';
  
  export default {
    data() {
      return {
        itemName: '',
        itemDescription: '',
        itemCategory: null
      };
    },
    computed: {
      ...mapState(['categories'])
    },
    methods: {
      ...mapActions(['addItem', 'fetchCategories']),
      onSubmit() {
        const item = {
          name: this.itemName,
          description: this.itemDescription,
          category_id: this.itemCategory,
          reported_by: this.$store.state.user.id,
          date_lost: new Date().toISOString().split('T')[0]
        };
        this.addItem(item);
        this.itemName = '';
        this.itemDescription = '';
        this.itemCategory = null;
      }
    },
    created() {
      this.fetchCategories();
    }
  };
  </script>
  