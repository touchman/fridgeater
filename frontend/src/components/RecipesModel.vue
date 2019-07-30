<template>

    <div id="app">
        <div class="container">
            <p>
                <button @click="prevPage">Previous</button>
                <button @click="nextPage">Next</button>
            </p>
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
                        <select v-model="recipes.input.type" id="type">
                            <option value="">OTHER</option>
                            <option>PASTA</option>
                            <option>SALAD</option>
                            <option>BREAD</option>
                            <option>CURRY</option>
                            <option>VEGETABLE</option>
                            <option>SOUP</option>
                            <option>ROAST</option>
                            <option>STEW</option>
                            <option>PIZZA</option>
                            <option>SAUCE</option>
                            <option>DESSERT</option>
                            <option>DRINK</option>
                        </select>
                        <label for="type">Type</label>
                    </td>
                    <td>
                        <div class="input-field">
                            <input placeholder="Name" v-model="recipes.input.name" id="name" type="text" ref="name">
                            <label for="name">Name</label>
                        </div>
                    </td>
                    <td>
                        <select v-model="recipes.input.lang" id="lang">
                            <option disabled value="">ru</option>
                            <option>en</option>
                        </select>
                        <label for="lang">Language</label>
                    </td>
                    <td>
                        <div class="input-field">
                            <input placeholder="Product" v-model="recipes.input.product" id="product" type="text"
                                   v-on:keyup.enter="addProduct">
                            <a href="#!" @click="addProduct" class="btn btn-waves green darken-2"><i
                                    class="material-icons">add</i></a>
                            <label for="product" @click="addProduct" style="cursor: pointer;">Product</label>
                            <p v-for="(product, i) in recipes.input.productList" :key="i">
                                {{product.name}}<a href="#!" @click="removeProduct(i)"
                                                   class="btn btn-waves green darken-2"><i
                                    class="material-icons">remove</i></a>
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
                            {{product.name}}
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

                axios.post('/backend/recipe/', str, {headers: {'Content-Type': 'application/json'}})
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
                if (this.recipes.input.product !== '') {
                    this.recipes.input.productList.push(
                        {
                            name: this.recipes.input.product,
                            lang: this.recipes.input.lang,
                            active: true
                        });
                    this.recipes.input.product = ''
                }
            },
            removeProduct: function (index) {
                this.recipes.input.productList.splice(index, 1);
            },
            remove: function (index, recipeId) {
                if (confirm("Do you really want to delete this item?")) {
                    console.log(index);
                    console.log(this.recipes.currentPage * this.recipes.pageSize + index);
                    this.recipes.data.recipe.splice(this.recipes.currentPage * this.recipes.pageSize + index, 1);
                    this.recipes.paginationData.splice(index, 1);

                    axios.delete('/backend/recipe/' + recipeId)
                        .then((response) => {
                            console.log(response);
                        })
                        .catch((error) => {
                            console.log(error);
                            alert("Error occurred: " + error)
                        });
                }
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