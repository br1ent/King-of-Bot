<template>
    <LoginRegisterField>
        <h3 class="text-center mb-4 fw-bold text-primary">注 册</h3>

        <div v-if="successMsg" class="alert alert-success border-0 shadow-sm mb-3 text-center">
            {{ successMsg }}
        </div>
        <div v-if="errMsg" class="alert alert-danger border-0 shadow-sm mb-3 text-center">
            {{ errMsg }}
        </div>

        <form @submit.prevent="register">
            <div class="mb-3">
                <label for="username" class="form-label small fw-bold">用户名</label>
                <input 
                    type="text" 
                    class="form-control form-control-lg shadow-sm" 
                    id="username" 
                    placeholder="请输入用户名" 
                    v-model="username"
                >
            </div>
            <div class="mb-3">
                <label for="password" class="form-label small fw-bold">密码</label>
                <input 
                    type="password" 
                    class="form-control form-control-lg shadow-sm" 
                    id="password" 
                    placeholder="请输入密码" 
                    v-model="password"
                >
            </div>
            <div class="mb-4">
                <label for="confirmedPassword" class="form-label small fw-bold">确认密码</label>
                <input 
                    type="password" 
                    class="form-control form-control-lg shadow-sm" 
                    id="confirmedPassword" 
                    placeholder="请再次输入密码" 
                    v-model="confirmedPassword"
                >
            </div>
            
            <button type="submit" class="btn btn-primary btn-lg w-100 shadow-sm fw-bold">
                注 册
            </button>
        </form>

        <div class="text-center mt-3">
            <small class="text-muted">已有账号？
                <router-link :to="{name: 'login'}" class="text-decoration-none fw-bold">立即登录</router-link>
            </small>
        </div>
    </LoginRegisterField>
</template>

<script setup>
import LoginRegisterField from '@/components/LoginRegisterField.vue';
import $ from "jquery"
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const username = ref("");
const password = ref("");
const confirmedPassword = ref("");
const errMsg = ref("");
const router = useRouter();
const successMsg = ref("");

const register = () => {
    errMsg.value = "";
    successMsg.value = "";
    $.ajax({
        url: "http://127.0.0.1:3000/api/user/account/register",
        type: "post",
        data: {
            username: username.value,
            password: password.value,
            confirmedPassword: password.value
        },
        success(resp) {
            if (resp.error_message === "success") {
                successMsg.value = "注册成功！正在跳转登录页面..."
                setTimeout(() => {
                    router.push({name: "login"});
                }, 1000);
            } else {
                errMsg.value = resp.error_message;
            }
        },
        error(resp) {
            errMsg.value = "服务器连接失败!请稍后重试";
        }
    })
}
</script>

<style scoped>
.form-control:focus {
    border-color: #0d6efd;
    box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
}
</style>