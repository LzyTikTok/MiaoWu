<template>
    <el-container v-loading="loading" class="post-article">
        <el-header class="header">
            <el-select v-model="article.animalId" placeholder="请选择要申请救助的动物" v-show="isSelectAnimalShow"
                       style="width: 200px;">
                <el-option
                        v-for="item in foundAnimals"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                </el-option>
            </el-select>
            <el-input v-model="article.title" placeholder="请输入标题..." style="width: 400px;margin-left: 10px"></el-input>
            <el-tag
                    :key="tag"
                    v-for="tag in article.dynamicTags"
                    closable
                    :disable-transitions="false"
                    @close="handleClose(tag)" style="margin-left: 10px" v-show="isTagShow">
                {{tag}}
            </el-tag>
            <el-input
                    class="input-new-tag"
                    v-if="tagInputVisible"
                    v-model="tagValue"
                    ref="saveTagInput"
                    size="small"
                    @keyup.enter.native="handleInputConfirm"
                    @blur="handleInputConfirm">
            </el-input>
            <el-button v-else class="button-new-tag" type="primary" size="small" @click="showInput" v-show="isTagShow">
                +Tag
            </el-button>
        </el-header>
        <el-main class="main">
            <div id="editor">
                <mavon-editor style="height: 100%;width: 100%;" ref=md @imgAdd="imgAdd"
                              @imgDel="imgDel" v-model="article.mdContent"></mavon-editor>
            </div>
            <div style="display: flex;align-items: center;margin-top: 15px;justify-content: flex-end">
                <el-button @click="cancelEdit" v-if="from!=undefined">放弃修改</el-button>
                <template v-if="from==undefined || from=='draft'">
                    <!--          <el-button @click="submit(0)">保存到草稿箱</el-button>-->
                    <el-button type="primary" @click="submit(1)">发表文章</el-button>
                </template>
                <template v-else="from==post">
                    <el-button type="primary" @click="submit(1)">保存修改</el-button>
                </template>
            </div>
        </el-main>
    </el-container>
