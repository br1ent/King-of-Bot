<template>
  <div>
    <NavBar v-if="shouldShowNavBar"></NavBar>
    <router-view></router-view>
  </div>
</template>


<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap'
import NavBar from './components/NavBar.vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import { computed, onMounted } from 'vue';

const store = useStore();
const route = useRoute();

const shouldShowNavBar = computed(() => {
    if (store.state.pk.status === 'playing') return false;
   
    return true;
});

// 应用启动时恢复用户登录状态
onMounted(() => {
    const token = localStorage.getItem("jwt_token");
    if (token) {
        store.commit("updateToken", token);
    
        store.dispatch("getinfo", {
            error() {
                localStorage.removeItem("jwt_token");
                store.commit("logout");
            }
        });
    } else {
        store.commit("updatePullingInfo", false);
    }
});

</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>