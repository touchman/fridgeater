<template>
    <div>
        <h2>Register Page</h2>
        <form @submit.prevent="register">
            <div>
                <input type="text" placeholder="Name" v-model="name">
            </div>
            <div>
                <input placeholder="Login" type="text" v-model="email">
            </div>
            <div>
                <input placeholder="Password" type="password" v-model="password">
            </div>
            <div>
                <input placeholder="Confirm password" type="password" v-model="password2">
            </div>
            <div>
                <button type="submit">Register</button>
            </div>
        </form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                name: "",
                email: "",
                password: "",
                password2: ""
            }
        },
        methods: {
            register: function () {
                let info = {
                    name: this.name,
                    email: this.email,
                    password: this.password
                };

                this.$store.dispatch('register', info).then(() => this.$router.push('/login')).catch(err => console.log(err))

            }
        }
    }
</script>

<style scoped>

</style>