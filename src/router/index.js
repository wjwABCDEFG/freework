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
        // component: () => import('@/views/table/index'),
        meta: { title: '申请列表', icon: 'el-icon-coordinate' }
      },
    ]
  },

  {
    path: '/user',
    component: Layout,
    children: [
      {
        path: 'list',
        name: 'List',
        component: () => import('@/views/user/userlist'),
        meta: { title: '用户管理', icon: 'el-icon-user-solid' }
      },
    ]
  },

  {
    path: '/recruitment',
    component: Layout,
    name: 'Recruitment',
    meta: { title: '招聘信息管理', icon: 'el-icon-s-flag' },
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
    path: '/statistic',
    component: Layout,
    redirect: '/statistic/userdata',
    name: 'Statistic',
    meta: { title: '数据统计', icon: 'nested' },
    children: [
      {
        path: 'userdata',
        name: 'UserData',
        //component: () => import('@/views/table/index'),
        meta: { title: '用户统计', icon: 'el-icon-s-data' }
      },
      {
        path: 'companydata',
        name: 'CompanyData',
        //component: () => import('@/views/table/index'),
        meta: { title: '企业统计', icon: 'el-icon-s-data' }
      },
      {
        path: 'resumedata',
        name: 'ResumeData',
        //component: () => import('@/views/table/index'),
        meta: { title: '简历统计', icon: 'el-icon-s-data' }
      },
    ]
  },

  {
    path: '/system',
    component: Layout,
    redirect: '/system/log',
    name: 'System',
    meta: { title: '系统管理', icon: 'el-icon-stopwatch' },
    children: [
      {
        path: 'operationlog',
        name: 'OperationLog',
        //component: () => import('@/views/table/index'),
        meta: { title: '操作日志', icon: 'el-icon-s-platform' }
      },
      {
        path: 'script',
        name: 'Script',
        // component: () => import('@/views/table/index'),
        meta: { title: '脚本', icon: 'el-icon-s-promotion' }
      },
      {
        path: 'errlog',
        name: 'ErrLog',
        // component: () => import('@/views/table/index'),
        meta: { title: '错误日志', icon: 'el-icon-error' }
      },
    ]
  },

  {
    path: '/example',                                       // url地址栏中的路径
    component: Layout,                                      // 
    redirect: '/example/table',                             // 目的组件目录
    name: 'Example',                                        // name相当于唯一id，后期如果修改路由地址 也不会受影响
    meta: { title: 'Example', icon: 'el-icon-s-help' },    // 显示图标和标签名字
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/table/index'),
        meta: { title: 'Table', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: 'Tree', icon: 'tree' }
      }
    ]
  },

  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        name: 'Menu2',
        meta: { title: 'menu2' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
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
