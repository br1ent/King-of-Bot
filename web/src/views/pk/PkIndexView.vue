<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'">
    </PlayGround>

    <MatchGround v-else-if="$store.state.pk.status === 'matching'">
    </MatchGround>
</template>

<script setup>
import { onMounted, onUnmounted } from 'vue';
import PlayGround from '../../components/PlayGround.vue';
import MatchGround from '../../components/MatchGround.vue';
import { useStore } from 'vuex';

const store = useStore();
let socket = null;
const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}`;

onMounted(() => {
    store.commit("updateOpponentInfo", {
        username: "我的对手",
        photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
        rating: store.state.pk.opponent_rating
    });

    socket = new WebSocket(socketUrl);

    socket.onopen = () => {
        console.log("WebSocket连接已打开");
        store.commit("updateSocket", socket);
    };

    socket.onclose = () => {
        console.log("WebSocket连接已关闭");
    };

    socket.onmessage = msg => {
        const data = JSON.parse(msg.data);
        console.log("收到消息:", data);
        if (data.event === "start-matching") {
            store.commit("updateOpponentInfo", {
                username: data.opponent_username,
                photo: data.opponent_photo,
                rating: data.opponent_rating
            });
            store.commit("updateGamemep", data.gamemap);
            setTimeout(() => {
                store.commit("updateStatus", "playing");
            }, 2000);
        }
    }
});

onUnmounted(() => {
    if (socket) {
        socket.close();
    }
    store.commit("updateStatus", "matching");
});

</script>

<style scoped>
</style>