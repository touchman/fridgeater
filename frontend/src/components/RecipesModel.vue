<template>

    <div id="app">
        <div class="container">
            <table class="table-responsive bordered highlight centered hoverable z-depth-2" align="center">
                <thead>
                <tr>
                    <th v-for="column in recipes.columns">
                        {{column}}
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <div class="input-field">
                            <input placeholder="Type" v-model="recipes.input.type" id="type" type="text">
                            <label for="type">Type</label>
                        </div>
                    </td>
                    <td>
                        <div class="input-field">
                            <input placeholder="Name" v-model="recipes.input.name" id="name" type="text" ref="name">
                            <label for="name">Name</label>
                        </div>
                    </td>
                    <td>
                        <div class="input-field">
                            <input placeholder="Language" v-model="recipes.input.lang" id="lang" type="text">
                            <label for="lang">Language</label>
                        </div>
                    </td>
                    <td>
                        <div class="input-field">
                            <input placeholder="Product" v-model="recipes.input.product" id="product" type="text">
                            <a href="#!" @click="addProduct" class="btn btn-waves green darken-2"><i
                                    class="material-icons">add</i></a>
                            <label for="product">Product</label>
                            <p v-for="(product, i) in recipes.input.productList" :key="i">
                                {{product.name}},
                            </p>
                        </div>
                    </td>
                    <td><a href="#!" @click="add" class="btn btn-waves green darken-2"><i class="material-icons">add</i></a>
                    </td>
                </tr>
                <tr v-for="(recipe, index) in recipes.paginationData" :key="index">
                    <td>{{recipe.type}}</td>
                    <td>{{recipe.name}}</td>
                    <td>{{recipe.lang}}</td>
                    <td>
                        <p v-for="(product, i) in recipe.productList" :key="i">
                            {{product.name}},
                        </p>
                    </td>
                    <td style="width: 18%;">
                        <!--<a href="#modal" @click="edit(index)" class="btn waves-effect waves-light yellow darken-2"><i class="material-icons">edit</i>
                        </a>-->
                        <a href="#!" class="btn waves-effect waves-light red darken-2"
                           @click="remove(index, recipe.id)"><i class="material-icons">remove</i>
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>
            <p>
                <button @click="prevPage">Previous</button>
                <button @click="nextPage">Next</button>
            </p>
        </div>
    </div>
</template>

<script>
    import "materialize-css/dist/js/materialize.min";
    import axios from 'axios';

    export default {
        data() {
            return {
                recipes: {
                    data: [],
                    columns: ['Type', 'Name', 'Language', 'Products', 'Actions'],
                    input: {
                        type: "",
                        active: true,
                        name: "",
                        lang: "",
                        product: "",
                        productList: []
                    },
                    bin: [],
                    pageSize: 3,
                    currentPage: 1,
                    paginationData: []
                }
            }
        },
        created() {
            axios.get(`/backend/recipe/recipes`)
                .then(response => {
                    this.recipes.data = response.data;
                    this.recipes.paginationData = response.data.recipe.slice(0, 3);
                });
            console.log(this.recipes)
        },
        methods: {
            //function to add data to table
            add: function () {
                this.recipes.data.recipe.push({
                    type: this.recipes.input.type,
                    active: this.recipes.input.active,
                    name: this.recipes.input.name,
                    lang: this.recipes.input.lang,
                    productList: this.recipes.input.productList
                });

                const str = JSON.stringify({
                    type: this.recipes.input.type,
                    active: this.recipes.input.active,
                    name: this.recipes.input.name,
                    lang: this.recipes.input.lang,
                    productList: this.recipes.input.productList
                });

                axios.post('http://localhost/backend/recipe/', str, {headers: {'Content-Type': 'application/json'}})
                    .then((response) => {
                        console.log(response);
                    })
                    .catch((error) => {
                        console.log(error);
                        alert("Error occurred: " + error)
                    });

                for (var key in this.recipes.input) {
                    this.recipes.input[key] = '';
                }
                this.recipes.input.productList = []
            },
            addProduct: function () {
                console.log(this.recipes);
                this.recipes.input.productList.push(
                    {
                        name: this.recipes.input.product,
                        lang: this.recipes.input.lang,
                        active: true
                    })
            },
            remove: function (index, recipeId) {
                console.log(index);
                console.log(this.recipes.currentPage * this.recipes.pageSize + index);
                this.recipes.data.recipe.splice(this.recipes.currentPage * this.recipes.pageSize + index, 1);
                this.recipes.paginationData.splice(index, 1);

                axios.delete('http://localhost/backend/recipe/' + recipeId)
                    .then((response) => {
                        console.log(response);
                    })
                    .catch((error) => {
                        console.log(error);
                        alert("Error occurred: " + error)
                    });
            },
            nextPage: function () {
                if ((this.recipes.currentPage * this.recipes.pageSize) < this.recipes.data.recipe.length) this.recipes.currentPage++;
                const start = this.recipes.currentPage * this.recipes.pageSize,
                    end = start + this.recipes.pageSize;
                this.recipes.paginationData = this.recipes.data.recipe.slice(start, end);
            },
            prevPage: function () {
                if (this.recipes.currentPage > 1) this.recipes.currentPage--;
                const start = this.recipes.currentPage * this.recipes.pageSize,
                    end = start + this.recipes.pageSize;
                this.recipes.paginationData = this.recipes.data.recipe.slice(start, end);
            }
        }
    }

</script>
//*[@id="content"]/div[1]/div/div/div
<style scoped>

</style>