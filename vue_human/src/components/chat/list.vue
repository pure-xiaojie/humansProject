<template>
	<!--好友列表-->
  <div id="list">
  	<ul style="padding-left: 0px">
  		<li v-for="user in users" :class="{ active: currentSession?user.username === currentSession.username:false }"
					v-on:click="changeCurrentSession(user)">
  			<img class="avatar" :src="user.userface" :alt="user.name">
  			<p class="name">{{user.name}}</p>
				<el-badge :is-dot="isDot[curUser.username+'#'+user.username]"></el-badge>
  		</li>
  	</ul>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'list',
  data () {
    return {
      curUser:JSON.parse(window.sessionStorage.getItem("user"))
    }
  },
  computed: mapState([
  'users',
  'currentSession',
	'isDot'
	]),
  methods:{
  	changeCurrentSession:function (user) {
  		this.$store.commit('changeCurrentSession',user)
  	}
  }
}
</script>

<style lang="scss" scoped>
#list {
	li {
		padding: 0px 15px;
		border-bottom: 1px solid #b9c0ca;
		cursor: pointer;
		list-style: none;
		&:hover {
			background-color: rgba(255, 255, 255, 0.03);
		}
	}
  li.active {/*注意这个是.不是冒号:*/
			background-color: rgba(255, 255, 255, 0.3);
	}
	.avatar {
		border-radius: 2px;
		width: 30px;
		height: 30px;
		vertical-align: middle;
	}
	.name {
		display: inline-block;
		margin-left: 15px;
	}
}
</style>
