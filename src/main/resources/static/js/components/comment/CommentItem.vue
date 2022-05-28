<template>
  <v-list-tile>
    <v-list-tile-avatar>
      <user-link :user="comment.author"></user-link>
    </v-list-tile-avatar>
    <v-list-tile-content>
      <v-list-tile-title>{{comment.text}}</v-list-tile-title>
      <v-list-tile-sub-title>

      </v-list-tile-sub-title>
    </v-list-tile-content>
  </v-list-tile>
</template>

<script>
import UserLink from "components/userLink.vue";
export default {
  name: "CommentItem",
  components: {UserLink},
  props:['comment']
}
</script>

<style scoped>

</style>