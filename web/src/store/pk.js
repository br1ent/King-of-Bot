export default {
    state: {
        socket: null,
        status: "mode_select", // matching:表示匹配页面, playing:表示多人游戏页面, mode_select:表示模式选择页面, singleplayer:表示单人游戏页面
        mode: "", // singleplayer:单人游戏, multiplayer:多人游戏
        opponent_username: "",
        opponent_photo: "",
        opponent_rating: "",
        gamemap: null,
        a_id: 0,
        a_sx: 0,
        a_sy: 0,
        b_id: 0,
        b_sx: 0,
        b_sy: 0,
        gameObject: null,
        loser: "none", // none, all, A, B
        ai_difficulty: "easy", // easy: 简单, medium: 普通, hard: 困难
    },
    getters: {
    },
    mutations: {
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponentInfo(state, opponent) {
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
            state.opponent_rating = opponent.rating;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateMode(state, mode) {
            state.mode = mode;
        },
        updateGame(state, game) {
            state.gamemap = game.map;
            state.a_id = game.a_id;
            state.a_sx = game.a_sx;
            state.a_sy = game.a_sy;
            state.b_id = game.b_id;
            state.b_sx = game.b_sx;
            state.b_sy = game.b_sy;
        },
        updateGameObject(state, gameObject) {
            state.gameObject = gameObject;
        },
        updateLoser(state, loser) {
            state.loser = loser;
        },
        updateAiDifficulty(state, difficulty) {
            state.ai_difficulty = difficulty;
        }
    },
    actions: {

    },
    modules: {
    }
}