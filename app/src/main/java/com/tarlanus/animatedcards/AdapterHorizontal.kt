package com.tarlanus.animatedcards

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tarlanus.animatedcards.databinding.RowHorizontalBinding


class AdapterHorizontal(lowner: MainActivity, var list : List<Int> ) :
    RecyclerView.Adapter<AdapterHorizontal.SimpleHolder>() {
    private  var originalWidth: Int = 0
    private  var originalHeight: Int  = 0
    private var lowner: MainActivity = lowner
    private var selectedItem = 0
    private var hashHolder: MutableMap<Int, AdapterHorizontal.SimpleHolder> = mutableMapOf()



    inner class SimpleHolder(val binding: RowHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
        val binding =
            RowHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleHolder(binding)
    }

    override fun getItemCount() = list.size
    override fun getItemViewType(position: Int): Int {
        if (position == selectedItem) return 0 else return 1

    }
    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
        val context = holder.itemView.context
        when (holder.absoluteAdapterPosition) {
            1 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card1))
            2 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card2))
            3 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card3))
            4 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card4))
            5 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card5))
            6 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card6))
            7 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card7))
            8 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card8))
            9 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card9))
            10 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card10))
            11 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card11))
            12 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card12))
            13 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card13))
            14 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card14))
            15 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card15))
            16 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card16))
            17 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card17))
            18 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card18))
            19 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card19))
            20 -> holder.binding.mainCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card20))
        }

        val mainCard = holder.binding.mainCard
        val layoutParams = mainCard.layoutParams


        if (originalWidth == 0) {
            originalWidth = layoutParams.width
            originalHeight = layoutParams.height
        }


        if (selectedItem == holder.absoluteAdapterPosition) {
            layoutParams.width = originalWidth + 40
            layoutParams.height = originalHeight + 40
        } else {
            layoutParams.width = originalWidth
            layoutParams.height = originalHeight
        }

        mainCard.layoutParams = layoutParams

        val listmodel = list[position]
        holder.binding.tvRow.text = "Card + $listmodel"

        Log.e("getTranslationX", holder.itemView.translationX.toString())
        Log.e("getTranslationY", holder.itemView.translationY.toString())

        hashHolder[holder.absoluteAdapterPosition] = holder

        holder.binding.mainCard.setOnClickListener {


            lowner.setScrolpos(selectedItem)
        }
    }
    override fun onViewRecycled(holder: SimpleHolder) {
        hashHolder.remove(holder.absoluteAdapterPosition)
        super.onViewRecycled(holder)
    }
    fun setNewData(lists: MutableList<Int>) {
        this.list = lists
        notifyDataSetChanged()

    }

    fun setCurrent(absoluteAdapterPosition: Int) {
        try {
            selectedItem = absoluteAdapterPosition
            notifyItemChanged(absoluteAdapterPosition)

        } catch (e : Exception) {
            Log.e("getException", e.localizedMessage.toString())

        }

    }


}