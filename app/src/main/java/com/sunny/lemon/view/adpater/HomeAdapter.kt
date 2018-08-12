package com.sunny.lemon.view.adpater

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sunny.lemon.R
import com.sunny.lemon.bean.HomeBean
import com.sunny.lemon.view.activity.PlayerActivity
import com.sunny.lemon.view.vh.HomeViewHolder

class HomeAdapter(var mContext: Context) : RecyclerView.Adapter<HomeViewHolder>() {


    fun setDatas(mDatas: ArrayList<HomeBean>) {
        this.mDatas.addAll(mDatas)
        notifyDataSetChanged()
    }

    fun resetData() {
        this.mDatas.clear()
        notifyDataSetChanged()
    }


    fun setData(mData: HomeBean) {
        this.mDatas.add(mData)
        notifyDataSetChanged()
    }


    var mDatas: ArrayList<HomeBean> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_home_layout, null)
        view.setOnClickListener{

            if (it.tag != null) {
                val b = it.tag as HomeBean
                val i = Intent();
                i.setClass(mContext,PlayerActivity::class.java)
                i.putExtra("link",b.mLink)
                mContext.startActivity(i)
            }

        }

        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val b = mDatas.get(position)
        Glide.with(mContext).asDrawable().load(b.mPicture).into(holder.mGifImageView)
        holder.mTimer.text = b.mTimer
        holder.mDescription.text = b.mDescription
        holder.mRootView.tag = b
    }

}