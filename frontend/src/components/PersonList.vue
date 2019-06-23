<template>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Type</th>
                <th scope="col">Active</th>
                <th scope="col">Name</th>
                <th scope="col">Language</th>
                <th scope="col">Products</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(recipe, index) in recipes.recipe" :key="index">
                <td>{{recipe.id}}</td>
                <td>{{recipe.type}}</td>
                <td>{{recipe.active}}</td>
                <td>{{recipe.name}}</td>
                <td>{{recipe.lang}}</td>
                <td>
                    <p v-for="(product, i) in recipe.productList" :key="i">
                        Id: <strong>{{product.id}}<br></strong>
                        Active: <strong>{{product.active}}<br></strong>
                        Name: <strong>{{product.name}}<br></strong>
                        Language: <strong>{{product.lang}}<br></strong>
                    </p>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                recipes: []
            }
        },

        created() {
            axios.get(`/backend/recipe/recipes`)
                .then(response => {
                    this.recipes = response.data;
                });
        }
    }
</script>
//*[@id="content"]/div[1]/div/div/div
<style scoped>

</style>