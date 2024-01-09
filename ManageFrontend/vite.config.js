import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  server:{
    proxy:{
      '/ACDC/manage/api':{
        target: 'http://127.0.0.1:8041/',
        rewrite:path => path.replace(/^\/ACDC\/manage/,""),
        changeOrigin:true
      }
    }
  },
  build:{
    assetsDir:"ACDC/manage/assets"
  }
})
