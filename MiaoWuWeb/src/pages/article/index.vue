<template>
    <div>
        <el-card :body-style="{ padding: '0px' }" style>
            <img src="@/static/defaultCat.jpg" class="image" @click.native="toArticleListPage"/>
            <div style="padding: 14px;">
                <el-link type="primary" @click="toArticleListPage">文章列表</el-link>
            </div>
        </el-card>
        <el-card :body-style="{ padding: '0px' }" style>
            <img src="@/static/defaultCat.jpg" class="image" @click.native="toUserCenterPage"/>
            <div style="padding: 14px;">
                <el-link type="primary" @click="toUserCenterPage">用户中心</el-link>
            </div>
        </el-card>
        <el-card :body-style="{ padding: '0px' }" style>
            <img src="@/static/defaultCat.jpg" class="image" @click.native="toFindAnimalPage"/>
            <div style="padding: 14px;">
                <el-link type="primary" @click="toFindAnimalPage">发现动物</el-link>
            </div>
        </el-card>
        <h5>欢迎来到喵呜</h5>
    </div>
</template>

<script>
    import request from "@/utils/request";
    import settings from "../../settings";
    import {ResultCode} from "@/utils/ResultCode";
    import qs from "querystring";

    export default {
        name: "index",
        data() {
            return {
                articles: [],
                restaurants: [],
                state: "",
                timeout: null
            };
        },
        created() {
            this.login();
        },
        methods: {
            login() {
                let self = this;
                let url =
                    settings.apiUrl +
                    "user/info" +
                    "?token=" +
                    window.localStorage.getItem("token");
                request
                    .request({
                        url,
                        method: "get",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        }
                    })
                    .then((result, error) => {
                        if (error) {
                            return;
                        }
                        if (result.code === ResultCode.SuccessCode) {
                            // debugger;
                            // self.$store.state.userInfo = JSON.parse(result.data);
                        } else if (result.code === ResultCode.ServerInnerError) {
                            this.$message.error("服务器出错喵~");
                        }
                    });
            },
            toArticleListPage() {
                this.$router.push({path: '/article/list'})
            },
            toUserCenterPage() {
                this.$router.push({path: '/profile/index'})
            },
            toFindAnimalPage() {
                this.$router.push({path: '/animal/editor'})
            }
        },
        mounted() {
        }
    };
</script>

<style scoped>
    .image {
        height: 200px;
    }
</style>
