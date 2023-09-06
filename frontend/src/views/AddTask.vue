<script setup>
import { ref, onMounted, computed, onBeforeMount } from 'vue';
import { useRouter } from 'vue-router';
import Navbar from '../components/Navbar.vue';
import axios from 'axios';
import ApiConnection from '../services/ApiConnection'

const task = ref({
    title: '',
    description: '',
    dueDate: '',
    isCompleted: ''
});

const users = ref([]);
const categories = ref();
const selectedUser = ref("Select_from_list");
const selectedCategory = ref("Select_from_list");
const addCategory = ref(false);
const addedCategory = ref({title: ''});
const router = useRouter();
const currentCategories = ref();

const addtask = () => {
    const formattedDueDate = new Date(task.value.dueDate).toISOString();
    const newTask = {
        ...task.value,
        dueDate: formattedDueDate,
        user: {
            id: selectedUser.value
        },
        category: {
            title: selectedCategory.value
        }
    }
	try{
		ApiConnection.addTask(newTask);
		alert("task Sucessfully added!")
		router.push('/tasklist');
	}
	catch (error)
	{
		alert("Not able to add task:" + error);
	}
}

const isCompleted = computed(() => {
    return (
        task.value.description.trim() !== '' &&
        task.value.title.trim() !== '' &&
        task.value.dueDate.trim() !== '' &&
        selectedUser.value !== 'Select_from_list'   
    );
})

const ftAddCategory = () =>
{
	if (currentCategories.value.includes(addedCategory.value.title.toLocaleLowerCase()))
		alert("Category already exists: " + addedCategory.value.title);
	else
	{
		categories.value.push(addedCategory.value);
		ApiConnection.addCategory(addedCategory.value);
		alert("New category " + addedCategory.value.title + " added");
		addedCategory.value = '';
		addCategory.value = false;
	}
}

onBeforeMount(async() => {
	let uResponse = await ApiConnection.fetchUsers();
	let cResponse = await ApiConnection.fetchCategories();

	users.value = uResponse.data;
	categories.value = cResponse.data;
	currentCategories.value = categories.value.map(x => x.title.toLowerCase());
})

</script>
<template>
    <main>
        <Navbar/>
        <div class="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
            <div class="row gx-lg-5 align-items-center mb-5">
                <div class="col-lg-6 mb-5 mb-lg-0" style="z-index: 10">
                    <h1 class="my-5 display-5 fw-bold ls-tight" style="color: hsl(218, 81%, 95%)"> Keep in mind that you'r going to
                      <br />
                        <span style="color: hsl(218, 81%, 75%)">Make your future</span>
                    </h1>
                    <p class="mb-4 opacity-70" style="color: hsl(218, 81%, 85%)">
                        Good Luck!
                    </p>
                </div>
                <div class="col-lg-6 mb-5 mb-lg-0 position-relative">
                    <div id="radius-shape-1" class="position-absolute rounded-circle shadow-5-strong"></div>
                    <div id="radius-shape-2" class="position-absolute shadow-5-strong"></div>
                    <h2 class="mt-3 display-5 fw-bold ls-tight text-center" style="color: hsl(218, 81%, 75%)">{{ $t("taskPageTitle") }}</h2>
                    <div class="card-body px-4 py-5 px-md-5">
                        <form @submit.prevent="addtask">
                            <div class="form-outline mb-4">
                                <div class="col-md-12 form-group mb-3">
                                    <label for="title" class="form-label">{{ $t("Title") }}</label>
                                    <input id="title" type="text" name="name" class="form-control" placeholder="title"
                                        required v-model="task.title">
                                </div>
                            </div>
                            <div class="form-outline mb-4">
                                <div class="col-md-12 form-group mb-3">
                                    <label for="description" class="form-label">{{ $t("description") }}</label>
                                    <textarea id="description" type="text" name="description" class="form-control"
                                        placeholder="description" required v-model="task.description"></textarea>
                                </div>
                            </div>
                            <div class="form-outline mb-4">
                                <label for="dueDate" class="form-label">{{$t("duedate")}}</label>
                                <input id="dueDate" type="date" name="dueDate" class="form-control" required
                                    v-model="task.dueDate" />
                            </div>
                            <!-- Dropdown -->
                            <div class="form-outline mb-4">
                                <div class="col-md-12 form-group mb-3">
                                    <label for="userName" class="form-label">{{ $t("assignTo") }}</label>
                                    <select id="userName" name="userName" class="form-control" v-model="selectedUser">
                                        <option value="Select_from_list" disabled>Select_from_list</option>
                                        <option v-for="user in users" :key="user.id" :value="user.id">{{ user.username }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <!-- Category -->
                            <div class="form-outline mb-4">
                                <div class="col-md-12 form-group mb-3">
                                    <label for="category" class="form-label">{{ $t("category") }}</label>
                                    <select id="category" name="category" class="form-control" v-model="selectedCategory">
                                        <option value="Select_from_list" disabled>Select_from_list</option>
                                        <option v-for="category in categories" :key="category.id" :value="category.title">{{ category.title }}
                                        </option>
                                    </select>
                                </div>
                            </div>
							<div class="add-category">
								<div class="add-category-button">
									<p>Add New Category</p>
									<button class="plus-button" @click="addCategory = !addCategory">+</button>
								</div>
								<div v-if="addCategory">
									<input type="text" v-model="addedCategory.title">
									<button @click="ftAddCategory()">add</button>
								</div>
							</div>
                            <br><br>
                            <div class="form-outline mb-4">
                                <div class="col-md-12 form-group">
                                    <input class="btn btn-primary w-100" type="submit" :disabled="!isCompleted" value="Submit">
                                </div>
                            </div>
                            <div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped>
	.add-category
	{
		display: flex;
		justify-content: space-around;
	}
	.add-category-button
	{
		display: flex;
	}



	.plus-button
	{
		background-color: #4CAF50;
  		border: none;
  		color: white;
  		padding: 5px 10px;
  		text-align: center;
  		text-decoration: none;
  		display: flex;
    	height: 27px;
    	align-items: center;
  		font-size: 20px;
		margin: 0 1rem;
	}
</style>