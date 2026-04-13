<template>
    <div class="matchground">
        <div class="row">
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="用户头像">
                </div>
                <div class="user-name">
                    {{ $store.state.user.username }}
                </div>
                <div class="user-rating">
                    {{ $store.state.user.rating }}
                </div>
            </div>
            <div class="col-4">
                <div class="bot-select">
                    <select class="form-select" v-model="select_bot">
                        <option selected value="-1">亲自出马</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id">{{ bot.title }}</option>
                    </select>
                </div>
            </div>
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.pk.opponent_photo" alt="对手头像">
                </div>
                <div class="user-name">
                    {{ $store.state.pk.opponent_username }}
                </div>
                <div class="user-rating">
                    {{ $store.state.pk.opponent_rating }}
                </div>
            </div>
            <div class="col-12">
                <button type="button" class="btn btn-success btn-lg" @click="startMatch">{{ btn_match }}</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useStore } from 'vuex';
import $ from 'jquery';

const store = useStore();
let btn_match = ref("开始匹配");

let select_bot = ref("-1");

const startMatch = () => {
    if (btn_match.value === "开始匹配") {
        btn_match.value = "取消匹配";
        store.state.pk.socket.send(JSON.stringify({
            event: "start_matching",
            bot_id: select_bot.value
        }));
    } else {
        btn_match.value = "开始匹配";
        store.state.pk.socket.send(JSON.stringify({
            event: "stop_matching"
        }));
    }
};

let bots = ref([]);
const getList = () => {
    $.ajax({
        url: "http://127.0.0.1:3000/user/bot/getlist",
        type: "get",
        headers: {
            Authorization: "Bearer " + store.state.user.token
        },
        success(resp) {
            bots.value = resp;
        }
    });
}
getList();

</script>

<style scoped>

div.matchground {
    width: 60vw;
    height: 70vh;
    background-color: rgba(50, 50, 50, 0.5);
    margin: 40px auto;
}

div.user-photo {
    text-align: center;
    margin-top: 10vh;
}

div.user-photo img {
    width: 150px;
    height: 150px;
    border-radius: 50%;
}

div.user-name {
    text-align: center;
    margin-top: 20px;
    font-size: 24px;
    font-weight: bold;
    color: white;
}

div.user-rating {
    text-align: center;
    margin-top: 10px;
    font-size: 18px;
    color: lightblue;
}

button {
    display: block;
    margin: 15vh auto;
}

div.bot-select {
    margin: 20vh auto;
}

div.bot-select > select {
    width: 60%;
    margin: 0 auto;
}
</style>