<!--
 * @FileDescription: 
 * @Author: ccy
 * @Date: 2023-03-16 16:43:16
 * @LastEditors: ccy
 * @LastEditTime: 2023-03-17 11:42:59
 * @FilePath: \zr_szhpt_web\src\App.vue
 * @Description: 
-->
<template>
  <router-view />
</template>

<script setup lang="ts">
import useSettingsStore from '@/store/modules/settings.ts'
import { handleThemeStyle } from '@/utils/theme.ts'

onMounted(() => {
  nextTick(() => {
    // 初始化主题样式
    handleThemeStyle(useSettingsStore().theme)
  })
})
</script>
