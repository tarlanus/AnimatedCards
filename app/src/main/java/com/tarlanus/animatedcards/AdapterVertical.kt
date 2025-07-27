package com.tarlanus.animatedcards

import android.animation.Animator
import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.tarlanus.animatedcards.databinding.RowSimpleBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Collections

class AdapterVertical(val lowner: MainActivity, var list: List<Int>) :
    RecyclerView.Adapter<AdapterVertical.SimpleHolder>() {
    private var isAnimating = false

    private var act: MainActivity = lowner

    private var hashHolder: MutableMap<Int, SimpleHolder> = mutableMapOf()

    private var clickedPosition = -1
    inner class SimpleHolder(val binding: RowSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mainCard : CardView

        init {

            mainCard = binding.mainCard


        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
        val binding = RowSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleHolder(binding)
    }

    override fun getItemCount() = list.size

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

        holder.binding.mainCard

        holder.setIsRecyclable(false)
        val listmodel = list[position]


        holder.binding.tvRow.text = "Card + $listmodel"

        Log.e("getTranslationX", holder.itemView.translationX.toString())
        Log.e("getTranslationY", holder.itemView.translationY.toString())

        hashHolder[holder.absoluteAdapterPosition] = holder

        holder.binding.mainCard.setOnClickListener {

            if (!isAnimating) {
                val owner = lowner as LifecycleOwner

                owner.lifecycleScope.launch {
                    delay(200)

                    val positiontoChange = holder.absoluteAdapterPosition


                isAnimating = true

                act.setAnimator()






                    for (u in 0 until holder.absoluteAdapterPosition) {
                        val qolder = hashHolder[u]


                        val obj = ObjectAnimator.ofFloat(qolder?.itemView, "translationX", -800f)

                        obj.duration = 500

                        obj.start()


                        obj.addListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {

                            }

                            override fun onAnimationEnd(animation: Animator) {

                                qolder?.itemView?.visibility = View.INVISIBLE

                            }

                            override fun onAnimationCancel(animation: Animator) {
                            }

                            override fun onAnimationRepeat(animation: Animator) {
                            }

                        })


                    }

                    val next = holder.absoluteAdapterPosition + 1


                    for (u in next until itemCount) {
                        val qolder = hashHolder[u]

                        val obj = ObjectAnimator.ofFloat(qolder?.itemView, "translationX", +800f)

                        obj.duration = 500

                        obj.start()

                        obj.addListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {

                            }

                            override fun onAnimationEnd(animation: Animator) {

                                qolder?.itemView?.visibility = View.INVISIBLE

                            }

                            override fun onAnimationCancel(animation: Animator) {
                            }

                            override fun onAnimationRepeat(animation: Animator) {
                            }

                        })


                    }




                    delay(510)





                    Collections.swap(list, positiontoChange, 0)

                    notifyItemMoved(positiontoChange, 0)


                    act.handlePositions(positiontoChange)
                    isAnimating = false


                }

            }
        }

    }

    override fun onViewRecycled(holder: SimpleHolder) {
        hashHolder.remove(holder.absoluteAdapterPosition)

        super.onViewRecycled(holder)
    }

    fun setProgress(progressInt: Float, firstPos: Int, lastPos: Int) {


        Log.e("getPositionbyvisiblity", "first $firstPos")
        Log.e("getPositionbyvisiblity", "lastPos $lastPos")

        for (i in firstPos..lastPos) {

            val setPercentX = (i * 100) + 100
            val setPercentY = (i * 100)

            val getHolder = hashHolder[i]

            val transx = progressInt * setPercentX * 5

            val transy = 0 - (progressInt * setPercentY * 2)

            getHolder?.itemView?.translationX = transx
            getHolder?.itemView?.translationY = transy

            //  notifyItemChanged(i)

        }
    }

    fun setNewData(lists: MutableList<Int>) {
        this.list = lists
        notifyDataSetChanged()


        for (u in 0 until itemCount) {
            val getHolder = hashHolder[u]

            getHolder?.itemView?.translationX = 0f
            getHolder?.itemView?.translationY = 0f
            getHolder?.itemView?.visibility = View.VISIBLE


        }

    }
}