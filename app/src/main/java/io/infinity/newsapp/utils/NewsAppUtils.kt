package io.infinity.newsapp.utils

import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.core.view.get
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formatDate(dateStr: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val dateFormat: DateFormat = SimpleDateFormat("MMM dd, yyyy 'at' HH:mm a")
    format.timeZone = TimeZone.getTimeZone("UTC")
    val date = format.parse(dateStr)
    return dateFormat.format(date!!)

}

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })

    return query

}

fun ChipGroup.getChipChangedStateFlow(): StateFlow<String> {
    val selectedChip = MutableStateFlow("")
    this.setOnCheckedChangeListener { chipGroup, checkedId ->
        if (checkedId != -1) {
            selectedChip.value = chipGroup?.children
                ?.toList()
                ?.filter { (it as Chip).isChecked }
                ?.get(0).run { (this as Chip).text }.toString()

        } else {
            selectedChip.value = ""
        }
    }

    return selectedChip
}
