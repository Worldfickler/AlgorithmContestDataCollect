import { createApp } from 'vue'
import App from './App.vue'
import VForm3 from 'vform3-builds'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/router'
import JsonViewer from 'vue-json-viewer'
const app = createApp(App);
app.use(ElementPlus)
app.use(VForm3)
app.use(router)
app.use(JsonViewer)
app.mount('#app')
