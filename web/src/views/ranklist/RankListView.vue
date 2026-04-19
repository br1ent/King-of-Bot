<template>
    <ContentField>
        <table class="table table-hover" style="text-align: center;">
            <thead>
                <tr>
                    <th>玩家</th>
                    <th>天梯分</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user in users" :key="user.id">
                    <td>
                        <img :src="user.photo" alt="玩家头像" class="user-photo">
                        &nbsp;
                        <span class="user-name">{{ user.username }}</span>
                    </td>
                    <td>{{ user.rating }}</td>
                </tr>
            </tbody>
        </table>
        <nav>
            <ul class="pagination" style="float: right;">
                <li class="page-item" @click="click_page(-2)">
                    <a class="page-link" href="#" tabindex="-1" aria-disabled="true">上一页</a>
                </li>
                <li :class="'page-item ' + page.is_active" @click="click_page(page.number)" v-for="page in pages" :key="page.number">
                    <a class="page-link" href="#">{{ page.number }}</a>
                </li>
                <li class="page-item" @click="click_page(-1)">
                    <a class="page-link" href="#">下一页</a>
                </li>
            </ul>
        </nav>
    </ContentField>
</template>

<script setup>
import ContentField from '../../components/ContentField.vue';
import { useStore } from 'vuex';
import $ from 'jquery';
import { ref } from 'vue';

const store = useStore();
let current_page = 1;
let users = ref([]);
let total_users = 0;

let pages = ref([]);

const click_page = page => {
    if (page === -2) {
        page = current_page - 1;
    } else if (page === -1) {
        page = current_page + 1;
    }
    let max_pages = parseInt(Math.ceil(total_users / 10));

    if (page >= 1 && page <= max_pages) {
        pull_page(page);
    }
    update_pages();
}

const update_pages = () => {
    let max_pages = parseInt(Math.ceil(total_users / 10));

    let new_pages = [];
    for (let i = current_page - 2; i <= current_page + 2; i ++ ) {
        if (i >= 1 && i <= max_pages) {
            new_pages.push({
                number: i,
                is_active: i === current_page ? "active" : ""
            });
        }
    }

    pages.value = new_pages;
}

const pull_page = page => {
    current_page = page;
    $.ajax({
        url: "http://127.0.0.1:3000/api/ranklist/getlist",
        type: "get",
        headers: {
            Authorization: "Bearer " + store.state.user.token
        },
        data: {
            page
        },
        success(resp) {
            console.log(resp);
            users.value = resp.users;
            total_users = resp.users_count;
            update_pages();
        },
        error(resp) {
            console.log(resp);
        }
    });
}
pull_page(current_page);


</script>

<style scoped>
img.user-photo {
    width: 4vh;
    border-radius: 50%;
}
</style>