import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },

  {
    path: '/banner',
    component: Layout,
    name: 'Banner',
    meta: { title: 'banner管理', icon: 'el-icon-s-flag' },
    children: [
      {
        path: 'list',
        name: 'List',
        component: () => import('@/views/banner/bannerlist'),
        meta: { title: 'banner管理', icon: 'el-icon-s-flag' }
      },
    ]
  },

  {
    path: '/company',                                       // url地址栏中的路径
    component: Layout,                                      // 
    redirect: '/company/list',                              // 默认重定向到二级url地址栏中的路径
    name: 'Company',                                        // name相当于唯一id，后期如果修改路由地址 也不会受影响
    meta: { title: '企业管理', icon: 'el-icon-s-cooperation' },  // 显示图标和标签名字
    children: [
      {
        path: 'list',
        name: 'List',
        component: () => import('@/views/company/companylist'),
        meta: { title: '企业列表', icon: 'el-icon-office-building' }
      },
      {
        path: 'save',
        name: 'Save',
        component: () => import('@/views/company/savecompany'),
        meta: { title: '添加企业', icon: 'el-icon-circle-plus-outline' }
      },
      {
        path: 'edit/:id',
        name: 'CompanyEdit',
        component: () => import('@/views/company/savecompany'),
        meta: { title: '编辑企业', noCache: true},
        hidden: true
      },
      {
        path: 'application',
        name: 'Application',
        component: () => import('@/views/company/applycompany'),
        meta: { title: '申请列表', icon: 'el-icon-coordinate' }
      },
    ]
  },

  {
    path: '/user',
    component: Layout,
    redirect: '/user/list',
    name: 'User', 
    meta: { title: '用户管理', icon: 'el-icon-user-solid' },
    children: [
      {
        path: 'list',
        name: 'List',
        component: () => import('@/views/user/userlist'),
        meta: { title: '用户管理', icon: 'el-icon-user' }
      },
      {
        path: 'resumelist',
        name: 'Resumelist',
        component: () => import('@/views/resume/resumelist'),
        meta: { title: '简历列表', icon: 'el-icon-document-copy' }
      },
      {
        path: 'resume',
        name: 'Resume',
        component: () => import('@/views/resume/resume'),
        meta: { title: '简历页面', icon: 'el-icon-tickets' }
      },
      {
        path: 'resumeedit/:id',
        name: 'Resumeedit',
        component: () => import('@/views/resume/resume'),
        meta: { title: '简历页面', noCache: true},
        hidden: true
      },
    ]
  },

  {
    path: '/recruitment',
    component: Layout,
    name: 'Recruitment',
    meta: { title: '招聘信息管理', icon: 'el-icon-s-claim' },
    children: [
      {
        path: 'list',
        name: 'List',
        component: () => import('@/views/recruitment/recruitmentlist'),
        meta: { title: '招聘信息列表', icon: 'el-icon-tickets' }
      },
      {
        path: 'save',
        name: 'Save',
        component: () => import('@/views/recruitment/saverecruitment'),
        meta: { title: '发布招聘信息', icon: 'el-icon-edit' }
      },
    ]
  },

  {
    path: '/video',
    component: Layout,
    name: 'Video',
    meta: { title: '视频管理', icon: 'el-icon-s-flag' },
    children: [
      {
        path: 'list',
        name: 'List',
        component: () => import('@/views/video/videolist'),
        meta: { title: '视频管理', icon: 'el-icon-video-camera-solid' }
      },
    ]
  },

  // {
  //   path: '/statistic',
  //   component: Layout,
  //   redirect: '/statistic/userdata',
  //   name: 'Statistic',
  //   meta: { title: '数据统计', icon: 'nested' },
  //   children: [
  //     {
  //       path: 'userdata',
  //       name: 'UserData',
  //       //component: () => import('@/views/table/index'),
  //       meta: { title: '用户统计', icon: 'el-icon-s-data' }
  //     },
  //     {
  //       path: 'companydata',
  //       name: 'CompanyData',
  //       //component: () => import('@/views/table/index'),
  //       meta: { title: '企业统计', icon: 'el-icon-s-data' }
  //     },
  //     {
  //       path: 'resumedata',
  //       name: 'ResumeData',
  //       //component: () => import('@/views/table/index'),
  //       meta: { title: '简历统计', icon: 'el-icon-s-data' }
  //     },
  //   ]
  // },

  {
    path: '/system',
    component: Layout,
    redirect: '/system/operationlog',
    name: 'System',
    meta: { title: '系统管理', icon: 'el-icon-stopwatch' },
    children: [
      // {
      //   path: 'operationlog',
      //   name: 'OperationLog',
      //   component: () => import('@/views/chat/Home'),
      //   meta: { title: '操作日志', icon: 'el-icon-s-platform' }
      // },
      {
        path: 'serviceListener',
        name: 'ServiceListener',
        component: () => import('@/views/system/serviceListener'),
        meta: { title: '服务监控', icon: 'el-icon-s-platform' }
      },
      {
        path: 'errlog',
        name: 'ErrLog',
        component: () => import('@/views/system/errlog'),
        meta: { title: '错误日志', icon: 'el-icon-error' }
      },
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
