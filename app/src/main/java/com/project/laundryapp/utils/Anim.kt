package com.project.laundryapp.utils

import android.view.View

object Anim {
    fun crossFade(contentView: View) {
        val shortAnimationDuration = 500L
        contentView.apply {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null)
        }
    }
}