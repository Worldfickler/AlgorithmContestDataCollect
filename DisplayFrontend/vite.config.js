import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import path from 'path'
export default defineConfig({
  plugins: [
    vue(), 
    createSvgIconsPlugin({
    // Specify the icon folder to be cached
    iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
    // Specify symbolId format
    symbolId: 'icon-[dir]-[name]',
  }),],
  server: {
    proxy: {
      '/ACDC/api': {
        target: 'http://127.0.0.1:8081/',
        rewrite: path => path.replace(/^\/ACDC/, ""),
        changeOrigin: true
      }
    }
  },
  build: {
    assetsDir: "ACDC/assets/",
  }
})
