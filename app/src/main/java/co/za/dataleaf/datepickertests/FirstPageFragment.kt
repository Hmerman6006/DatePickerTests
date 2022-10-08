package co.za.dataleaf.datepickertests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.za.dataleaf.datepickertests.databinding.FirstPageFragmentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class FirstPageFragment: Fragment() {

    private lateinit var _binding: FirstPageFragmentBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FirstPageFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_firstPageFragment_to_secondPageFragment)
        }

        val picker = MaterialDatePicker
            .Builder
            .datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText("Select date of birth")
            .build()
        openDatePicker(picker)

        binding.tietDate.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, "TIME_PICKER")
        }

    }

    private fun openDatePicker(picker: MaterialDatePicker<Long>) {

        picker.addOnPositiveButtonClickListener {
            Log.d("1stFragment", "addOnPositiveButtonClickListener $it")
            it?.let { dt ->

                val offset = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt), ZoneOffset.UTC)
                val zid = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt), ZoneId.systemDefault()).toString()
                println("Offset: $offset")
                println("Zone id: ${zid}")
                val instant = Instant.now(Clock.systemUTC())  // .truncatedTo( ChronoUnit.SECONDS ).epochSecond
//                    .atOffset(ZoneOffset.UTC)
//                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
              // LocalDateTime.ofInstant(Instant.now().epochSecond, ZoneOffset.UTC)  // .atOffset(ZoneOffset.UTC)
                val instantStr: String = instant.toString()
                if (zid.isNotBlank()) {
                    val ss = zid.slice(0..9)
                    println("Default String format for Instant: $ss ~ $instantStr")
                    binding.tietDate.setText(ss)
                }
            }
        }

        picker.addOnDismissListener {
            Log.d("1stFragment", "addOnDismissListener $it")
        }

        picker.removeOnNegativeButtonClickListener {
            Log.d("1stFragment", "removeOnNegativeButtonClickListener $it")
        }

        picker.addOnCancelListener {
            Log.d("1stFragment", "addOnCancelListener $it")
        }
    }
}