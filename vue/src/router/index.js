import { createRouter, createWebHistory } from 'vue-router';
import { useStore } from 'vuex';

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import CategoriesView from '../views/CategoriesView.vue';
import ItemsView from '../views/ItemsView.vue';
import ClaimsView from '../views/ClaimsView.vue';
import NotificationsView from '../views/NotificationsView.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/logout',
    name: 'logout',
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/categories',
    name: 'categories',
    component: CategoriesView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/items',
    name: 'items',
    component: ItemsView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/claims',
    name: 'claims',
    component: ClaimsView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/notifications',
    name: 'notifications',
    component: NotificationsView,
    meta: {
      requiresAuth: true
    }
  }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {
  const store = useStore();
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  if (requiresAuth && store.state.token === '') {
    return { name: 'login' };
  }
});

export default router;
