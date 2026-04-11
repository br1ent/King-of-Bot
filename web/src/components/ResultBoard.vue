<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.loser === 'all'">
            平局!
        </div>
        <div class="result-board-text" 
        v-else-if="$store.state.pk.loser === 'A' && $store.state.pk.a_id === parseInt($store.state.user.id)">
            你输了!
        </div>
        <div class="result-board-text" 
        v-else-if="$store.state.pk.loser === 'B' && $store.state.pk.b_id === parseInt($store.state.user.id)">
            你输了!
        </div>
        <div class="result-board-text" v-else>
            你赢了!
        </div>
        <div class="result-board-btn">
            <button type="button" class="btn btn-info" @click="restart">再来一把!</button>
        </div>
    </div>
</template>

<script setup>
import { useStore } from 'vuex';

const store = useStore();
const restart = () => {
    store.commit("updateStatus", "matching");
    store.commit("updateLoser", "none");
    store.commit("updateOpponentInfo", {
        username: "我的对手",
        photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
        rating: ""
    });
}


</script>

<style scoped>
div.result-board {
    height: 30vh;
    width: 30vw;
    background-color: rgba(50, 50, 50, 0.5);
    position: absolute;
    top: 30vh;
    left: 35vw;
}

div.result-board-text {
    text-align: center;
    color: white;
    font-size: 50px;
    font-weight: bold;
    font-style: italic;
    padding: 5vh;
}

div.result-board-btn {
    text-align: center;
    padding: 5vh;
}
</style>