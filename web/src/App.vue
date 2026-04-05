<template>
  <div>
    <NavBar></NavBar>
    <router-view></router-view>
  </div>
</template>


<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap'
import NavBar from './components/NavBar.vue';
</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>