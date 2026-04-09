<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <router-link class="navbar-brand" :to="{name: 'pk'}">King Of Bots</router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <router-link :class="route_name === 'pk' ? 'nav-link active' : 'nav-link'" :to="{name: 'pk'}">
                            对战
                         </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="route_name === 'record' ? 'nav-link active' : 'nav-link'" :to="{name: 'record'}">
                            对局列表
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="route_name === 'ranklist' ? 'nav-link active' : 'nav-link'" :to="{name: 'ranklist'}">
                            排行榜
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav" v-if="$store.state.user.is_login">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            {{ $store.state.user.username }}
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link class="dropdown-item" :to="{name: 'user_bot'}">我的bot</router-link>
                            </li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item" href="#" @click="logout">退出</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <ul class="navbar-nav" v-else-if="!store.state.user.pulling_info">
                    <li class="nav-item">
                        <router-link class="nav-link" :to="{name: 'login'}" role="button">
                            登录
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" :to="{name: 'register'}" role="button">
                            注册
                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script setup>
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';

const route = useRoute()
let route_name = computed(() => route.name)

const router = useRouter();
const store = useStore();
const logout = () => {
    store.dispatch("logout");
    router.push({name: "login"})
}

</script>

<style scoped>

</style>