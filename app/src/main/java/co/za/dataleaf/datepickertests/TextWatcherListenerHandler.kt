package co.za.dataleaf.datepickertests

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText

data class DateTextWatchers(
    var year: Int? = null,
    var month: Int? = null,
    var day: Int? = null
)

enum class VarsDateTextWatchers {
    YEAR,
    MONTH,
    DAY
}

open class DateTextWatcherListenerHandler(
    private val what: VarsDateTextWatchers,
    private val dateInputWatched: DateTextWatchers,
    private val edInput: EditText,
    private val edMonthInput: EditText,
    private val edYearInput: EditText
): TextWatcher {
    private var isFormatting = false
    private val maxLength = when(what) {
        VarsDateTextWatchers.YEAR -> 4
        VarsDateTextWatchers.MONTH -> 2
        VarsDateTextWatchers.DAY -> 2
    }
    private val months = DateTimeUtil.getMonths()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (isFormatting) return
        Log.d("2ndFragment", "beforeTextChanged $s")
        Log.d("2ndFragment", "beforeTextChanged $start")
        Log.d("2ndFragment", "beforeTextChanged $count")
        Log.d("2ndFragment", "beforeTextChanged $after")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (isFormatting) return
        Log.d("2ndFragment", "onTextChanged $s")
        Log.d("2ndFragment", "onTextChanged $start")
        Log.d("2ndFragment", "onTextChanged $count")
    }

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) return
        Log.d("2ndFragment", "afterTextChanged $s")
        Log.d("2ndFragment", "afterTextChanged ${s?.length}")

        isFormatting = true
        validateInputLong(s, maxLength)
        isFormatting = false
    }

    private fun validateInputLong(s: Editable?, length: Int) {
        if (isLong(s.toString())) {
            s?.let {
                if (it.length > length) it.delete(length, it.length)
                val i = s.toString()
                val l = i.toLong()
                if (l < 1L) {
                    s.clear()
                    setWhatNull()
                    return
                }

                when(what) {
                    VarsDateTextWatchers.YEAR -> {
                        dateInputWatched.year = l.toInt()
                        Log.d("WATCHER YEAR", dateInputWatched.year.toString())
                    }
                    VarsDateTextWatchers.MONTH -> {
                        if (l > 12 && !(l in months)) {
                            s.clear()
                            edInput.setError("Month between 1 - 12")
                            dateInputWatched.month = null
                            return
                        } else {
                            edInput.setError(null)
                            dateInputWatched.month = l.toInt()
                        }
                        Log.d("WATCHER MONTHS", dateInputWatched.month.toString())
                    }
                    VarsDateTextWatchers.DAY -> {
                        Log.d("WATCHERS::", "MM: ${dateInputWatched.month} YYYY: ${dateInputWatched.year}")
                        if (dateInputWatched.month != null && dateInputWatched.year != null) {
                            val days = DateTimeUtil.getDaysMaxLength(dateInputWatched.month!!, DateTimeUtil.isLeapYear(
                                dateInputWatched.year!!
                            )).toLong()
                            Log.d("WATCHER DAYS", "$days :: $l => ${l < days} => ${l > days}")
                            if (l > days) {
                                s.clear()
                                edInput.setError("Day between 1 - $days")
                                dateInputWatched.day = null
                            } else {
                                edInput.setError(null)
                                dateInputWatched.day = l.toInt()
                            }
                        } else {
                            s.clear()
                            if (dateInputWatched.month == null) edMonthInput.setError("Month required")
                            else edYearInput.setError("Year required")
                            dateInputWatched.month = null
                        }
                    }
                }
            }
        } else {
            s?.let {
                if (it.length > 0) it.delete(it.length - 1, it.length)
                else it.clear()
                setWhatNull()
            }
        }
    }

    private fun setWhatNull() {
        when(what) {
            VarsDateTextWatchers.YEAR -> dateInputWatched.year = null
            VarsDateTextWatchers.MONTH -> dateInputWatched.month = null
            VarsDateTextWatchers.DAY -> dateInputWatched.day = null
        }
    }

    private fun isLong(str: String?) = str?.toLongOrNull()?.let { true } ?: false

}