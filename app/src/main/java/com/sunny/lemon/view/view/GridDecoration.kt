package com.sunny.lemon.view.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridDecoration(var size:Int) : RecyclerView.ItemDecoration(){



    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {

        outRect!!.bottom = size
        outRect!!.top = size
        outRect!!.left = size
        outRect!!.right = size

        super.getItemOffsets(outRect, view, parent, state)
    }





}
