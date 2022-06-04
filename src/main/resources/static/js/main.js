import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'API/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import { connect } from './util/ws'
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from '@sentry/vue'
import { BrowserTracing } from '@sentry/tracing'

Sentry.init({
    Vue,
    dsn: "https://142877bb0fc34e8b9e87f34e98b916c7@o1275240.ingest.sentry.io/6470203",
    integrations: [
        new BrowserTracing({
            routingInstrumentation: Sentry.vueRouterInstrumentation(router),
            tracingOrigins: ["localhost", "my-site-url.com", /^\//],
        }),
    ],

    tracesSampleRate: 1.0,
});

Sentry.configureScope(scope => scope.setUser({
    id:profile && profile.id,
    username: profile && profile.name

    })

)

if (profile) {
    connect()
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    store:store,
    router:router,
    render: a => a(App)
})