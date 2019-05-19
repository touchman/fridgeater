<template id="app">
    <div>
        <Header/>
        <PersonList/>
    </div>
</template>

<script>
    import PersonList from "./components/PersonList";
    import Header from "./components/Header";

    export default {
        name: 'app',
        components: {
            Header,
            PersonList
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