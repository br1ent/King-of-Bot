<template>
    <ContentField>
        <table class="table table-hover" style="text-align: center;">
            <thead>
                <tr>
                    <th>玩家A</th>
                    <th>玩家B</th>
                    <th>对战结果</th>
                    <th>对战时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.record.id">
                    <td>
                        <img :src="record.a_photo" alt="玩家A的头像" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-name">{{ record.a_username }}</span>
                    </td>
                    <td>
                        <img :src="record.b_photo" alt="玩家B的头像" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-name">{{ record.b_username }}</span>
                    </td>
                    <td>{{ record.result }}</td>
                    <td>{{ record.record.createtime }}</td>
                    <td>
                        <button type="button" class="btn btn-primary" @click="open_record_content(record.record.id)">查看录像</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </ContentField>
</template>

<script setup>
import ContentField from '../../components/ContentField.vue';
import { useStore } from 'vuex';
import $ from 'jquery';
import { ref } from 'vue';
import router from '@/router/index.js'

const store = useStore();
let courrent_page = 1;
let records = ref([]);
let total_records = 0;

console.log(total_records);

const pull_page = page => {
    courrent_page = page;
    $.ajax({
        url: "http://127.0.0.1:3000/record/getlist",
        type: "get",
        headers: {
            Authorization: "Bearer " + store.state.user.token
        },
        data: {
            page
        },
        success(resp) {
            records.value = resp.records;
            total_records = resp.records_count;
        },
        error(resp) {
            console.log(resp);
        }
    });
}
pull_page(courrent_page);

const stringTo2D = map =>  {
    let g = [];
    for (let i = 0, k = 0; i < 13; i ++ ) {
        let line = [];
        for (let j = 0; j < 14; j ++ , k ++ ) {
            if (map[k] === "0") line.push(0);
            else line.push(1);
        }
        g.push(line);
    }
    return g;
}

const open_record_content = recordId => {
    for (const record of records.value) {
        if (record.record.id === recordId) {
            store.commit("updateIsRecord", true);
            console.log(record);
            store.commit("updateGame", {
                map: stringTo2D(record.record.map),
                a_id: record.record.aid,
                a_sx: record.record.asx,
                a_sy: record.record.asy,
                b_id: record.record.bid,
                b_sx: record.record.bsx,
                b_sy: record.record.bsy
            });
            store.commit("updateSteps", {
                a_steps: record.record.asteps,
                b_steps: record.record.bsteps
            });
            store.commit("updateRecordLoser", record.record.loser);
            router.push({
                name: "record_content",
                params: {
                    recordId
                }
            });
            break;
        }
    }
}
</script>

<style scoped>
img.record-user-photo {
    width: 4vh;
    border-radius: 50%;
}
</style>