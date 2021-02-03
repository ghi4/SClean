package com.project.laundryapp.utils

import android.view.View

object Anim {
    fun crossFade(contentView: View) {
        val shortAnimationDuration = 500L
        contentView.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null)
        }
    }
}