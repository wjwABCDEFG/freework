module.exports = {
  plugins: [
    { src: '~/plugins/nuxt-swiper-plugin.js', ssr: false },
    { src: '~plugins/element-ui', ssr: true }
  ],
  // modules: [
  //   '/axios'
  // ],

  css: [
    'swiper/dist/css/swiper.css',
    { src: 'element-ui/lib/theme-chalk/index.css' }
  ],
  /*
  ** Headers of the page
  */
  head: {
    title: 'freework招聘平台',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: 'freework招聘平台展示页' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },
  /*
  ** Customize the progress bar color
  */
  loading: { color: '#3B8070' },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** Run ESLint on save
    */
    extend (config, { isDev, isClient }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    }
  }
}

