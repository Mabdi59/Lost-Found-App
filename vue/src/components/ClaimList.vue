<template>
    <div>
      <h2>Claims</h2>
      <ul>
        <li v-for="claim in claims" :key="claim.id">
          <p>Item ID: {{ claim.item_id }}</p>
          <p>Claimed by: {{ claim.claimed_by }}</p>
          <p>Date Claimed: {{ claim.date_claimed }}</p>
          <button @click="deleteClaim(claim.id)">Delete</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import { mapState, mapActions } from 'vuex';
  
  export default {
    computed: {
      ...mapState(['claims'])
    },
    methods: {
      ...mapActions(['fetchClaims', 'deleteClaim'])
    },
    created() {
      this.fetchClaims();
    }
  };
  </script>
  