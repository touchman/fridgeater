import Vue from 'vue'
import App from './App.vue'
import './assets/fonts.google.css'
import router from './router'
import store from './store'
import Axios from 'axios'


Vue.config.productionTip = false;
Vue.prototype.$http = Axios;
const token = localStorage.getItem('token')

if (token) {
    Vue.prototype.$http.defaults.headers.common['token'] = token
}

Vue.prototype.$http.defaults.headers.common['Content-Type'] = 'application/json'
Vue.prototype.$http.defaults.headers.post['Content-Type'] = 'application/json';
Vue.prototype.$http.defaults.headers.put['Content-Type'] = 'application/json';

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');
