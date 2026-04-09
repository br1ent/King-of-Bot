<template>
    <ContentField v-if="!$store.state.user.pulling_info">
        <div class="row justify-content-md-center">
            <div class="col-3">
                <div v-if="successMsg" class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center;">
                    {{ successMsg }}
                    <button type="button" class="btn-close" @click="successMsg = ''" aria-label="Close"></button>
                </div>
                <div v-if="errMsg" class="alert alert-danger alert-dismissible fade show" role="alert">
                    {{ errMsg }}
                </div>
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input type="text" class="form-control" id="username" placeholder="请输入用户名" v-model="username">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input type="password" class="form-control" id="password" placeholder="请输入密码" v-model="password">
                    </div>
                    <button type="submit" class="btn btn-primary" style="width: 100%;">登录</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script setup>
import ContentField from '@/components/ContentField.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore();
const username = ref("");
const password = ref("");
const errMsg = ref("");
const successMsg = ref("");
const router = useRouter();

const jwt_token = localStorage.getItem("jwt_token");
if (jwt_token) {
    store.commit("updateToken", jwt_token);
    store.dispatch("getinfo", {
        success() {
            router.push({name: 'home'});
            store.commit("updatePullingInfo", false);
        },
        error() {
            store.commit("updatePullingInfo", false);
        }
    })
} else {
    store.commit("updatePullingInfo", false);
}

const login = () => {
    errMsg.value = "",
    store.dispatch("login", {
        username: username.value,
        password: password.value,
        success() {
            store.dispatch("getinfo", {
                success() { 
                    successMsg.value = "登录成功!"
                    setTimeout(() => {
                        router.push({name: 'home'});
                    }, 1000);
                }
            })
        },
        error() {
            errMsg.value = "用户名或密码错误!"
        }
    });
};

</script>

<style scoped>
div.errMsg {
    color: red;
    text-align: center;
    margin-bottom: 10px;
}
</style>