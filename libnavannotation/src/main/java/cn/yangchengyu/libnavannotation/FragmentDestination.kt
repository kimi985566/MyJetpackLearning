package cn.yangchengyu.libnavannotation

/**
 * Desc  : nav Fragment 注解
 * Author: Chengyu Yang
 * Date  : 2020/3/6
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class FragmentDestination(
    val pageUrl: String,
    val needLogin: Boolean = false,
    val asStarter: Boolean = false
)