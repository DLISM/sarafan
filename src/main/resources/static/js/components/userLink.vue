<template>
  <router-link
      v-if="user"
      :to="`/user/${userId}`"
      class="px-3"
  >
    <v-avatar
        :size="`${size || 36}px`"
        v-if="user && user.userpic"
    >
      <v-img :src="user.userpic" :alt="user.name"></v-img>
    </v-avatar>

    <v-avatar
        v-else
        :size="`${size || 36}px`"
        color="indigo">
      <v-icon dark>account_circle</v-icon>
    </v-avatar>

    <span class="pl-3">
            {{userName}}
    </span>
  </router-link>
</template>

<script>
export default {
  name: "userLink",
  props:['user', 'size'],
  computed:{
    userName(){ return this.user? this.user.name: 'unknow' },
    userId() {
      return this.user ? String(this.user.id) : 'null'
    }
  }
}
</script>

<style scoped>

</style>