package com.hariofspades.dagger2advanced.adapter;

import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/** Created by 4ino.dev 29.01.2019
* Contains static method for adding RecyclerView item hover effect
* requires API version >= 21
* */

class RecyclerViewHoverEffect {
    private static final long ANIM_DURATION = 300;
    private static final float TRANSLATION_Z = 80;
    private static final float SCALE = 1.025F;
    private static final float ALPHA = 0.7F;

    /** Just call this in your onBindViewHolder method like this:
     * RecyclerViewHoverEffect.addHoverEffect(holder.itemView, holder.foregroundView)
     * @param view - recyclerView item root CardView
     * @param foreground - any view that should change it's opacity while hovering (may be null)
     * */
    static void addHoverEffect(final View view, @Nullable final View foreground) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        fingerOn(view, foreground);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        fingerOff(view, foreground);
                        break;
                    case MotionEvent.ACTION_UP:
                        fingerOff(view, foreground);
                        //perform click if necessary
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /* animates hovering */
    private static void fingerOn(View view, View foreground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.animate()
                    .translationZ(TRANSLATION_Z)
                    .scaleX(SCALE)
                    .scaleY(SCALE)
                    .setDuration(ANIM_DURATION)
                    .start();
            if (foreground != null) {
                foreground.animate()
                        .alpha(ALPHA)
                        .setDuration(ANIM_DURATION)
                        .start();
            }
        }
    }

    /* animates going back to normal */
    private static void fingerOff(View view, View foreground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.animate()
                    .translationZ(0)
                    .setDuration(ANIM_DURATION)
                    .scaleX(1.0F)
                    .scaleY(1.0F)
                    .start();
            if (foreground != null) {
                foreground.animate()
                        .alpha(1.0F)
                        .setDuration(ANIM_DURATION)
                        .start();
            }
        }
    }
}
