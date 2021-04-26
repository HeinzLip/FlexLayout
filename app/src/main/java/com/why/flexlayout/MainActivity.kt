package com.why.flexlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.why.flexlayout.weiget.FlexLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData(findViewById<FlexLayout>(R.id.flexlayout))
    }


    fun initData(root: ViewGroup){
        for(index in  0..15){
            val item = LayoutInflater.from(this).inflate(R.layout.flexlayout_item, null, false)
            item.findViewById<TextView>(R.id.text).text = (5..15).randomStr()
//            val textView = TextView(context)
//            textView.text = (5..15).randomStr()
            root.addView(item)
        }

    }
}