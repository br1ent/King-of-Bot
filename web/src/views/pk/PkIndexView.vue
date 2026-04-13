<template>
    <div>
        <PlayGround v-if="$store.state.pk.status === 'playing'">
        </PlayGround>

        <MatchGround v-else-if="$store.state.pk.status === 'matching'">
        </MatchGround>

        <ResultBoard v-if="$store.state.pk.loser !== 'none'">
        </ResultBoard>
    </div>
</template>

<script setup>
import { onMounted, onUnmounted } from 'vue';
import PlayGround from '../../components/PlayGround.vue';
import MatchGround from '../../components/MatchGround.vue';
import ResultBoard from '../../components/ResultBoard.vue';
import { useStore } from 'vuex';

const store = useStore();
let socket = null;
const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}`;

store.commit("updateLoser", "none");
store.commit("updateIsRecord", false);

onMounted(() => {
    store.commit("updateOpponentInfo", {
        username: "我的对手",
        photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
        rating: ""
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
            store.commit("updateGame", data.game);
            console.log("匹配成功后的信息:", data);
            setTimeout(() => {
                store.commit("updateStatus", "playing");
            }, 200);
        } else if (data.event === "move") {
            console.log(data);

            const game = store.state.pk.gameObject;
            const [snake0, snake1] = game.snakes;

            snake0.set_direction(data.a_direction);
            snake1.set_direction(data.b_direction);
        } else if (data.event === "result") {
            const game = store.state.pk.gameObject;
            const [snake0, snake1] = game.snakes;

            if (data.loser === "all" || data.loser === "A") {
                snake0.status = "die";
            }
            if (data.loser === "all" || data.loser === "B") {
                snake1.status = "die";
            }
            store.commit("updateLoser", data.loser);
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