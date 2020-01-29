package com.hariofspades.dagger2advanced.adapter

import android.os.Build
import android.view.MotionEvent
import android.view.View

class RecyclerViewHoverEffectKt {
    companion object {
        private const val ANIM_DURATION: Long = 300
        private const val TRANSLATION_Z = 80f
        private const val SCALE = 1.025f
        private const val ALPHA = 0.7f

        /** Just call this in your onBindViewHolder method like this:
         * RecyclerViewHoverEffect.addHoverEffect(holder.itemView, holder.foregroundView)
         * @param view - recyclerView item root CardView
         * @param foreground - any view that should change it's opacity while hovering (may be null)
         */
        public fun addHoverEffect(view: View, foreground: View?) {
            view.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> fingerOn(view, foreground)
                    MotionEvent.ACTION_CANCEL -> fingerOff(view, foreground)
                    MotionEvent.ACTION_UP -> {
                        fingerOff(view, foreground)
                        //perform click if necessary
                        v.performClick()
                    }
                    else -> { 
                    }
                }
                true
            }
        }

        /* animates hovering */
        private fun fingerOn(view: View, foreground: View?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.animate()
                        .translationZ(TRANSLATION_Z)
                        .scaleX(SCALE)
                        .scaleY(SCALE)
                        .setDuration(ANIM_DURATION)
                        .start()
                foreground?.animate()?.alpha(ALPHA)?.setDuration(ANIM_DURATION)?.start()
            }
        }

        /* animates going back to normal */
        private fun fingerOff(view: View, foreground: View?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.animate()
                        .translationZ(0f)
                        .setDuration(ANIM_DURATION)
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .start()
                foreground?.animate()?.alpha(1.0f)?.setDuration(ANIM_DURATION)?.start()
            }
        }
    }
}