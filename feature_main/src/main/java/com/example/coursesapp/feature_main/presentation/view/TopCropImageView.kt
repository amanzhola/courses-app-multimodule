package com.example.coursesapp.feature_main.presentation.view

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class TopCropImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        scaleType = ScaleType.MATRIX
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        val changed = super.setFrame(l, t, r, b)
        updateMatrix()
        return changed
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateMatrix()
    }

    private fun updateMatrix() {
        val d = drawable ?: return
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        val drawableWidth = d.intrinsicWidth.toFloat()
        val drawableHeight = d.intrinsicHeight.toFloat()

        if (drawableWidth <= 0f || drawableHeight <= 0f || viewWidth <= 0f || viewHeight <= 0f) {
            return
        }

        val matrix = Matrix()
        val scale = maxOf(viewWidth / drawableWidth, viewHeight / drawableHeight)
        matrix.setScale(scale, scale)

        val scaledWidth = drawableWidth * scale
        val dx = (viewWidth - scaledWidth) / 2f
        matrix.postTranslate(dx, 0f)

        imageMatrix = matrix
    }
}