package com.freeagent.testapp.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.freeagent.testapp.R

/**
 * Generates row with textviews of equal size
 */
class TableGenerator(private val context: Context) {

    /**
     * Generate textview rows
     *
     * @param textViewList list of value names to be shows in row
     * @return populate values in textviews and create equal sized rows
     */
    fun generateRow(textViewList:List<String>): TableRow {
        val row = TableRow(context)
        row.layoutParams = TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3f)
        textViewList.forEach{ row.addView(generateTextView(it)) }
        return row
    }

    /**
     * generated textviews to be added to rows
     *
     * @param text text to be set for textview
     * @return textviews for table views
     */
    private fun generateTextView(text: String): TextView {
        val tv = TextView(context)
        tv.layoutParams = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        tv.text = text
        tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tv.textSize = 18f
        tv.setPadding(10, 10, 10, 10)
        tv.background = ContextCompat.getDrawable(context, R.drawable.ic_table_cell_background)
        return tv
    }

}