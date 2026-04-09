<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
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
                    <div class="errMsg">{{ errMsg }}</div>
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

const register = () => {
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
                router.push({name: "login"});
            } else {
                errMsg.value = resp.error_message;
            }
        },
        error(resp) {
            errMsg.value = resp.error_message;
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