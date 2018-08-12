package com.sunny.lemon.view.vh

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sunny.lemon.R
import pl.droidsonroids.gif.GifImageView

class HomeViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var mRootView = v

    var mGifImageView = v.findViewById<GifImageView>(R.id.home_gif_image)

    var mTimer = v.findViewById<TextView>(R.id.tv_timer)

    var mDescription = v.findViewById<TextView>(R.id.tv_description)

}