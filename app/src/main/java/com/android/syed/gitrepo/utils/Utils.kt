package com.android.syed.gitrepo.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Toast
import androidx.core.content.ContextCompat


fun getCircleStrokeBg(context: Context, solidColor: Int, strokeColor: Int): GradientDrawable {
    val gd = GradientDrawable()
    gd.shape = GradientDrawable.OVAL
    gd.setColor(Color.parseColor("#D81B60"))
    gd.setStroke(getOneDpAsPixel(context), Color.parseColor("#00574B"))

    return gd
}

fun getOneDpAsPixel(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (scale + 0.5f).toInt()
}

fun getRectStrokeTopRightTopLeftRoundBg(
    context: Context,
    solidColor: Int,
    strokeColor: Int,
    cornerRadius: Int
): GradientDrawable {
    //The corners are ordered top-left, top-right, bottom-right, bottom-left.
    val gd = GradientDrawable()
    val rad = (cornerRadius * getOneDpAsPixel(context)).toFloat()
    gd.shape = GradientDrawable.RECTANGLE
    gd.setColor(ContextCompat.getColor(context, solidColor))
    gd.setStroke(getOneDpAsPixel(context), ContextCompat.getColor(context, strokeColor))
    gd.cornerRadii = floatArrayOf(rad, rad, rad, rad, 0f, 0f, 0f, 0f)
    return gd
}

fun showToast(context: Context, msg: String) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 500)
    toast.show()
}