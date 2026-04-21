vue f:\MyProject\King Of Bot\web\src\components\ModeSelectField.vue
<template>
    <div class="mode-select-field">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="mode-card">
                        <h2 class="text-center mb-4">选择游戏模式</h2>
                        
                        <div class="row">
                            <!-- 多人对战模式 -->
                            <div class="col-md-6 mb-4">
                                <div class="card mode-card-item" @click="selectMode('multiplayer')">
                                    <div class="card-body text-center">
                                        <div class="mode-icon">
                                            <i class="fas fa-users fa-3x"></i>
                                        </div>
                                        <h4 class="card-title">多人对战</h4>
                                        <p class="card-text">与真实玩家实时对战，体验竞技乐趣</p>
                                        <ul class="text-start">
                                            <li>实时匹配其他玩家</li>
                                            <li>天梯积分系统</li>
                                            <li>排行榜竞争</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- 单机模式 -->
                            <div class="col-md-6 mb-4">
                                <div class="card mode-card-item" @click="selectMode('singleplayer')">
                                    <div class="card-body text-center">
                                        <div class="mode-icon">
                                            <i class="fas fa-robot fa-3x"></i>
                                        </div>
                                        <h4 class="card-title">单机模式</h4>
                                        <p class="card-text">与AI机器人对战，练习技巧</p>
                                        <ul class="text-start">
                                            <li>多种难度AI对手</li>
                                            <li>随时开始游戏</li>
                                            <li>无网络要求</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const store = useStore();
const router = useRouter();

const selectMode = (mode) => {
    store.commit('updateMode', mode);
    
    if (mode === 'multiplayer') {
        store.commit('updateStatus', 'matching');
        router.push('/pk');
    } else if (mode === 'singleplayer') {
        store.commit('updateStatus', 'single_player');
        router.push('/pk');
    }
};
</script>

<style scoped>
.mode-select-field {
    min-height: 70vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(50, 50, 50, 0.5);
    margin: 40px auto;
    border-radius: 15px;
    width: 80vw;
}

.mode-card {
    padding: 40px;
    background-color: rgba(216, 199, 199, 0.546);
    border-radius: 15px;
}

.mode-card-item {
    cursor: pointer;
    transition: all 0.3s ease;
    background-color: rgba(255, 255, 255, 0.225);
    border: 2px solid transparent;
    height: 100%;
}

.mode-card-item:hover {
    transform: translateY(-5px);
    border-color: #007bff;
    box-shadow: 0 10px 20px rgba(0, 123, 255, 0.3);
}

.mode-icon {
    color: #007bff;
    margin-bottom: 20px;
}

.card-title {
    color: black;
    margin-bottom: 15px;
}

.card-text {
    color: balck;
    margin-bottom: 20px;
}

.card-text ul {
    color: #ddd;
    padding-left: 20px;
}

.card-text li {
    margin-bottom: 5px;
}
</style>