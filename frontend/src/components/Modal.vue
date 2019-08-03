<template>
    <transition name="modal-fade">
        <div class="modal-backdrop">
            <div class="modal"
                 role="dialog"
                 aria-labelledby="modalTitle"
                 aria-describedby="modalDescription">
                <header id="modalTitle">
                    <slot name="header" style="position: center">
                        <button type="button" @click="close" aria-label="Close modal" style="float: right;">
                            <i class="material-icons">
                                cancel
                            </i>
                        </button>
                    </slot>
                </header>
                <div class="modal-body"
                     id="modalDescription">
                    <div class="col-sm-4">
                        <select class="col-sm-4" v-model="type" id="type" style="min-width: 100%; margin: 1px">
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
                    </div>
                    <div class="input-field col-sm-4" style="min-width: 100%; margin: 1px">
                        <input placeholder="Name" v-model="name" id="name" type="text" ref="name"
                               style="min-width: 100%;">
                    </div>
                    <div class="input-field col-sm-4" style="min-width: 100%;">
                        <select disabled v-model="lang" id="lang" style="min-width: 100%;">
                            <option>ru</option>
                            <option>en</option>
                        </select>
                    </div>
                    <div class="input-field col-sm-4" style="min-width: 100%; margin: 1px">
                        <input placeholder="Product" v-model="product" id="product" type="text"
                               v-on:keyup.enter="addProduct">
                        <a href="#!" @click="addProduct" class="btn green darken-2">
                            <i class="material-icons">add_circle</i>
                        </a>
                        <label for="product" @click="addProduct" style="cursor: pointer;">Product</label>
                        <p v-for="(product, i) in productList" :key="i">
                            {{product.name}}
                            <a href="#!" @click="removeProduct(i)" class="btn green darken-2" style="float: right;">
                                <i class="material-icons">remove_circle_outline</i>
                            </a>
                        </p>
                    </div>
                    <button type="button" class="btn-edit" @click="edit" style="margin: 1px">
                        <i class="material-icons">save_alt</i>
                    </button>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
    import axios from 'axios';

    export default {
        name: "Modal.vue",
        props: ['index', 'id', 'type', 'name', 'lang', 'productList', 'product'],
        methods: {
            close() {
                this.$emit('close');
            },
            addProduct: function () {
                if (this.$props.product !== '') {
                    this.$props.productList.push(
                        {
                            name: this.$props.product,
                            lang: this.$props.lang,
                            active: true
                        });
                    this.$props.product = ''
                }
            },
            removeProduct: function (index) {
                this.$props.productList.splice(index, 1);
            },
            edit: function () {
                const str = JSON.stringify({
                    id: this.$props.id,
                    type: this.$props.type,
                    active: this.$props.active,
                    name: this.$props.name,
                    lang: this.$props.lang,
                    productList: this.$props.productList
                });

                axios.put('/backend/recipe/' + this.$props.id, str, {headers: {'Content-Type': 'application/json'}})
                    .then((response) => {
                        console.log(response);
                        this.$emit('toggle', response.data, this.$props.index);
                    })
                    .catch((error) => {
                        console.log(error);
                        alert("Error occurred: " + error)
                    });

                this.close();

            }
        }
    }
</script>

<style scoped>
    .modal-backdrop {
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: rgba(0, 0, 0, 0.3);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .modal {
        background: #FFFFFF;
        box-shadow: 2px 2px 20px 1px;
        overflow-x: auto;
        display: flex;
        flex-direction: column;
    }

    .modal-header,
    .modal-footer {
        padding: 15px;
        display: flex;
    }

    .modal-header {
        border-bottom: 1px solid #eeeeee;
        color: #4AAE9B;
        justify-content: space-between;
    }

    .modal-footer {
        border-top: 1px solid #eeeeee;
        justify-content: flex-end;
    }

    .modal-body {
        position: relative;
        padding: 40px 20px;
    }

    .btn-close {
        border: none;
        font-size: 20px;
        padding: 10px;
        cursor: pointer;
        font-weight: bold;
        color: #4AAE9B;
        background: transparent;
    }

    .btn-green {
        color: white;
        background: #4AAE9B;
        border: 1px solid #4AAE9B;
        border-radius: 2px;
    }
</style>