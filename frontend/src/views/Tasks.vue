<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Navbar from '../components/Navbar.vue';
import ApiConnection from '../services/ApiConnection';

const tasks = ref([]);
const filterUsername = ref('');
const filteredTasks = ref([]);
const router = useRouter();
const route = useRoute();


const formatDueDateForBackend = (date) => {
    const formattedDate = new Date(date);
    const year = formattedDate.getFullYear();
    const month = (formattedDate.getMonth() + 1).toString().padStart(2, '0');
    const day = formattedDate.getDate().toString().padStart(2, '0');
    const hours = formattedDate.getHours().toString().padStart(2, '0');
    const minutes = formattedDate.getMinutes().toString().padStart(2, '0');
    const seconds = formattedDate.getSeconds().toString().padStart(2, '0');
    const formattedDateString = `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
    return formattedDateString;
};

const reminder = (task) => {
    const currentDate = new Date();
    const dueDateValue = new Date(task.dueDate);
    const timeRemainingInDays = Math.ceil((dueDateValue - currentDate) / (1000 * 60 * 60 * 24));
    return timeRemainingInDays < 2 ? "table-danger" : "";
};

const getTasks = async () => {
    let response = await ApiConnection.getAllTasks();
    tasks.value = response.data.map(task => {
        if (task.user && task.user.profilePicture) {
            task.user.profilePicture = `http://localhost:8080/Images/${task.user.profilePicture}`;
        } else {
            task.user.profilePicture = "";
        }
        return {
            ...task,
        }
    });

};

const updateCompletionStatus = async (task) => {
    let updatedTask = task;
    updatedTask.isCompleted = !task.isCompleted;
    const formattedDueDate = formatDueDateForBackend(task.dueDate);
    updatedTask.dueDate = formattedDueDate;
    await ApiConnection.updateCompletionStatus(task.id, updatedTask);
    task.isCompleted = updatedTask.isCompleted;
    alert("Status updated successfully");
    location.reload();
}

const deleteTask = async (id) => {
    await ApiConnection.deleteTaskById(id);
    getTasks();
    alert("Task successfully erased!");
    location.reload();
};

const applyFilter = () => {
    filteredTasks.value = tasks.value.filter(task => {
        if (!filterUsername.value.trim() || task.user && task.user.username.toLowerCase().includes(filterUsername.value.toLowerCase())) {
            return true;
        }
        return false;
    });
};

onMounted(() => {
    getTasks();
    applyFilter();
});

</script>
<template>
    <main>
        <Navbar/>
        <div class="container">
            <div class="row ">
                <div class="col-md-12">
                    <div class="d-flex mt-3 justify-content-center">
                        <h1 style="color: hsl(218, 81%, 75%)">Your Todo List</h1>
                    </div>
                    <div class="d-flex justify-content-start" id="newtask">
                        <a href="/addtask" class="btn btn-primary">New Task</a>
                    </div>
                    <div class="input-group mb-3 mt-3">
                        <input type="text" class="form-control" v-model="filterUsername" @keyup.enter="applyFilter"
                            placeholder="Enter username">
                        <button class="btn btn-outline-primary" type="button" @click="applyFilter">Search</button>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">Title</th>
                                    <th scope="col">Description </th>
                                    <th scope="col">Due Date</th>
                                    <th scope="col">Assigned To</th>
                                    <th scope="col">Is Completed?</th>
                                    <th scope="col">Category</th>
                                    <th scope="col">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="task in filteredTasks" :key="task.id" :class="reminder(task)">
                                    <th scope="row">{{ task.title }}</th>
                                    <td>{{ task.description }}</td>
                                    <td>{{ task.dueDate }}</td>
                                    <td v-if="task.user">
                                        <div class="d-flex align-items-center">
                                            <img :src="task.user.profilePicture" alt="Profile Picture"
                                                class="profile-picture" />
                                            <span class="mx-2">{{ task.user.username }}</span>
                                        </div>
                                    </td>
                                    <td v-else></td>
                                    <td>
                                        <input type="checkbox" id="checkbox" v-model="task.isCompleted"
                                            @change="updateCompletionStatus(task)">
                                    </td>
                                    <td>{{ task.category.title }}</td>
                                    <td>
                                        <div class="button-group">
                                        <a class="btn btn-primary" :href="`/update/${task.id}`">Edit</a>
                                        <button class="btn btn-danger mx-2" @click="deleteTask(task.id)">Delete</button>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped>
.profile-picture {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    object-fit: cover;
    margin-right: 8px;
}

.button-group {
    display: flex;
    align-items: center;
}

.button-edit,
.button-delete {
    flex: 1;
    margin: 0 4px;
    width: 100%;
    max-width: 120px;
}</style>
