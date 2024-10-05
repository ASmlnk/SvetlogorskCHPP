package com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import androidx.transition.Visibility
import com.daimajia.swipe.SwipeLayout
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.PermissionRequestWork
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.databinding.ItemCalendarNoteRequestWorkBinding
import com.example.svetlogorskchpp.databinding.ItemSwipeCalendarNoteBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class NoteAdapter(
    private val onClickDelete: (noteMy: Note) -> Unit,
) :
    ListAdapter<Note, RecyclerView.ViewHolder>(ItemNoteCallback()) {

    private val expandedPositions = SparseBooleanArray()

       // private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> NoteHolder.inflateFrom(parent)
            else -> NoteRequestWorkHolder.inflateFrom(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Note.NoteMy -> (holder as NoteHolder).bind(item, onClickDelete)
            is Note.NoteRequestWork -> {
               // val isExpanded = position == expandedPosition
                (holder as NoteRequestWorkHolder).bind(item, onClickDelete,expandedPositions, this)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isNotEmpty()) {
            if (holder is NoteRequestWorkHolder) {
               // val isExpanded = position == expandedPosition
                (holder as NoteRequestWorkHolder).bind((getItem(position) as Note.NoteRequestWork),onClickDelete, expandedPositions, this)
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

   /* private fun toggleItem (position: Int) {
        if (position == expandedPosition) {
            expandedPosition = -1
        } else {
            expandedPosition = position
        }
        notifyItemChanged(position, Unit)
        notifyItemChanged(expandedPosition, Unit)
    }*/

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Note.NoteMy -> 0
            is Note.NoteRequestWork -> 1
        }
    }

    class NoteHolder(val binding: ItemSwipeCalendarNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(item: Note.NoteMy, onClickDelete: (noteMy: Note.NoteMy) -> Unit) {
            binding.apply {
                swipeItemNote.apply {
                    showMode = SwipeLayout.ShowMode.LayDown
                    addDrag(SwipeLayout.DragEdge.Right, binding.swipeRightNote)
                    addSwipeListener(object : SwipeLayout.SwipeListener {
                        override fun onStartOpen(layout: SwipeLayout?) {}
                        override fun onOpen(layout: SwipeLayout?) {}
                        override fun onStartClose(layout: SwipeLayout?) {}
                        override fun onClose(layout: SwipeLayout?) {}
                        override fun onUpdate(
                            layout: SwipeLayout?,
                            leftOffset: Int,
                            topOffset: Int,
                        ) {
                        }

                        override fun onHandRelease(
                            layout: SwipeLayout?,
                            xvel: Float,
                            yvel: Float,
                        ) {
                        }
                    })
                }
                deleteNote.setOnClickListener {
                    onClickDelete(item)
                }
                tvContet.text = item.content
                tvTime.text = SimpleDateFormat("HH:mm").format(item.dateNotes.time)
                tvTime.isGone = !item.isTimeNotes
            }
        }

        companion object {
            fun inflateFrom(parentContext: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflate = LayoutInflater.from(parentContext.context)
                val binding =
                    ItemSwipeCalendarNoteBinding.inflate(
                        layoutInflate,
                        parentContext,
                        false
                    )
                return NoteHolder(binding)
            }
        }
    }

    class NoteRequestWorkHolder(
        private val binding: ItemCalendarNoteRequestWorkBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        /*fun updateExpansion(isExpanded: Boolean) {
            binding.apply {
                if (isExpanded) {
                    layoutButton.visibility = View.VISIBLE
                    ObjectAnimator.ofFloat(layoutButton, "alpha", 0f, 1f).apply {
                        duration = 300
                        start()
                    }
                } else {
                    ObjectAnimator.ofFloat(layoutButton, "alpha", 1f, 0f).apply {
                        duration = 300
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                layoutButton.visibility = View.GONE
                            }
                        })
                        start()
                    }
                }
            }
        }*/

        fun bind(item: Note.NoteRequestWork, onClickDelete: (noteMy: Note) -> Unit,expandedPositions:  SparseBooleanArray, adapter: NoteAdapter) {
            val isExpanded = expandedPositions[absoluteAdapterPosition, false]

            binding.apply {

             //  layoutButton.visibility = if (isExpanded) View.VISIBLE else View.GONE


                /*layoutButton.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                val targetHeight = if (isExpanded) layoutButton.measuredHeight else 0
                layoutButton.layoutParams.height = targetHeight
                layoutButton.requestLayout()*/


                tvNumberRequest.text = item.numberRequestWork
                tvReason.text = item.reason + "\n" + item.additionally
                tvAccession.text = item.accession
                tvDateRequestWork.text =
                    calendarToString(item.dateOpen) + "\n" + calendarToString(item.dateClose)
                tvExtend.text = item.contentExtend
                tvExtend.isGone = !item.isExtend
                tvPermission.text = item.permission.entity

                itemView.setOnClickListener {
                    val shouldExpand = !expandedPositions[position, false]
                    if(shouldExpand) {

                        expandedPositions.put(position, true)
                        layoutButton.expand()
                    } else {
                        expandedPositions.delete(position)
                        layoutButton.collapse()
                    }

                    /*if (isExpanded) {
                        expandedPositions.delete(position)
                    } else {
                        expandedPositions.put(position, true)
                    }*/
                    /*if (isExpanded) {
                        toggleHeight(layoutButton,layoutButton.height, 0)
                        layoutButton.visibility = View.GONE

                    } else {
                        layoutButton.visibility = View.VISIBLE
                        layoutButton.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        toggleHeight(layoutButton,0,layoutButton.measuredHeight)
                    }*/




                    //TransitionManager.beginDelayedTransition(itemView.parent as ViewGroup)
                    //toggleHeight(isExpanded)
                    adapter.notifyItemChanged(position, true) // Используем payload*/
                    //adapter.toggleItem(position)

                }

                bDelete.setOnClickListener {
                    onClickDelete(item)
                    expandedPositions.delete(position)
                }


                when (item.permission) {
                    PermissionRequestWork.OTHER -> tvPermission.isGone = true
                    PermissionRequestWork.DISPATCHER -> {
                        tvNumberRequest.setTextColor(itemView.resources.getColor(R.color.text_input_assembly))
                        tvPermission.background =
                            itemView.resources.getDrawable(R.drawable.background_request_work_text_permission_d)
                    }

                    PermissionRequestWork.CHIEF_ENGINEER -> {
                        tvNumberRequest.setTextColor(itemView.resources.getColor(R.color.color_request_work_calendar))
                        tvPermission.background =
                            itemView.resources.getDrawable(R.drawable.background_request_work_text_permission_c)
                    }
                }
            }
        }

        fun toggleHeight(view: View, startHeight: Int, endHeight: Int) {

            val valueAnimation = ValueAnimator.ofInt(startHeight, endHeight)
            valueAnimation.addUpdateListener { animator ->
                val layoutParams = view.layoutParams
                layoutParams.height = animator.animatedValue as Int
                view.layoutParams = layoutParams
            }
            valueAnimation.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {

                }
            })


            valueAnimation.duration = 1500
            valueAnimation.start()
        }

       /* fun updateExpansion(isExpanded: Boolean) {
            if (isExpanded) {
                slideUp(binding.layoutButton)
            } else {
                slideDown(binding.layoutButton)
            }
        }

        private fun slideUp(view: View) {
            view.visibility = View.VISIBLE
            view.alpha = 0f
            view.translationY = -view.height.toFloat()

            val animatorSet = AnimatorSet().apply {
                playTogether(
                    ObjectAnimator.ofFloat(view, "translationY", 0f),
                    ObjectAnimator.ofFloat(view, "alpha", 1f)
                )
                duration = 200
                start()
            }
        }

        private fun slideDown(view: View) {
            val animatorSet = AnimatorSet().apply {
                playTogether(
                    ObjectAnimator.ofFloat(view, "translationY", view.height.toFloat()),
                    ObjectAnimator.ofFloat(view, "alpha", 0f)
                )
                duration = 200
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                    }
                })
                start()
            }
        }*/


        private fun calendarToString(calendar: Calendar): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy  HH:mm")
            return sdf.format(calendar.time)
        }


        companion object {
            fun inflateFrom(
                parentContext: ViewGroup
            ): RecyclerView.ViewHolder {
                val layoutInflate = LayoutInflater.from(parentContext.context)
                val binding =
                    ItemCalendarNoteRequestWorkBinding.inflate(
                        layoutInflate,
                        parentContext,
                        false
                    )
                return NoteRequestWorkHolder(binding)
            }
        }
    }
}

@SuppressLint("ObjectAnimatorBinding")
fun View.expand() {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = measuredHeight

    layoutParams.height = 0
    visibility = View.VISIBLE

    val animator = ObjectAnimator.ofInt(this,"heigh", 0, targetHeight)
    val animatorSet = AnimatorSet()
    animatorSet.play(animator).apply {
        animator.duration = 300
        animator.start()
    }


    /*val animator = ValueAnimator.ofInt(0, targetHeight).apply {
        addUpdateListener { valueAnimator ->
            layoutParams.height = valueAnimator.animatedValue as Int
            requestLayout()
        }
        duration = 1300
    }
    animator.start()
   visibility = View.VISIBLE*/
}

fun View.collapse() {
    val initialHeight = measuredHeight

    val animator = ValueAnimator.ofInt(initialHeight, 0).apply {
        addUpdateListener { valueAnimator ->
            layoutParams.height = valueAnimator.animatedValue as Int
            requestLayout()
        }
        duration = 300
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
        })
    }
    animator.start()
}
