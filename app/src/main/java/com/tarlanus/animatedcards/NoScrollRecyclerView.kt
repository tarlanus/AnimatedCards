package com.tarlanus.animatedcards

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class NoScrollRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    override fun canScrollVertically(direction: Int): Boolean {
        return false
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }
}
