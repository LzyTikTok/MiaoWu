<template>
  <el-container v-loading="loading" class="post-article">
    <el-header class="header">
<!--      换成动物-->
      <el-select v-model="article.cid" placeholder="请选择要申请救助的动物" style="width: 200px;">
        <el-option
          v-for="item in categories"
          :key="item.id"
          :label="item.cateName"
          :value="item.id">
        </el-option>
      </el-select>
      <el-input v-model="article.title" placeholder="请输入标题..." style="width: 400px;margin-left: 10px"></el-input>
      <el-tag
        :key="tag"
        v-for="tag in article.dynamicTags"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)" style="margin-left: 10px">
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
      <el-button v-else class="button-new-tag" type="primary" size="small" @click="showInput">+Tag</el-button>
    </el-header>
    <el-main class="main">
      <div id="editor">
        <mavon-editor style="height: 100%;width: 100%;" ref=md @imgAdd="imgAdd"
                      @imgDel="imgDel" v-model="article.mdContent"></mavon-editor>
      </div>
      <div style="display: flex;align-items: center;margin-top: 15px;justify-content: flex-end">
        <el-button @click="cancelEdit" v-if="from!=undefined">放弃修改</el-button>
        <template v-if="from==undefined || from=='draft'">
          <el-button @click="submit(0)">保存到草稿箱</el-button>
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
      // this.getCategories();
      var from = this.$route.query.from;
      this.from = from;
      var _this = this;
      // if (from != null && from != '' && from != undefined) {
      //   var id = this.$route.query.id;
      //   this.id = id;
      //   this.loading = true;
      //   getRequest("/article/" + id).then(resp=> {
      //     _this.loading = false;
      //     if (resp.status == 200) {
      //       _this.article = resp.data;
      //       var tags = resp.data.tags;
      //       _this.article.dynamicTags = []
      //       for (var i = 0; i < tags.length; i++) {
      //         _this.article.dynamicTags.push(tags[i].tagName)
      //       }
      //     } else {
      //       _this.$message({type: 'error', message: '页面加载失败!'})
      //     }
      //   }, resp=> {
      //     _this.loading = false;
      //     _this.$message({type: 'error', message: '页面加载失败!'})
      //   })
      // }
    },
    components: {
      mavonEditor
    },
    methods: {
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
      // getCategories(){
      //   let _this = this;
      //   getRequest("/admin/category/all").then(resp=> {
      //     _this.categories = resp.data;
      //   });
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
        self.submitArticle().then((res,rej) => {
          let articleId = res;
          self.submitTags(articleId).then((res, rej)=>{
            this.$message.success("发布文章成功啦喵");
          });
        });
      },
      submitArticle(){
        //todo form
        let self = this;
        let url = settings.apiUrl + "article/addArticle";
        let form = {
          'title': self.article.title,
          'content': self.article.mdContent,
          'source': self.$refs.md.d_render,
          'authorId': self.$store.state.userInfo.id,
          'type': 1
        };
        return new Promise((result,reject)=>{
          request.request({
              url,
              method: "post",
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
              },
              data: qs.stringify(form)
            }).then((res, error) => {
              if (error) {
                this.loading = false;
                return;
              }
              if (res.code === ResultCode.SuccessCode) {
                 result(res.data);
              } else if(res.code === ResultCode.BadRequest){
                 self.$message.error(res.message);
              }
              else if (res.code === ResultCode.ServerInnerError) {
                this.$message.error('服务器出错喵~');
                reject();
              }
          })
        })
      },
      submitTags(articleId){
        //todo 提交tag form
        let url = settings.apiUrl + "Label/addLabel";
        let self = this;
        let form = {
          'articleId': articleId,
          'labelNames': self.article.dynamicTags
        };
        return new Promise((resolve,reject)=>{
          request.request({
            url,
            method: "post",
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: qs.stringify(form)
          }).then((res, error) => {
            if (error) {a
              this.loading = false;
              return;
            }
            if (res.code === ResultCode.SuccessCode) {
              resolve();
              this.loading = false;
            } else if(res.code === ResultCode.BadRequest){
              self.$message.error(res.message);
              this.loading = false;
              reject();
            }
            else if (res.code === ResultCode.ServerInnerError) {
              this.$message.error('服务器出错喵~');
              reject();
              this.loading = false;
            }
          })
        })
      }
    },
    data() {
      return {
        categories: [],
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
        }
      }
    }
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
