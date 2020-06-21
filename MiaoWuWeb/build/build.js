//bulid.js是webpack的打包文件，通过配置package.json中的script来执行脚本。命令号npm run build即运行node build/build.js
'use strict'
//检查node+npm的版本，引用./check-versions.js文件
require('./check-versions')()

//process.env是一个包含用户环境信息的对象 NODE_ENV是用户自定义的变量，用来判断是开发环境还是生产环境
process.env.NODE_ENV = 'production'

//导入ora模块 实现loading效果
const ora = require('ora')
//导入rimraf模块 以包的形式包装rm -rf命令，用来删除文件和文件夹的，不管文件是否为空
const rm = require('rimraf')
//导入node的path模块
const path = require('path')
//导入chalk模块 用来改变文字颜色
const chalk = require('chalk')
//导入webpack模块
const webpack = require('webpack')
//导入config/index.js
const config = require('../config')
//导入webpack.prod.conf
const webpackConfig = require('./webpack.prod.conf')
//实现loading的模块
const spinner = ora('building for production...')
//开始动画
spinner.start()

//rm方法删除static文件夹
//path.join是将路径片段以'\'连接成新的路径，任何一个路径片段有错误就会报错
rm(path.join(config.build.assetsRoot,config.build.assetsSubDirectory),err => {
    if(err) throw err
    //构建webpack
    webpack(webpackConfig,(err,stats) => {
        //停止动画
        spinner.stop()
        if(err) throw err
        //process.stdout.write是标准输出，相当于console.log
        process.stdout.write(stats.toString({
            //增加控制卡颜色开关，即显示不同颜色的字体
            colors:true,
            //是否增加内置模块信息
            modules:false,
            children:false,
            //允许较少的输出
            chunks:false,
            //不将内置模块的信息加到包信息
            chunkModules:false

        }) + '\n\n')

        if(stats.hasErrors()){
            console.log(chalk.red(' Build complete.\n'))
            //执行失败
            process.exit(1)
        }
        //编译退出
        console.log(chalk.cyan(' Build failed with errors.\n'))
        console.log(chalk.yellow(
            ' Tip:built files are meant to be served over an HTTP server.\n' +
            ' Opening index.html over file:// won\'t work.\n'
        ))
    })
})

