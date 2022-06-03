import Vue from 'vue'

const profile=Vue.resource('/profile{/id}')

export default {
    get: id=> profile.get({id}),
    changeSubscription: channelID => Vue.http.post(`/profile/change-subscription/${channelID}`),
    subscriberList:channelId => Vue.http.get(`/profile/get-subscribers/${channelId}`),
    changeSubscriptionStatus: subscriberId => Vue.http.post(`/profile/change-status/${subscriberId}`)
}