<template>
    <div>
      <h2>Notifications</h2>
      <ul>
        <li v-for="notification in notifications" :key="notification.id">
          <p>{{ notification.message }}</p>
          <p>{{ notification.is_read ? 'Read' : 'Unread' }}</p>
          <button @click="markAsRead(notification.id)" v-if="!notification.is_read">Mark as Read</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import { mapState, mapActions } from 'vuex';
  
  export default {
    computed: {
      ...mapState(['notifications'])
    },
    methods: {
      ...mapActions(['fetchNotifications', 'markAsRead'])
    },
    created() {
      this.fetchNotifications();
    }
  };
  </script>
  