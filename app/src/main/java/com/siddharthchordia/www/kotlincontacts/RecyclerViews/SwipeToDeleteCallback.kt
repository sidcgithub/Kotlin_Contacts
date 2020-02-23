package com.siddharthchordia.www.kotlincontacts.RecyclerViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeToDeleteCallback(context: Context,mAdapter: ContactListAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    final val mAdapter = mAdapter;
    final val context = context
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //To change body of created functions use File | Settings | File Templates.
        mAdapter.itemDelete(viewHolder.adapterPosition,context)
    }
}