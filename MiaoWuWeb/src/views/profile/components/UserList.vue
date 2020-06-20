<template>
    <el-row>
        <div v-if="!users">
            <span>没有相关信息哦~</span>
        </div>
        <el-col :span="8" v-for="(user, index) in users" :key="index" :offset="2">
            <el-card :body-style="{ padding: '0px' }" style>
                <img class="avator" src="@/static/profile.jpg"/>
                <el-button type="primary" v-if="follows" @click="addOrDelFollow(user.id)">取消关注</el-button>
                <div style="padding: 14px;">
                    <p type="primary" @click="toArticleDetail(article.id)">{{user.name}}</p>
                    <div class="bottom clearfix" style="font-size: 11px"></div>
                </div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
    import request from "@/utils/request";
    import settings from "@/settings";
    import {ResultCode} from "@/utils/ResultCode";
    import qs from "querystring";
    import {objectsContains} from '@/utils/miaoWuUtils';

    export default {
        name: "userList",
        props: {
            fans: {
                type: Array,
                deafult: () => []
            },
            follows: {
                type: Array,
                deafult: () => []
            }
        },
        watch: {
            fans(val) {
                this.users = val;
            },
            follows(val) {
                this.users = val;
            }
        },
        data() {
            return {
                users: []
            };
        },
        methods: {
            addOrDelFollow(userId) {
                let self = this;
                let url = settings.apiUrl + "follows";
                let form = {
                    userId: userId,
                    fansId: self.$store.getters.id
                };
                request.request({
                    url,
                    method: "post",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    data: qs.stringify(form)
                })
                    .then((res, error) => {
                        if (error) {
                            return;
                        }
                        if (res.code === ResultCode.SuccessCode) {
                            self.$message.success("关注成功~");
                        } else if (res.code === ResultCode.CancelSuccessCode) {
                            self.$message.success("取消关注成功~");
                            self.$router.go(-1);
                        } else if (res.code === ResultCode.BadRequest) {
                            self.$message.error(res.message);
                        } else if (res.code === ResultCode.ServerInnerError) {
                            this.$message.error("服务器出错喵~");
                            reject();
                        }
                    });
            }
        }
    };
</script>

<style scoped>
    .avator {
        width: 50px;
        border-radius: 50%;
    }
</style>
