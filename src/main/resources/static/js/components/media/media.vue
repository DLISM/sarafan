<template>
  <v-card>
    <v-flex v-if="type==='href'" xs12 sm6 offset-sm3>
      <v-img v-if="message.linkCover" :src="message.linkCover" aspect-ratio="2.75">
      </v-img>

      <v-card-title>
        <div>
          <h3><a :href="message.link">{{message.linkTitle || message.link}}</a></h3>
        </div>
        <div v-if="message.linkDescription">{{message.linkDescription}}</div>
      </v-card-title>
    </v-flex>

    <v-flex v-if="type==='image'" xs12 sm6 offset-sm3>
      <a :href="message.link">
        {{message.link}}
        <v-img v-if="message.linkCover" :src="message.linkCover" aspect-ratio="2.75"></v-img>
      </a>
    </v-flex>

    <v-flex v-if="type==='youtube'" xs12 sm6 offset-sm3>
      <youtube :src="message.link"></youtube>
    </v-flex>
  </v-card>
</template>

<script>
import Youtube from "./Youtube.vue"

export default {
  name: "media",
  props:['message'],
  components:{Youtube},
  data(){
    return{
      type:'href'
    }
  },
  beforeMount() {
    if(this.message.link.indexOf('youtu')>-1){
      this.type='youtube'
    }else if(this.message.link.match(/\.(jpeg|jpg|gif|png)$/)!==null){
      this.type='image'
    }else {
      this.type='href'
    }
  }
}
</script>

<style scoped>

</style>