export default {
    state: {
        socket: null,
        status: "matching", // matching:表示匹配中, playing:表示正在游戏
        opponent_username: "",
        opponent_photo: "",
        opponent_rating: "",
        gamemep: null
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
        updateGamemep(state, gamemep) {
            state.gamemep = gamemep;
        }
    },
    actions: {

    },
    modules: {
    }
}