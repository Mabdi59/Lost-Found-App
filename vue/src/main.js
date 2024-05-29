import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createStore } from './store';
import axios from 'axios';

const currentToken = localStorage.getItem('token');
const currentUser = JSON.parse(localStorage.getItem('user') || '{}');

axios.defaults.baseURL = import.meta.env.VITE_REMOTE_API;

if (currentToken) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

const store = createStore(currentToken, currentUser);

createApp(App)
  .use(router)
  .use(store)
  .mount('#app');
