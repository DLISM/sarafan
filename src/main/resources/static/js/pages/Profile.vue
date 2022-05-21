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
              <v-flex>{{ profile.email }}</v-flex>
              <v-flex>{{ profile.gender }}</v-flex>
              <v-flex>{{ profile.local }}</v-flex>
              <v-flex>{{ profile.lastVisit }}</v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import {mapState} from "vuex";

export default {
  name:'Profile',
  computed: mapState(['profile'])
}
</script>
<style scoped>
.v-image, img{
  width: 100px;
  height: 100px;
}

</style>