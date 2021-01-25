package com.sms.diaganal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

internal class GridViewAdapter internal constructor(
    context: Context,
    private val resource: Int,
    private val itemList: MutableList<String>?,
    private val itemImages: MutableList<Int>
) : ArrayAdapter<GridViewAdapter.ItemHolder>(context, resource){

    override fun getCount(): Int {
        //return the count of number of items
        return if (this.itemList != null) this.itemList.size else 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        //ItemHolder holds the intialized items required for the layout inflated
        val holder: ItemHolder
        if (convertView == null) {
            //inflate the custom view layout
            convertView = LayoutInflater.from(context).inflate(resource, null)
            holder = ItemHolder()
            holder.name = convertView!!.findViewById(R.id.textView)
            holder.icon = convertView.findViewById(R.id.icon)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemHolder
        }

        //removes the extra height automatically added by grid view
        holder.icon!!.adjustViewBounds = true
        //setting the respective texts and images according to the sent lists
        holder.name!!.text = this.itemList!![position]
        holder.icon!!.setImageResource(this.itemImages[position])

        return convertView

    }
    class ItemHolder {
        var name: TextView? = null
        var icon: ImageView? = null
    }
}

