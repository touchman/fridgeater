<template id="app">
    <div>
        <Header/>
        <RecipesModel/>
    </div>
</template>

<script>
    import RecipesModel from "./components/RecipesModel";
    import Header from "./components/Header";

    export default {
        name: 'app',
        components: {
            Header,
            RecipesModel
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