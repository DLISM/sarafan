<template>
  <v-app>
    <v-toolbar app>
     <v-toolbar-title>Sarafan</v-toolbar-title>

      <v-btn flat v-if="profile" :desapled="$route.path==='/'" @click="showMessage">
        Messages list
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn flat v-if="profile"
             :desapled="$route.path==='/user'" @click="showProfile">
        {{profile.name}}
      </v-btn>

      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>

    </v-toolbar>

    <v-content>
      <router-view>

      </router-view>
    </v-content>

  </v-app>
</template>

<script>
import {mapState, mapMutations} from 'vuex'
import {addHandler} from 'util/ws'

export default {
  computed:mapState(['profile']),
  methods:{

    ...mapMutations([
      'addMessageMutation',
      'updateMessageMutation',
      'removeMessageMutation',
      'addCommentMutation'
      ]),

    showMessage(){
      this.$router.push('/')
    },
    showProfile(){
      this.$router.push('/user')
    }
  },
  created() {
    addHandler(data=>{
      if(data.objectType==='MESSAGE') {
        switch (data.eventType){
          case 'CREATE':
            this.addMessageMutation(data.body)
            break
          case 'UPDATE':
            this.updateMessageMutation(data.body)
            break
          case 'REMOVE':
            this.removeMessageMutation(data.body)
            break
          default:
            console.log('Looks like the event type is unknown "${date.eventType}"')
        }
      }else if(data.objectType==='COMMENT') {
        switch (data.eventType){
          case 'CREATE':
            this.addCommentMutation(data.body)
            break
          default:
            console.log('Looks like the event type is unknown "${date.eventType}"')
        }
      }else {
        console.log('Looks like the event type is unknown "${date.objectType}"')
      }
    })
  },
  beforeMount() {
    if(!this.profile){
      this.$router.replace('/auth')
    }
  }
}
</script>
<style>

</style>