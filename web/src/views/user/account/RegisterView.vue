<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <div v-if="successMsg" class="alert alert-success alert-dismissible fade show" role="alert">
                    {{ successMsg }}
                    <button type="button" class="btn-close" @click="successMsg = ''" aria-label="Close"></button>
                </div>
                <div v-if="errMsg" class="alert alert-danger alert-dismissible fade show" role="alert">
                    {{ errMsg }}
                </div>
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input type="text" class="form-control" id="username" placeholder="请输入用户名" v-model="username">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input type="password" class="form-control" id="password" placeholder="请输入密码" v-model="password">
                    </div>
                    <div class="mb-3">
                        <label for="confirmed_password" class="form-label">确认密码</label>
                        <input type="password" class="form-control" id="confirmed_password" placeholder="请确认密码" v-model="confirmedPassword">
                    </div>
                    <button type="submit" class="btn btn-primary" style="width: 100%;">注册</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script setup>
import ContentField from '@/components/ContentField.vue';
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
        url: "http://127.0.0.1:3000/user/account/register",
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

div.errMsg {
    color: red;
    text-align: center;
    margin-bottom: 10px;
}
</style>