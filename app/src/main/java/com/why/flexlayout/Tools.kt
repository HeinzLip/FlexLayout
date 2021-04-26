package com.why.flexlayout

import android.content.Context
import java.lang.StringBuilder

/**
 * @ClassName: Tools
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 4/26/21 4:00 PM
 */

fun Int.dp2px(context: Context): Int{
    val scale = context.resources.displayMetrics.density;
    return (this * scale + 0.5f).toInt();
}

fun IntRange.randomStr(): String{
    val strs = mutableListOf<Char>().apply { "123456789zxcvbnmasdfghjklqwertyuiop".forEach { this.add(it) } }
    val str = StringBuilder().apply {  (0..this@randomStr.random()).onEach { append(strs.random()) } }
    return str.toString()
}