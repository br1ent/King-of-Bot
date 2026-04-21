<template>
    <div class="single-player-ground">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="setup-card">
                        <h2 class="text-center mb-4">单机模式设置</h2>
                        
                        <!-- AI难度选择 -->
                        <div class="mb-4">
                            <label class="form-label">选择AI难度</label>
                            <select class="form-select" v-model="selectedDifficulty" @change="updateDifficulty">
                                <option value="easy">简单</option>
                                <option value="medium">中等</option>
                                <option value="hard">困难</option>
                            </select>
                        </div>
                        
                        <!-- 玩家信息 -->
                        <div class="row mb-4">
                            <div class="col-6">
                                <div class="player-card">
                                    <div class="player-photo">
                                        <img :src="$store.state.user.photo" alt="玩家头像">
                                    </div>
                                    <div class="player-info">
                                        <h5>{{ $store.state.user.username }}</h5>
                                        <p>玩家</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="player-card">
                                    <div class="player-photo">
                                        <img src="/src/assets/images/ai.png" alt="AI头像">
                                    </div>
                                    <div class="player-info">
                                        <h5>{{ aiName }}</h5>
                                        <small class="text-muted">{{ difficultyText }}</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- 开始游戏按钮 -->
                        <div class="text-center">
                            <button type="button" class="btn btn-primary btn-lg" @click="startGame">
                                开始游戏
                            </button>
                            <button type="button" class="btn btn-secondary btn-lg ms-3" @click="backToModeSelect">
                                返回
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useStore } from 'vuex';
import { ref, computed } from 'vue';

const store = useStore();

const selectedDifficulty = ref("easy");

const difficultyText = computed(() => {
    switch (selectedDifficulty.value) {
        case "easy":
            return "适合新手，AI比较笨";
        case "medium":
            return "平衡难度，适合有一定经验的玩家";
        case "hard":
            return "高难度挑战，AI具有较强策略";
        default:
            return "";
    }
});

const aiName = computed(() => {
    switch (selectedDifficulty.value) {
        case "easy":
            return "智能小蛇";
        case "medium":
            return "算法大师";
        case "hard":
            return "代码猎人";
        default:
            return "AI对手";
    }
});

const updateDifficulty = () => {
    store.commit('updateAiDifficulty', selectedDifficulty.value);
};

const startGame = () => {
    store.commit('updateOpponentInfo', {
        username: aiName.value,
        photo: "/src/assets/images/ai.png",
        rating: 1500
    });

    singlePlayerGame();

    store.commit('updateStatus', 'playing');
};

const backToModeSelect = () => {
    store.commit('updateStatus', 'mode_select');
    store.commit('updateMode', '');
};

const singlePlayerGame = () => {
    const gameData = {
        a_id: -1,
        a_sx: 13,
        a_sy: 1,
        b_id: store.state.user.id,
        b_sx: 1,
        b_sy: 14,
        map: randomMap()
    };

    store.commit('updateGame', gameData);
};

const randomMap = () => {
    const rows = 15;
    const cols = 16;
    const wallsCount = 20;
    const map = Array(rows).fill().map(() => Array(cols).fill(0));

    // 添加边界墙
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < cols; j++) {
            if (i === 0 || i === rows - 1 || j === 0 || j === cols - 1) {
                map[i][j] = 1;
            }
        }
    }

    // 添加随机内部墙
    for (let i = 0; i < wallsCount; i++) {
        let r, c;
        do {
            r = Math.floor(Math.random() * (rows - 2)) + 1;
            c = Math.floor(Math.random() * (cols - 2)) + 1;
        } while (map[r][c] === 1 || 
                (r === 13 && c === 1) || // 避开玩家起始位置
                (r === 1 && c === 14));  // 避开AI起始位置
        
        map[r][c] = 1;
    }
    return map;
}


</script>

<style scoped>
.single-player-ground {
    min-height: 70vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(50, 50, 50, 0.5);
    margin: 40px auto;
    border-radius: 15px;
    width: 60vw;
}

.setup-card {
    padding: 40px;
    background-color: rgba(255, 255, 255, 0.1);
    border-radius: 15px;
}

.player-card {
    text-align: center;
    padding: 20px;
    background-color: rgba(255, 255, 255, 0.05);
    border-radius: 10px;
}

.player-photo img {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    margin-bottom: 10px;
}

.player-info h5 {
    color: #fff;
    margin-bottom: 5px;
}

.player-info p {
    color: #ccc;
    margin-bottom: 5px;
}

.form-label {
    color: #fff;
    font-weight: bold;
}

.form-text {
    color: #aaa;
    font-size: 0.9em;
}

.btn-lg {
    padding: 12px 30px;
    font-size: 1.1em;
}
</style>