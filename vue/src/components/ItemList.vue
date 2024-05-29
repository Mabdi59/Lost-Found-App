<template>
    <div>
      <h2>Items</h2>
      <ul>
        <li v-for="item in items" :key="item.id">
          <h3>{{ item.name }}</h3>
          <p>{{ item.description }}</p>
          <p>{{ item.date_lost }}</p>
          <p>{{ item.is_claimed ? 'Claimed' : 'Not Claimed' }}</p>
          <button @click="deleteItem(item.id)">Delete</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import { mapState, mapActions } from 'vuex';
  
  export default {
    computed: {
      ...mapState(['items'])
    },
    methods: {
      ...mapActions(['fetchItems', 'deleteItem'])
    },
    created() {
      this.fetchItems();
    }
  };
  </script>
  