</template>
<script>
    // import {uploadFileRequest} from '../utils/api'
    // Local Registration
    import {mavonEditor} from 'mavon-editor'
    import request from '@/utils/request'
    import settings from '@/settings'
    import {ResultCode} from '@/utils/ResultCode'
    import qs from 'querystring'
    import 'mavon-editor/dist/css/index.css'
    // import {isNotNullORBlank} from '../utils/utils'

    // 可以通过 mavonEditor.markdownIt 获取解析器markdown-it对象


    export default {
        mounted: function () {
            this.getFoundAnimals();
            var from = this.$route.query.from;
            this.from = from;
            var self = this;
            this.getArticle();
        },
        components: {
            mavonEditor
        },
        data() {
            return {
                foundAnimals: [],
                tagInputVisible: false,
                tagValue: '',
                loading: false,
                from: '',
                article: {
                    id: '-1',
                    dynamicTags: [],
                    title: '',
                    mdContent: '',
                    cid: ''
                },
                isSelectAnimalShow: true,
                isTagShow: true,
                isUpdateMode: false,
            }
        },
        methods: {
            getArticle() {
                let self = this;
                if (self.$route.query.articleId) {
                    request.get(settings.apiUrl + 'articles/getById?id=' + self.$route.query.articleId).then(res => {
                        if(res.data.authorId !== self.$store.getters.id){
                            self.$message.error("没有权限修改哦");
                            self.$router.go(-1);
                            return ;
                        }
                        self.article = res.data;
                        self.article.mdContent = res.data.content;
                        self.isSelectAnimalShow = false;
                        self.isTagShow = false;
                        self.isUpdateMode = true;
                    })
                }
            },
            cancelEdit() {
                this.$router.go(-1)
            },
            imgAdd(pos, $file) {
                var _this = this;
                // 第一步.将图片上传到服务器.
                var formdata = new FormData();
                formdata.append('image', $file);
                uploadFileRequest("/article/uploadimg", formdata).then(resp => {
                    var json = resp.data;
                    if (json.status == 'success') {
//            _this.$refs.md.$imgUpdateByUrl(pos, json.msg)
                        _this.$refs.md.$imglst2Url([[pos, json.msg]])
                    } else {
                        _this.$message({type: json.status, message: json.msg});
                    }
                });
            },
            imgDel(pos) {

            },
            handleClose(tag) {
                this.article.dynamicTags.splice(this.article.dynamicTags.indexOf(tag), 1);
            },
            showInput() {
                this.tagInputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },
            handleInputConfirm() {
                let tagValue = this.tagValue;
                if (tagValue) {
                    this.article.dynamicTags.push(tagValue);
                }
                this.tagInputVisible = false;
                this.tagValue = '';
            },
            back() {
                this.$router.go(-1);
            },
            submit() {
                let self = this;
                self.loading = true;
                if (self.isUpdateMode) {
                    self.updateArticle();
                    return;
                }
                self.submitArticle().then((res, rej) => {
                    let articleId = res;
                    self.submitTags(articleId).then((res, rej) => {
                        this.$message.success("发布文章成功啦喵");
                    });
                });
            },
            updateArticle() {
                let self = this;
                let form = {
                    'id': self.article.id,
                    'title': self.article.title,
                    'content': self.article.mdContent,
                    'source': self.$refs.md.d_render,
                    'authorId': self.$store.getters.id,
                    'animalId': self.article.animalId,
                    'type': 1
                }
                let config = {
                    method: 'PUT',
                    url: settings.apiUrl + 'articles',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    data: qs.stringify(form)
                }
                request.request(config).then((res) => {
                    if (res.code === ResultCode.SuccessCode) {
                        self.$message.success("修改文章成功啦喵");
                        self.$router.go(-1);
                    } else if (res.code === ResultCode.BadRequest) {
                        self.$message.success("出错啦~");
                        self.$router.go(-1);
                    }
                    self.loading = false;
                })
            },
            submitArticle() {
                let self = this;
                if (self.article.animalId === null) {
                    self.$message.error("还没有选择动物哦~");
                    return;
                }
                let url = settings.apiUrl + "article/addArticle";
                let form = {
                    'title': self.article.title,
                    'content': self.article.mdContent,
                    'source': self.$refs.md.d_render,
                    'authorId': self.$store.getters.id,
                    'animalId': self.article.animalId,
                    'type': 1
                };
                return new Promise((result, reject) => {
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
                            result(res.data);
                        } else if (res.code === ResultCode.BadRequest) {
                            self.$message.error(res.message);
                        } else if (res.code === ResultCode.ServerInnerError) {
                            this.$message.error('服务器出错喵~');
                            reject();
                        }
                    })
                })
            },
            submitTags(articleId) {
                let url = settings.apiUrl + "labels";
                let self = this;
                let form = {
                    'articleId': articleId,
                    'labelNames': self.article.dynamicTags
                };
                return new Promise((resolve, reject) => {
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
                            resolve();
                            this.loading = false;
                        } else if (res.code === ResultCode.BadRequest) {
                            self.$message.error(res.message);
                            this.loading = false;
                            reject();
                        } else if (res.code === ResultCode.ServerInnerError) {
                            this.$message.error('服务器出错喵~');
                            reject();
                            this.loading = false;
                        }
                    })
                })
            },
            getFoundAnimals() {
                let url = settings.apiUrl + "animals?userId=" + this.$store.getters.id;
                let self = this;
                request.request({
                    url,
                    method: "get",
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',

                    },
                }).then((res, error) => {
                    if (error) {
                        this.loading = false;
                        return;
                    }
                    if (res.code === ResultCode.SuccessCode) {
                        this.foundAnimals = res.data;
                        this.loading = false;
                    } else if (res.code === ResultCode.BadRequest) {
                        self.$message.error("还没有发现动物哦~")
                        // self.$message.error(res.message);
                        this.loading = false;
                        reject();
                    } else if (res.code === ResultCode.ServerInnerError) {
                        this.$message.error('服务器出错喵~');
                        this.loading = false;
                    }
                })
            }
        },
    }
</script>
<style>
    .post-article > .main > #editor {
        width: 100%;
        height: 450px;
        text-align: left;
    }

    .post-article > .header {
        background-color: #ececec;
        margin-top: 10px;
        padding-left: 5px;
        display: flex;
        justify-content: flex-start;
    }

    .post-article > .main {
        /*justify-content: flex-start;*/
        display: flex;
        flex-direction: column;
        padding-left: 5px;
        background-color: #ececec;
        padding-top: 0px;
    }

    .post-article > .header > .el-tag + .el-tag {
        margin-left: 10px;
    }

    .post-article > .header > .button-new-tag {
        margin-left: 10px;
        height: 32px;
        line-height: 30px;
        padding-top: 0;
        padding-bottom: 0;
    }

    .post-article > .header > .input-new-tag {
        width: 90px;
        margin-left: 10px;
        vertical-align: bottom;
    }

    .post-article {
    }
</style>
