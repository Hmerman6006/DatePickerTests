package co.za.dataleaf.datepickertests

import android.os.Build
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.IsoFields
import java.util.*
import kotlin.collections.ArrayList

object DateTimeUtil {
    private val androidVersion = Build.VERSION.SDK_INT

    @JvmStatic
    fun getNow(): LocalDateTime? {
        return LocalDateTime.now()
    }

    @JvmStatic
    fun getNowString(formatter: String = "yyyy-MM-dd HH:mm:ss"): String {
        val fmt = DateTimeFormatter.ofPattern(formatter)
        return LocalDateTime.now().format(fmt)
    }

    @JvmStatic
    fun getDateDifference(fromDate: String, toDate: String, formatter: String, locale: Locale = Locale.getDefault()): Period? {

        val fmt: DateTimeFormatter = DateTimeFormatter.ofPattern(formatter, locale)
        val bgn: LocalDate = LocalDate.from(fmt.parse(fromDate))
        val end: LocalDate = LocalDate.from(fmt.parse(toDate))

        return Period.between(
            bgn,
            end
        )
    }

    @JvmStatic
    fun getPeriods(fromDate: String, toDate: String, formatter: String, locale: Locale = Locale.getDefault()): Map<String, Int?> {

        val period = getDateDifference(fromDate, toDate, formatter, locale)
        val weeks = period?.days?.div(7)

        return mapOf("years" to period?.years, "months" to period?.months, "days" to period?.days, "weeks" to weeks)
    }

    @JvmStatic
    fun weekNow(): Int {
        return LocalDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
    }

    @JvmStatic
    fun lastWeek(yr: Int): Int {
        val is53weekYear = LocalDate.of(yr, 1, 1).dayOfWeek == DayOfWeek.THURSDAY ||
                LocalDate.of(yr, 12, 31).dayOfWeek == DayOfWeek.THURSDAY
        return if (is53weekYear) 53 else 52
    }

    @JvmStatic
    fun getMonths(): List<Long> {
        return listOf<Long>(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L)
    }

    @JvmStatic
    fun getMonth(month: Int): Month {
        return when(month) {
            1 -> Month.JANUARY
            2 -> Month.FEBRUARY
            3 -> Month.MARCH
            4 -> Month.APRIL
            5 -> Month.MAY
            6 -> Month.JUNE
            7 -> Month.JULY
            8 -> Month.AUGUST
            9 -> Month.SEPTEMBER
            10 -> Month.OCTOBER
            11 -> Month.NOVEMBER
            12 -> Month.DECEMBER
            else -> throw Exception("OutOfMonthBounds")
        }
    }

    @JvmStatic
    fun getDaysMaxLength(month: Int, leapYear: Boolean): Int {
        return if(month == 2) getMonth(month).length(leapYear) else getMonth(month).maxLength()
    }

    @JvmStatic
    fun isLeapYear(yr: Int): Boolean {
        var leap = false
        if (yr % 4 == 0) {  // division by 4
            leap = if (yr % 100 == 0) {  // is century
                yr % 400 == 0
            } else {
                true
            }
        }
        return leap
    }
}