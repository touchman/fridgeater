<template>
    <div id="app">
        <div id="nav">
            <router-link to="/">Home |</router-link>
            <router-link to="/about">About |</router-link>
            <router-link to="/register" v-if="!isLoggedIn">Register |</router-link>
            <router-link to="/login" v-if="!isLoggedIn">Login |</router-link>
            <router-link to="/recipes" v-if="isLoggedIn">Recipes |</router-link>
            <span style="cursor: pointer" v-if="isLoggedIn"><a @click="logout">Logout</a></span>
        </div>
        <router-view/>
    </div>
</template>

<script>
    import RecipesModel from "./components/RecipesModel";
    import Header from "./components/Header";

    export default {
        name: 'app',
        computed: {
            isLoggedIn: function () {
                return this.$store.getters.isLoggedIn
            }
        },
        components: {
            Header,
            RecipesModel
        },
        methods: {
            logout: function () {
                this.$store.dispatch('logout')
                    .then(() => {
                        this.$router.push('/login')
                    })
            }
        },
        created: function () {
            this.$http.interceptors.response.use(undefined, function (err) {
                return new Promise(function (resolve, reject) {
                    if (err.status === 401 && err.config && !err.config.__isRetryRequest) {
                        this.$store.dispatch('logout')
                    }
                    throw err;
                });
            });
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>