package cn.yangchengyu.libnavannotation

/**
 * Desc  : Nav Activity 注解
 * Author: Chengyu Yang
 * Date  : 2020/3/6
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ActivityDestination(
    val pageUrl: String,
    val needLogin: Boolean = false,
    val asStarter: Boolean = false
)