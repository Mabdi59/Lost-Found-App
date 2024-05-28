## Lost and Found Application
# Overview
The Lost and Found application is designed to help users report and find lost items. It consists of a backend built with Java Spring Boot and a frontend built with Vue.js. This document provides an overview of the setup and usage for both parts of the application.

# Project Structure
css
Copy code
lost-and-found-app/
├─ backend/
│  ├─ src/
│  │  ├─ main/
│  │  └─ test/
│  ├─ database/
│  └─ pom.xml
├─ frontend/
│  ├─ src/
│  ├─ public/
│  ├─ .env
│  └─ package.json
├─ README.md
# Getting Started

# Prerequisites
Node.js
npm
Java 11+
PostgreSQL
## Backend Setup

# Database
Inside the <project-root>/database/ directory, you'll find an executable Bash script (.sh file) and several SQL scripts (.sql files). These can be used to build and rebuild a PostgreSQL database for the Lost and Found application.

From a terminal session, execute the following commands:

sh
Copy code
cd <project-root>/backend/database/
./create.sh
This Bash script drops the existing database, if necessary, creates a new database named lostandfound, and runs the various SQL scripts in the correct order. You don't need to modify the Bash script unless you want to change the database name.

Each SQL script has a specific purpose as described here:

# File Name	# Description
data.sql	This script populates the database with any static setup data or test/demo data. The project team should modify this script.
dropdb.sql	This script destroys the database so that it can be recreated. It drops the database and associated users. The project team shouldn't have to modify this script.
schema.sql	This script creates all of the database objects, such as tables and sequences. The project team should modify this script.
user.sql	This script creates the database application users and grants them the appropriate privileges. The project team shouldn't have to modify this script.

## Database Users
The database superuser—meaning postgres—must only be used for database administration. It must not be used by applications. As such, two database users are created for the Lost and Found application to use as described here:

Username	Description
lostfound_owner	This user is the schema owner. It has full access—meaning granted all privileges—to all database objects within the public schema and also has privileges to create new schema objects. This user can be used to connect to the database from PGAdmin for administrative purposes.
lostfound_appuser	The application uses this user to make connections to the database. This user is granted SELECT, INSERT, UPDATE, and DELETE privileges for all database tables and can SELECT from all sequences. The application datasource has been configured to connect using this user.

## Spring Boot
Note: Spring Boot has been configured to run on port 9000 for this project. You might be used to port 8080 from earlier in the cohort, but it's changed so as not to conflict with the Vue server that you'll be running concurrently.

# Datasource
A Datasource has been configured for you in /src/resources/application.properties. It connects to the database using the lostfound_appuser database user. You can change the name of this database if you want, but remember to change it here and in the create.sh script in the database folder:

properties
Copy code
# datasource connection properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lostandfound
spring.datasource.name=lostandfound
spring.datasource.username=lostfound_appuser
spring.datasource.password=lostfoundpassword

# JdbcTemplate

If you look in /src/main/java/com/lostandfound/dao, you'll see JdbcUserDao. This is an example of how to get an instance of JdbcTemplate in your DAOs. If you declare a field of type JdbcTemplate and add it as an argument to the constructor, Spring automatically injects an instance for you:

java
Copy code
@Service
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
# CORS
Any controller that'll be accessed from a client like the Vue Starter application needs the @CrossOrigin annotation. This tells the browser that you're allowing the client application to access this resource:

java
Copy code
@RestController
@CrossOrigin
public class AuthenticationController {
    // ...
}
# Security
Most of the functionality related to Security is located in the /src/main/java/com/lostandfound/security package. You shouldn't have to modify anything here, but feel free to go through the code if you want to see how things work.

Authentication Controller
There is a single controller in the com.lostandfound.controller package called AuthenticationController.java.

This controller contains the /login and /register routes and works with the Vue starter as is. If you need to modify the user registration form, start here.

The authentication controller uses the JdbcUserDao to read and write data from the users table.

# Testing
DAO Integration Tests
com.lostandfound.dao.BaseDaoTests has been provided for you to use as a base class for any DAO integration test. It initializes a Datasource for testing and manages rollback of database changes between tests.

com.lostandfound.dao.JdbcUserDaoTests has been provided for you as an example for writing your own DAO integration tests.

Remember that when testing, you're using a copy of the real database. The schema for the test database is defined in the same schema script for the real database, database/lostfound_schema.sql. The data for the test database is defined separately within /src/test/resources/test-data.sql.

Frontend Setup
This is the Vue starter project for the Lost and Found application. This document walks you through how to set up and run the project. It also explains the project's features, such as Vue Router, Vuex, and authentication.

Project Setup
The first thing you'll need to do is to download any dependencies by running this command:

sh
Copy code
npm install
Next, take a moment to review the .env file that's located in the root of the project. You can store environment variables that you want to use throughout your application in this file. When you open it, it'll look like this:

sh
Copy code
VITE_REMOTE_API=http://localhost:9000
Note: The Java Spring Boot application is configured to run on port 9000 instead of 8080.

