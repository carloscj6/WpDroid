package com.revosleap.wpdroid.ui.dialogs

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.revosleap.wpdroid.R

abstract class FullScreenBottomSheet : BottomSheetDialogFragment() {

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        var bottomSheet: View? = null

        if (dialog != null) {
            bottomSheet = dialog.findViewById(R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        val view = view
        val finalBottomSheet = bottomSheet
        assert(view != null)
        view?.post {
            val parent = view.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = (behavior as BottomSheetBehavior<*>?)!!
            bottomSheetBehavior.peekHeight = view.measuredHeight
            assert(finalBottomSheet != null)
            (finalBottomSheet?.parent as View).setBackgroundColor(Color.TRANSPARENT)
        }
    }

}