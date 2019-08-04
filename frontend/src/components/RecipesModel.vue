<template>

    <div id="app">
        <div class="container">
            <modal v-show="isModalVisible" @close="closeModal"
                   v-bind:index="recipes.editableInput.index"
                   v-bind:id="recipes.editableInput.id"
                   v-bind:type="recipes.editableInput.type"
                   v-bind:name="recipes.editableInput.name"
                   v-bind:lang="recipes.editableInput.lang"
                   v-bind:productList="recipes.editableInput.productList"
                   @toggle="toggleModals"/>
            <table class="table-responsive bordered highlight centered hoverable z-depth-2" align="center">
                <thead>
                <tr>
                    <th @click="sort('type')" style="cursor: pointer;">
                        Type
                    </th>
                    <th @click="sort('name')" style="cursor: pointer;">
                        Name
                    </th>
                    <th @click="sort('lang')" style="cursor: pointer;">
                        Language
                    </th>
                    <th>
                        Products
                    </th>
                    <th v-if="isLoggedIn">
                        Actions
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr v-if="isLoggedIn">
                    <td>
                        <select v-model="recipes.input.type" id="type">
                            <option>OTHER</option>
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
                            <option>ru</option>
                            <option>en</option>
                        </select>
                        <label for="lang">Language</label>
                    </td>
                    <td>
                        <div class="input-field col s6">
                            <input placeholder="Product" v-model="recipes.input.product" id="product" type="text"
                                   v-on:keyup.enter="addProduct">
                            <label for="product" @click="addProduct" style="cursor: pointer;">Product</label>
                            <a href="#!" @click="addProduct">
                                <i class="material-icons">add_box</i>
                            </a>
                            <p v-for="(product, i) in recipes.input.productList" :key="i">
                                {{product.name}}
                                <a href="#!" @click="removeProduct(i)" class="btn green darken-2">
                                    <i class="material-icons">remove_circle_outline</i>
                                </a>
                            </p>
                        </div>
                    </td>
                    <td>
                        <a href="#!" @click="add" class="btn green darken-2">
                            <i class="material-icons">save</i>
                        </a>
                    </td>
                </tr>
                <tr v-for="(recipe, index) in sorted" :key="index">
                    <td>{{recipe.type}}</td>
                    <td>{{recipe.name}}</td>
                    <td>{{recipe.lang}}</td>
                    <td>
                        <p v-for="(product, i) in recipe.productList" :key="i">
                            {{product.name}}
                        </p>
                    </td>
                    <td style="width: 18%;" v-if="isLoggedIn">
                        <a href="#!" class="btn waves-light red darken-2"
                           @click="showModal(index)"><i class="material-icons">edit</i>
                        </a>
                        <a href="#!" class="btn waves-light red darken-2"
                           @click="remove(index, recipe.id)"><i class="material-icons">remove_circle_outline</i>
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
    import modal from '../components/Modal';


    export default {
        components: {
            modal,
        },
        data() {
            return {
                isModalVisible: false,
                recipes: {
                    data: [],
                    input: {
                        type: "OTHER",
                        active: true,
                        name: "",
                        lang: "ru",
                        product: "",
                        productList: []
                    },
                    editableInput: {
                        index: 0,
                        id: 0,
                        type: "",
                        active: true,
                        name: "",
                        lang: "",
                        product: "",
                        productList: []
                    }
                },
                currentSort: 'name',
                currentSortDir: 'asc'
            }
        },
        created() {
            axios({url: '/backend/recipe/recipes', method: 'GET'})
                .then(response => {
                    this.recipes.data = response.data;
                });
        },
        methods: {
            toggleModals: function (data, index) {
                this.recipes.data.recipe.splice(index, 1, data);
            },
            showModal(index) {
                this.isModalVisible = true;
                var obj = this.recipes.data.recipe[index];

                this.recipes.editableInput.index = index;
                this.recipes.editableInput.id = obj.id;
                this.recipes.editableInput.type = obj.type;
                this.recipes.editableInput.name = obj.name;
                this.recipes.editableInput.lang = obj.lang.toLowerCase();
                this.recipes.editableInput.productList = obj.productList;

            },
            closeModal() {
                this.isModalVisible = false;
            },
            sort: function (str) {
                if (str === 'products' || str === 'actions') return;
                const s = str.toLowerCase();
                //if s == current sort, reverse
                if (s === this.currentSort) {
                    this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';
                }
                this.currentSort = s;
            },
            //function to add data to table
            add: function () {
                const str = JSON.stringify({
                    type: this.recipes.input.type,
                    active: this.recipes.input.active,
                    name: this.recipes.input.name,
                    lang: this.recipes.input.lang,
                    productList: this.recipes.input.productList
                });
                axios({url: '/backend/recipe/', data: str, method: 'POST'})
                    .then((response) => {
                        this.recipes.data.recipe.push({
                            id: response.data.id,
                            type: response.data.type,
                            active: response.data.active,
                            name: response.data.name,
                            lang: response.data.lang,
                            productList: response.data.productList
                        });
                    })
                    .catch((error) => {
                        console.log(error);
                        alert("Error occurred: " + error)
                    });

                for (const key in this.recipes.input) {
                    this.recipes.input[key] = '';
                }
                this.recipes.input.productList = [];
                this.recipes.input.type = 'OTHER';
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
                    this.recipes.data.recipe.splice(index, 1);

                    axios({url: '/backend/recipe/' + recipeId, method: 'DELETE'})
                        .then((response) => {
                            console.log(response);
                        })
                        .catch((error) => {
                            console.log(error);
                            alert("Error occurred: " + error)
                        });
                }
            }
        },
        computed: {
            sorted: function () {
                if (this.recipes.data.recipe == null) {
                    return [];
                }
                return this.recipes.data.recipe.sort((a, b) => {
                    let modifier = 1;
                    if (this.currentSortDir === 'desc') modifier = -1;
                    if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
                    if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
                    return 0;
                });
            },
            isLoggedIn: function () {
                return this.$store.getters.isLoggedIn
            }
        }
    }

</script>
//*[@id="content"]/div[1]/div/div/div
<style scoped>

</style>