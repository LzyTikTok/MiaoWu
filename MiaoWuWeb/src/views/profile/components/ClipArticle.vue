<template>
  <div class="post">
    <div class="articles" v-for="(artilce, i) in articles">
      <div class="user-block">
        <img class="img-circle"
             :src="'https://wpimg.wallstcn.com/57ed425a-c71e-4201-9428-68760c0537c4.jpg'+avatarPrefix">
        <span class="username text-muted">Iron Man</span>
        <span class="description">Shared publicly - 7:30 PM today</span>
      </div>
      <p>
        Lorem ipsum represents a long-held tradition for designers,
        typographers and the like. Some people hate it and argue for
        its demise, but others ignore the hate as they create awesome
        tools to help create filler text for everyone from bacon lovers
        to Charlie Sheen fans.
      </p>
      <ul class="list-inline">
        <li>
          <span class="link-black text-sm">
            <i class="el-icon-share"/>
            Share
          </span>
        </li>
        <li>
          <span class="link-black text-sm">
            <svg-icon icon-class="like"/>
            Like
          </span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  export default {
    name: "ClipArticle",
    props: {
      articles: {
        type: Object,
        default: () => {
          return {
            name: '',
            title: "",
            id: 2,
            idCode: "",
            password: "",
            phone: "",
          }
        }
      }
    }
  }
</script>

<style scoped>

</style>
