<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="用户头像" style="width: 100%;">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card">
                    <div class="card-header">
                        <span style="font-size: 130%">我的bot</span>
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal" data-bs-target="#addbot">+创建bot</button>
                    </div>
                    <!-- Modal -->
                    <div class="modal fade" id="addbot" tabindex="-1">
                        <div class="modal-dialog modal-dialog-centered modal-xl">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">创建bot</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="add-bot-title" class="form-label">名称</label>
                                    <input type="text" class="form-control" id="add-bot-title" placeholder="请输入bot名称" v-model="botadd.title">
                                </div>
                                <div class="mb-3">
                                    <label for="add-bot-description" class="form-label">简介</label>
                                    <input type="text" class="form-control" id="add-bot-description" placeholder="请输入bot简介" v-model="botadd.description">
                                </div>
                                <div class="mb-3">
                                    <label for="add-bot-content" class="form-label">bot代码</label>
                                    <VAceEditor
                                        v-model:value="botadd.content"
                                        @init="editorInit"
                                        lang="java"
                                        theme="textmate"
                                        style="height: 300px" 
                                        :options="{ 
                                            fontSize: 20, 
                                            fontFamily: 'Consolas, Monaco, monospace',
                                            enableBasicAutocompletion: true,
                                            enableLiveAutocompletion: true,
                                            enableSnippets: true,}"
                                    />
                                </div>
                            </div>
                                <div class="modal-footer">
                                    <div class="errMsg">{{ botadd.errMsg }}</div>
                                    <button type="button" class="btn btn-primary" @click="add_bot">保存</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.createtime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right: 10px" 
                                        data-bs-toggle="modal" :data-bs-target="'#update-bot-modal' + bot.id" @click="update_bot(bot)
                                        ">
                                        修改
                                        </button>

                                        <button type="button" class="btn btn-danger" @click="remove_bot(bot)">删除</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'update-bot-modal' + bot.id" tabindex="-1">
                                            <div class="modal-dialog modal-dialog-centered modal-xl">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">修改bot</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                <div class="modal-body">
                                                    <div class="mb-3">
                                                        <label for="add-bot-title" class="form-label">名称</label>
                                                        <input type="text" class="form-control" id="add-bot-title" placeholder="请输入bot名称" v-model="bot.title">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="add-bot-description" class="form-label">简介</label>
                                                        <input type="text" class="form-control" id="add-bot-description" placeholder="请输入bot简介" v-model="bot.description">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="add-bot-content" class="form-label">bot代码</label>
                                                        <VAceEditor
                                                            v-model:value="bot.content"
                                                            @init="editorInit"
                                                            lang="java"
                                                            theme="textmate"
                                                            style="height: 300px"
                                                            :options="{ 
                                                                fontSize: 20, 
                                                                fontFamily: 'Consolas, Monaco, monospace',
                                                                enableBasicAutocompletion: true,
                                                                enableLiveAutocompletion: true,
                                                                enableSnippets: true}"
                                                        />
                                                    </div>
                                                </div>
                                                    <div class="modal-footer">
                                                        <div class="errMsg">{{ bot.errMsg }}</div>
                                                        <button type="button" class="btn btn-primary" @click="update_bot(bot)">修改</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="getList">取消</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import $ from 'jquery'
import { useStore } from 'vuex';
import { reactive, ref } from 'vue';
import {Modal} from 'bootstrap/dist/js/bootstrap'
import { VAceEditor } from 'vue3-ace-editor';
import 'ace-builds/src-noconflict/mode-java';
import 'ace-builds/src-noconflict/ext-language_tools';
import ace from 'ace-builds';

ace.config.set(
    "basePath", 
    "https://cdn.jsdelivr.net/npm/ace-builds@1.4.12/src-noconflict/"
)

ace.require("ace/ext/language_tools");

const store = useStore();
let bots = ref([]);

const botadd = reactive({
    title: "",
    description: "",
    content: "",
    errMsg: ""
});

const getList = () => {
    $.ajax({
        url: "http://127.0.0.1:3000/user/bot/getlist",
        type: "get",
        headers: {
            Authorization: "Bearer " + store.state.user.token
        },
        success(resp) {
            bots.value = resp;
        }
    });
}
getList();

const add_bot = () => {
    botadd.errMsg = "";

    $.ajax({
        url: "http://127.0.0.1:3000/user/bot/add",
        type: "post",
        data: {
            title: botadd.title,
            description: botadd.description,
            content: botadd.content
        },
        headers: {
            Authorization: "Bearer " + store.state.user.token
        },
        success(resp) {
            if (resp.error_message === "success") {
                Modal.getInstance("#addbot").hide();
                botadd.title = "";
                botadd.description = "";
                botadd.content = "";
                getList();
            } else {
                botadd.errMsg = resp.error_message;
            }
        },
        error() {
            botadd.errMsg = "服务器异常，添加失败!";
        }
    });
}

const remove_bot = (bot) => {
    botadd.errMsg = "";
    $.ajax({
        url: "http://127.0.0.1:3000/user/bot/remove",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token
        },
        data: {
            bot_id: bot.id
        },
        success(resp) {
            if (resp.error_message === "success") {
                getList();
            } else {
                botadd.errMsg = resp.error_message;
            }
        },
        error() {
            botadd.errMsg = "服务器异常，删除失败!"
        }
    });
}

const update_bot = (bot) => {
    botadd.errMsg = "";

    $.ajax({
        url: "http://127.0.0.1:3000/user/bot/update",
        type: "post",
        data: {
            bot_id: bot.id,
            title: bot.title,
            description: bot.description,
            content: bot.content
        },
        headers: {
            Authorization: "Bearer " + store.state.user.token
        },
        success(resp) {
            if (resp.error_message === "success") {
                Modal.getInstance("#update-bot-modal" + bot.id).hide();
                getList();
            } else {
                botadd.errMsg = resp.error_message;
            }
        },
        error() {
            botadd.errMsg = "服务器异常，修改失败!";
        }
    });
}



</script>

<style scoped>
div.card {
    margin-top: 20px;
}
div.errMsg {
    color: red;
}

:deep(.ace_editor) {
    border: 1px solid #ced4da;
    border-radius: 4px;
}
</style>