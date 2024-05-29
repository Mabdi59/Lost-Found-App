<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link to="/">Home</router-link>&nbsp;|&nbsp;
      <router-link to="/categories">Categories</router-link>&nbsp;|&nbsp;
      <router-link to="/items">Items</router-link>&nbsp;|&nbsp;
      <router-link to="/claims">Claims</router-link>&nbsp;|&nbsp;
      <router-link to="/notifications">Notifications</router-link>&nbsp;|&nbsp;
      <router-link v-if="$store.state.token !== ''" to="/logout">Logout</router-link>
      <router-link v-else to="/login">Login</router-link>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
};
</script>

<style scoped>
#nav {
  padding: 1em;
}
#nav a {
  margin: 0 0.5em;
  text-decoration: none;
  color: #42b983;
}
</style>
