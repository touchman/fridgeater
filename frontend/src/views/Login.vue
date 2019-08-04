<template>
    <div>
        <h2>Login Page</h2>
        <form class="login" @submit.prevent="login">
            <div>
                <input placeholder="Login" type="text" v-model="email">
            </div>
            <div>
                <input placeholder="Password" type="password" v-model="password">
            </div>
            <div>
                <button type="submit">Login</button>
            </div>
        </form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                email: "",
                password: ""
            }
        },
        methods: {
            login: function () {
                const email = this.email;
                const password = this.password;
                this.$store.dispatch('login', {
                    email,
                    password
                }).then(() => this.$router.push('/recipes')).catch(err => console.log(err))
            }
        }
    }
</script>

<style scoped>

</style>