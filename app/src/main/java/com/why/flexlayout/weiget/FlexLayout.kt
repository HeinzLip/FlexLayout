package com.why.flexlayout.weiget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.why.flexlayout.dp2px

/**
 * @ClassName: FlexLayout
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/24/21 9:55 AM
 */
class FlexLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val WIDTH_SPACE = 10.dp2px(context)
    private val HEIGHT_SPACE = 15.dp2px(context)

    private val allLineViews = mutableListOf<MutableList<View>>()
    private val listLineHeight = mutableListOf<Int>()

    private fun clearList(){
        allLineViews.clear()
        listLineHeight.clear()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        clearList() //防止多次调用measure导致脏数据

        var lineWidth = 0
        var lineHeight = 0

        var maxLineWidth = 0
        var maxLineHeight = 0
        var lineViews = mutableListOf<View>()
        val selWidth = MeasureSpec.getSize(widthMeasureSpec)
        val selHeight = MeasureSpec.getSize(heightMeasureSpec)

        for(i in 0 until childCount){
            val child = getChildAt(i)
            val childParams = child.layoutParams
            val childNeedWidth = childParams.width
            val childNeedHeight = childParams.height

            val childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childNeedWidth)
            val childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childNeedHeight)

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)

            if(lineWidth + child.measuredWidth + WIDTH_SPACE > (selWidth - paddingLeft - paddingRight)){
                allLineViews.add(lineViews)
                listLineHeight.add(lineHeight)
                maxLineWidth = maxLineWidth.coerceAtLeast(lineWidth)
                maxLineHeight += lineHeight

                lineWidth = 0
                lineHeight = 0
                lineViews = mutableListOf()
            }
            lineWidth += child.measuredWidth + WIDTH_SPACE
            lineHeight = lineHeight.coerceAtLeast(child.measuredHeight + HEIGHT_SPACE)
            lineViews.add(child)

            if(i == childCount - 1){
                allLineViews.add(lineViews)
                listLineHeight.add(lineHeight)
                maxLineWidth = maxLineWidth.coerceAtLeast(lineWidth)
                maxLineHeight += lineHeight
            }
        }

        val parentWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val parentHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        val realWidth = if(parentWidthMode != MeasureSpec.EXACTLY) maxLineWidth else selWidth
        val realHeight = if(parentHeightMode != MeasureSpec.EXACTLY) maxLineHeight else selHeight

        setMeasuredDimension(realWidth, realHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var pt = 0
        var pl = 0

        for(i in 0 until allLineViews.size){
            val lineViews = allLineViews[i]
            for(j in 0 until lineViews.size){
                val child = lineViews[j]
                val ct = pt
                val cl = pl
                val cr = pl + child.measuredWidth
                val cb = pt + child.measuredHeight

                child.layout(cl, ct, cr, cb)

                pl = cr + WIDTH_SPACE
            }

            pt += listLineHeight[i]
            pl = left
        }
    }


}