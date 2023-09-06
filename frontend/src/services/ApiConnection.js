import axios from 'axios'


const url = "http://localhost:8080/tasks"
const urlUsers = "http://localhost:8080/users"

class ApiConnection {
    async getAllTasks() {
        try {
            let response = await axios.get(url);
            return (response);
        } catch (error) {
            return (error.message);
        }
    }

    async getTaskById(id) {
        try {
            let response = await axios.get(`${url}/${id}`);
            // task.value = res.data;
            // selectedUser.value = res.data.user.id;
            // task.value.dueDate = res.data.dueDate.split('T')[0];
            return (response);
        } catch (error) {
            return console.error('Not able to fetch tasks:', error.response)        
        }
    }

    async deleteTaskById(id) {
        try {
            let response = await axios.delete(`${url}/${id}`);
            return (response);
        } catch (error) {
            return (error.message);
        }
    }

    async addTask(task) {
        try {
            let response = await axios.post(url, task);
            return (response);
        } catch (error) {
            return (error.message);
        }
    }

    async updateTask(id, task) {
        try {
            let response = await axios.put(`${url}/${id}`, task);
            return (response);
        } catch (error) {
            return (error.message);
        }
    }

    async fetchUsersById(id) {
        try {
            let response = await axios.get(`${urlUsers}/${id}`)          
            return (response)
        } catch (error) {
            return (error.message)
        }
    };

    async addUser(user) {
        try {
            let response = await axios.post(urlUsers, user);
            return (response);
        } catch (error) {
            return (error.message);
        }
    };

    async fetchUsers() {
        try {
            let response = await axios.get(urlUsers)
            return (response)
        } catch (error) {
            return (error.message)
        }
    }

    async updateCompletionStatus(id, task) {
        try {
            let response = await axios.put(`${url}/${id}/status`, task)
            return (response)
        }catch (error){
            return (error.message)
        }
    }

}

export default new ApiConnection();