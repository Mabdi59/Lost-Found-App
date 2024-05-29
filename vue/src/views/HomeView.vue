<template>
  <div class="home">
    <h1>Home</h1>
    <p v-if="$store.state.token === ''">You must be authenticated to see this</p>
    <p v-else>Welcome, {{ $store.state.user.username }}!</p>
  </div>
</template>

<script>
export default {
};
</script>
