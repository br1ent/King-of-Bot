import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkIndexView.vue'
import RankListView from '../views/ranklist/RankListView.vue'
import RecordView from '../views/record/RecordView.vue'
import NotFound from '../views/error/NotFound.vue'
import UserBotView from '../views/user/bot/UserBotView.vue'
import LoginView from '../views/user/account/LoginView.vue'
import RegisterView from '../views/user/account/RegisterView.vue'
import store from '../store/index'
import RecordContentView from '../views/record/RecordContentView.vue'
import ModeSelectView from '../views/pk/ModeSelectView.vue'


const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/modeselect',
  },
  {
    path: '/home',
    name: 'home_redirect',
    
  }
  {
    path: '/pk',
    name: 'pk',
    component: PkIndexView,
    meta: {
      requestAuth: true,
      title: "开始对战"
    }
  },
  {
    path: '/modeselect',
    name: 'modeselect',
    component: ModeSelectView,
    meta: {
      requestAuth: true,
      title: "模式选择"
    }
  },
  {
    path: '/ranklist',
    name: 'ranklist',
    component: RankListView,
    meta: {
      requestAuth: true,
      title: "排行榜"
    }
  },
  {
    path: '/user/account/login',
    name: 'login',
    component: LoginView,
    meta: {
      requestAuth: false,
      title: "登录",
    }
  },
  {
    path: '/user/account/register',
    name: 'register',
    component: RegisterView,
    meta: {
      requestAuth: false,
      title: "注册",
    }
  },
  {
    path: '/record',
    name: 'record',
    component: RecordView,
    meta: {
      requestAuth: true,
      title: "录像列表"
    }
  },
  {
    path: '/record/:recordId',
    name: 'record_content',
    component: RecordContentView,
    meta: {
      requestAuth: true,
      title: "回放"
    }
  },
  {
    path: '/user/bot',
    name: 'user_bot',
    component: UserBotView,
    meta: {
      requestAuth: true,
      title: "我的bot"
    }
  },
  
  {
    path: '/404',
    name: '404',
    component: NotFound,
    meta: {
      requestAuth: false,
      title: "页面丢失"
    }
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/404'
  }
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({name: "login"});
  } else {
    next();
  }

  if (to.meta.title) {
    document.title = to.meta.title;
  } else {
    document.title = "kob"
  }
})

export default router