Start your Vue application with the following command:

sh
Copy code
npm run dev
Authentication
When you first run the project and visit the base URL, you're taken to a login page. This is because the home route / is secured by default. If you look in src/router/index.js, you'll see the following code:

js
Copy code
router.beforeEach((to) => {
  // Vuex code...

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});
This is a feature of Vue Router called Navigation Guards. You may not have learned about this in class, so take some time to read through the documentation to learn what they are and how they work.

The above code runs before each route. It first checks to see if the route requires authentication that is defined per route using the meta object key requiresAuth.

In the following configuration, you must be authenticated to view the home route while anyone can visit the login, logout, and registration routes:

js
Copy code
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
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  }
];
Next, the navigation guard checks to see if the route requires authentication and if an authentication token exists.

If authentication is not required, or the authentication token does exist—meaning it isn't an empty string—the user is routed to the requested route.

However, if authentication is required and the authentication token doesn't exist—meaning it's an empty string—the user is redirected to the /login route:

js
Copy code
// If it does and they are not logged in, send the user to "/login"
if (requiresAuth && store.state.token === '') {
  return {name: "login"};
}
// Otherwise, do nothing and they'll go to their next destination
Note: the application stores the current user (if any) and their authentication token in a centralized store using Vuex.

Vuex
The state for this application is stored in src/store/index.js using Vuex. The state object has two values: token and user. When you log in, the back-end service returns an authentication token along with your user credentials.

The authentication token is sent in the Authorization header to verify your identity. To persist this token when the application is closed or the page is refreshed, you'll store the token in local storage.

The default token either comes from local storage or it's set to an empty string. As you learned in the previous section, if the route requires authentication and this token is empty, it redirects the user to the login page:

js
Copy code
// src/main.js
const currentToken = localStorage.getItem('token');
if (currentToken) {
  // Set token axios requests
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

// src/store/index.js
export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {}
    },
    // ...
  });
}
# Login
When you reach the /login route, you'll see a bare login page. This is intentional. It's up to you to style this page to fit within your application.

When you fill in a username and password and click the "Sign In" button, the method login() is called. The login() method uses the src/services/AuthService.js to send a POST request to your API's /login route.

If you look at AuthService, you'll notice that there's no base URL set for Axios:

js
Copy code
import axios from 'axios';

export default {
  login(user) {
    return axios.post('/login', user)
  }
}
This is because this value is set in src/main.js and the value comes from the .env property file you saw earlier:

js
Copy code
axios.defaults.baseURL = import.meta.env.VITE_REMOTE_API;
If you get a successful response (200), it contains the authentication token and user object. You'll set these in Vuex by committing mutations:

js
Copy code
// src/views/LoginView.vue
login() {
  authService
    .login(this.user)
    .then(response => {
      if (response.status == 200) {
        this.$store.commit("SET_AUTH_TOKEN", response.data.token);
        this.$store.commit("SET_USER", response.data.user);
        this.$router.push("/");
      }
    })
  // ...
}
When you call the SET_AUTH_TOKEN mutation, several things happen.

First, you set the state.token value to what was returned from the API's /login method. Next, you store that same value in local storage so that it persists across refreshes. Finally, you set the Authorization header in Axios so that every subsequent request contains the token. This way, you don't have to manually do this on every request:

js
Copy code
mutations: {
  SET_AUTH_TOKEN(state, token) {
    state.token = token;
    localStorage.setItem('token', token);
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  }
}
Once the login() method finishes updating the store by committing the mutations, it forwards the user back to the homepage. They'll be able to see the homepage because they're authenticated.

Logout
There's a logout link in App.vue that forwards the user to the /logout route. When the user reaches this route, you'll commit this mutation in the store called LOGOUT:

js
Copy code
// src/views/LogoutView.vue
export default {
  created() {
    this.$store.commit("LOGOUT");
    this.$router.push("/login");
  }
};
When the mutation is called, the token is removed from local storage, the token and user state are cleared, and the user is redirected back to the homepage. The homepage then forwards the user to the login page because they're no longer logged in:

js
Copy code
mutations: {
  LOGOUT(state) {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    state.token = '';
    state.user = {};
    axios.defaults.headers.common = {};
  }
}
# Register
When you reach the /register route, you'll see a bare registration page. Like the login page, this is intentional. You'll need to style this page to fit within your application.

When you fill in a username, password, confirm the password role, and click the "Create Account" button, the method register() is called. This calls the register() method in src/services/AuthService.js. This passes your user details to your back-end application's REST API to create a new user:

js
Copy code
// src/views/RegisterView.vue
methods: {
  register() {
    // ...
    authService
      .register(this.user)
      .then((response) => {
        if (response.status == 201) {
          this.$router.push({
            path: '/login',
            query: { registration: 'success' },
          });
        }
      })
    // ...
  }
}
# License
This project is licensed under the MIT License - see the LICENSE.md file for details.

# Contact
For any inquiries or issues, please contact Mohamed Abdi at mohamedcoder0@gmail.com or create an issue on the GitHub repository.