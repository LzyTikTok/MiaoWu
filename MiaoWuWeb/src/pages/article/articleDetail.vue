<template>
    <el-container>
        <el-main>
            <el-row v-loading="loading">
                <el-col :span="24">
                    <div style="text-align: left;">
                        <el-button
                                type="text"
                                icon="el-icon-back"
                                @click="goBack"
                                style="padding-bottom: 0px; margin-bottom: 10px"
                        >返回
                        </el-button>
                    </div>
                </el-col>
                <el-col :span="24">
                    <div>
                        <div>
                            <h3 style="margin-top: 0px;margin-bottom: 0px">{{article.title}}</h3>
                            <span class="description"> 作者: {{author.name}}</span>
                        </div>
                        <el-button
                                type="primary"
                                @click="handleFollow"
                                style="margin-top: 10px"
                                v-if="userId !== article.authorId"
                        >关注
                        </el-button>
                        <div
                                style="width: 100%;margin-top: 5px;display: flex;justify-content: flex-end;align-items: center"
                        >
                            <!--          <div style="display: inline; color: #20a0ff;margin-left: 50px;margin-right:20px;font-size: 12px;">-->
                            <!--            {{article.nickname}}-->
                            <!--          </div>-->
                            <!--          todo 浏览次数-->
                            <!--          <span style="color: #20a0ff;margin-right:20px;font-size: 12px;">浏览 {{article.pageView==null?0:article.pageView}}</span>-->
                            <span
                                    style="color: #20a0ff;margin-right:20px;font-size: 12px;"
                            >{{new Date(article.lastUpdate).getTime() | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
                            <el-tag
                                    type="success"
                                    v-for="(item,index) in labels"
                                    :key="index"
                                    size="small"
                                    style="margin-left: 8px"
                            >{{item.name}}
                            </el-tag>
                            <span style="margin:0px 50px 0px 0px"></span>
                        </div>
                    </div>
                </el-col>
                <el-col>
                    <div id="content" style="text-align: left" v-html="article.source"></div>
                </el-col>
            </el-row>

            <h3>评论区</h3>
            <div id="comment-container" v-for="(comment,index) in comments" :key="index">
                <div style="text-align: left; margin-bottom: 5px">
                    {{comment.user.name}}
                    <span
                            style="color: #20a0ff;margin-right:20px;font-size: 12px;"
                    >{{new Date(comment.date).getTime() | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
                    <el-popconfirm
                            confirmButtonText='提交'
                            cancelButtonText='手滑了'
                            icon="el-icon-info"
                            iconColor="red"
                            title="确定取消关注吗？"
                            @onConfirm="postSave(comment.user.id)"
                    >
                        <el-button
                                type="primary"
                                @click="handleFollow"
                                style="margin-top: 10px"
                                v-if="userId !== comment.userId"
                        >取消关注
                        </el-button>
                    </el-popconfirm>
                    <el-button
                            type="primary"
                            @click="handleFollow"
                            style="margin-top: 10px"
                            v-if="userId !== comment.user.id"
                    >关注
                    </el-button>
                    <el-popconfirm
                            confirmButtonText='提交'
                            cancelButtonText='手滑了'
                            icon="el-icon-info"
                            iconColor="red"
                            title="确定提交救助动物吗？"
                            @onConfirm="postSave(comment.user.id)"
                    >
                        <el-button v-if="article.authorId !== comment.userId && article.authorId === userId"
                                   slot="reference" type="primary"
                                   style="">提交救助动物
                        </el-button>
                    </el-popconfirm>
                </div>

                <div style="text-align: left">{{comment.commentContent}}</div>
            </div>
            <el-input
                    type="textarea"
                    :rows="2"
                    placeholder="添加评论"
                    v-model="textarea"
                    style="margin-top: 10px"
                    v-loading="commentLoading"
            ></el-input>

            <el-button type="primary" @click="commitComment" style="margin-top: 10px">提交评论</el-button>
        </el-main>
        <el-footer style></el-footer>
    </el-container>
</template>

<script>
    import request from "@/utils/request";
    import settings from "@/settings";
    import {ResultCode} from "@/utils/ResultCode";
    import {parseTime} from "../../utils";
    import qs from "querystring";
    import {objectsContains} from '@/utils/miaoWuUtils';


    export default {
        name: "articleDetail",
        created() {
            this.getArticle();
        },
        data() {
            return {
                articleData: {
                    article: {
                        title: ""
                    },
                    comments: {},
                    labels: {},
                    author: {
                        name: ""
                    }
                },
                loading: "",
                commentLoading: "",
                textarea: ""
            };
        },
        computed: {
            userId() {
                return this.$store.getters.id;
            },
            userInfo() {
                return this.$store.state.user.userInfo
            },
            article() {
                return this.articleData.article
            },
            comments() {
                return this.articleData.comments
            },
            labels() {
                return this.articleData.labels
            },
            author() {
                return this.articleData.author
            }

            // article(){
            //   return this.article;
            // }
        },
        methods: {
            getArticle() {
                let self = this;
                let url = `${settings.apiUrl}articles/${self.$route.query.articleId}`;
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
                            this.$message.error("服务器出错喵~");
                            return;
                        }
                        if (result.code === ResultCode.SuccessCode) {
                            this.articleData = result.data;
                        } else if (result.code === ResultCode.ServerInnerError) {
                            this.$message.error("服务器出错喵~");
                        }
                    });
            },
            goBack() {
                this.$router.go(-1);
            },
            commitComment() {
                let self = this;
                self.commentLoading = true;
                let url = settings.apiUrl + "comments";
                let form = {
                    userId: self.$store.getters.id,
                    articleId: self.article.id,
                    comment: self.textarea
                };
                request
                    .request({
                        url,
                        method: "post",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        data: qs.stringify(form)
                    })
                    .then((res, error) => {
                        if (error) {
                            this.commentLoading = false;
                            return;
                        }
                        if (res.code === ResultCode.SuccessCode) {
                            self.commentLoading = false;
                            self.$message.success("发表评论成功喵~");
                            self.$router.go(0);
                        } else if (res.code === ResultCode.BadRequest) {
                            self.commentLoading = false;
                            self.$message.error(res.message);
                        } else if (res.code === ResultCode.ServerInnerError) {
                            self.commentLoading = false;
                            this.$message.error("服务器出错喵~");
                            reject();
                        }
                    });
            },
            handleFollow() {
                let self = this;
                let url = settings.apiUrl + "follows";
                //双重保险
                if (this.article.authorId === self.$store.getters.id) {
                    this.$message.error("不能关注自己~");
                }
                let form = {
                    userId: this.article.authorId,
                    fansId: self.$store.getters.id
                };
                request
                    .request({
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
                        } else if (res.code === ResultCode.BadRequest) {
                            self.$message.error(res.message);
                        } else if (res.code === ResultCode.ServerInnerError) {
                            this.$message.error("服务器出错喵~");
                            reject();
                        }
                    });
            },
            postSave(userId) {
                //todo 未测试
                let url = settings.apiUrl + "saves";
                let self = this;
                let form = {
                    'userId': userId,
                    'animalId': this.article.animalId,
                };
                request.request({
                    url,
                    method: "post",
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',

                    },
                    data: qs.stringify(form)
                }).then((res, error) => {
                    if (error) {
                        this.loading = false;
                        return;
                    }
                    if (res.code === ResultCode.SuccessCode) {
                        this.$message.success("提交救助成功啦喵");
                        this.loading = false;
                    } else if (res.code === ResultCode.BadRequest) {
                        self.$message.error(res.message);
                        this.loading = false;
                        reject();
                    } else if (res.code === ResultCode.ServerInnerError) {
                        this.$message.error('服务器出错喵~');
                        this.loading = false;
                    }
                })
            }
        },
        mounted() {
            // objectsContains(userInfo.follows);
        }
    };
</script>

<style scoped>
    #comment-container {
        padding: 10px;
        margin-bottom: 10px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }

    #content {
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    }
</style>
