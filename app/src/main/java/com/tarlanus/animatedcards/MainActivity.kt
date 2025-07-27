package com.tarlanus.animatedcards


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tarlanus.animatedcards.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView2: RecyclerView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterVertical: AdapterVertical
    private lateinit var adapterHorizontal: AdapterHorizontal

    private var shouldChange = true
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    private var horizontalPosition = 0
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView = binding.myrec
        recyclerView2 = binding.myrec2
        recyclerView.itemAnimator = null
        recyclerView2.itemAnimator = null
        val list : MutableList<Int> = mutableListOf(
        )

        for (i in 0..100) {
            list.add(i)
        }
        adapterVertical = AdapterVertical(this, list)
        adapterHorizontal = AdapterHorizontal(this, list)
        adapterHorizontal.setCurrent(0)
        recyclerView.adapter = adapterVertical
        recyclerView2.adapter = adapterHorizontal
        behavior = BottomSheetBehavior.from(binding.idSetBottom)
        behavior.peekHeight = 300
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        Handler().postDelayed({
            setXy(true)

        }, 100)


        binding.bottomBut.setOnClickListener {
            if (behavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            }
        }



        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                Log.e("getSlideOFsset", "state $newState")

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                    if (shouldChange == true) {
                        Handler().postDelayed({

                            val pos =
                                (recyclerView2.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                            if (pos != null) {
                                (recyclerView2.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                                    pos,
                                    0
                                )
                            }
                            setXy(true)


                        }, 100)
                    }




                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.bottomBut.rotation = 180f
                    if (shouldChange == true) {

                        Handler().postDelayed({
                            setXy(true)


                        }, 100)
                    }

                } else {
                    binding.bottomBut.rotation = 0f

                }



            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {


                Log.e("getSlideOFsset", "slideOffset $slideOffset")
                val firstPos =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                val lastPos =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()


                if (shouldChange == true) {
                    lifecycleScope.launch {
                        delay(50)







                        if (firstPos != null && lastPos != null) {
                            adapterVertical.setProgress(slideOffset, firstPos, lastPos)
                        }
                        if (slideOffset >= 0.7f) {


                            binding.myrec.visibility = View.INVISIBLE
                            binding.myrec2.visibility = View.VISIBLE




                        } else {

                            binding.myrec.visibility = View.VISIBLE
                            binding.myrec2.visibility = View.INVISIBLE


                        }


                    }

                }


            }

        })

        recyclerView2.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = (recyclerView2.layoutManager as? LinearLayoutManager)
                val pos =
                    lm?.findFirstVisibleItemPosition()
                Log.e("getScrollPosition", "$pos")
                if (pos != null)
                    adapterHorizontal.setCurrent(pos)
            }
        })


    }

    private fun setXy(b: Boolean) {



        for (u in 0 until adapterVertical.itemCount) {
            val holder = recyclerView.findViewHolderForAdapterPosition(u)
            val getHolder = holder
            getHolder?.itemView?.translationX = 0f
            getHolder?.itemView?.translationY = 0f
            getHolder?.itemView?.visibility = View.VISIBLE


        }
        if ( b == true) {
            //  adapterVertical.notifyItemChanged(u)
            val list : MutableList<Int> = mutableListOf(
            )

            for (i in 0..100) {

                list.add(i)
            }

            adapterVertical.setNewData(list)

        }
    }

    fun setScrolpos(position: Int) {
        val offset = (200 * 0.30)
        val lm = (recyclerView2.layoutManager as? LinearLayoutManager)

        lifecycleScope.launch(Dispatchers.Main) {

            recyclerView2.smoothScrollToPosition(position + 1)
            delay(100)
            recyclerView2.scrollBy(220, 0)


        }

    }

    fun setAnimator() {
        recyclerView.itemAnimator = DefaultItemAnimator()
    }


    fun setCollapsing() {

        binding.bottomBut.performClick()

    }

    fun handlePositions(absoluteAdapterPosition: Int) {

        horizontalPosition = absoluteAdapterPosition
        adapterHorizontal.setCurrent(absoluteAdapterPosition)
        adapterHorizontal.notifyItemChanged(absoluteAdapterPosition)
        Log.e("getChangeAdapterPosition", absoluteAdapterPosition.toString())
        lifecycleScope.launch {


            recyclerView.scrollToPosition(0)






            shouldChange = false



            delay(300)
            (recyclerView2.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(absoluteAdapterPosition, 0)


            setCollapsing()




            recyclerView2.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE




            delay(300)



            val list : MutableList<Int> = mutableListOf(
            )

            for (i in 0..100) {

                list.add(i)
            }


            adapterVertical.setNewData(list)


            recyclerView.scrollToPosition(absoluteAdapterPosition)


            shouldChange = true
            recyclerView.itemAnimator = null



        }

    }


}