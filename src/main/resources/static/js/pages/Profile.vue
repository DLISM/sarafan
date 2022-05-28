<template>
  <v-container>
    <v-layout justify-space-around >
      <v-flex xs6>
        <div class="title mb-3">Профиль пользователья</div>
        <v-layout row justify-space-between>
          <v-flex>
            <v-img :src="profile.userpic"></v-img>
          </v-flex>
          <v-flex>
            <v-layout column>
              <v-flex>{{ profile.name }}</v-flex>
              <v-flex>{{ profile.gender }}</v-flex>
              <v-flex>{{ profile.local }}</v-flex>
              <v-flex>{{ profile.lastVisit }}</v-flex>
              <v-flex>{{profile.subscriptions && profile.subscriptions.length}} subscriptions</v-flex>
              <v-flex>{{profile.subscribers && profile.subscribers.length}} subscribers</v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
        <v-btn
            v-if="!isMyProfile"
            @click="changeSubscription"
        >
          {{isISubscibed ? 'Unsubscribe' : 'Subscribe'}}
        </v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import profileApi from 'API/profile'
export default {
  name:'Profile',

  data(){
    return{
      profile:{}
    }
  },

  computed: {
    isMyProfile(){
      return !this.$route.params.id || this.$route.params.id === this.$store.state.profile.id
    },
    isISubscibed(){
      return this.profile.subscribers && this.profile.subscribers.find(
          subscription =>{
            return subscription.id === this.$store.state.profile.id
          }
      )
    }
  },
  watch:{
    '$route'(){
      this.updateProfile()
    }
  },
  methods:{
    async changeSubscription(){
      const data= await profileApi.changeSubscription(this.profile.id)
      this.profile = await data.json()
    },
    async updateProfile(){
      const id =this.$route.params.id || this.$store.state.profile.id
      const data = await profileApi.get(id)
      this.profile=await data.json()

      this.$forceUpdate()
    }
  },
  beforeMount() {
    this.updateProfile()
  }

}
</script>
<style scoped>
.v-image, img{
  width: 100px;
  height: 100px;
}

</style>