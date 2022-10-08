package co.za.dataleaf.datepickertests

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.za.dataleaf.datepickertests.databinding.SecondPageFragmentBinding

class SecondPageFragment: Fragment() {

    private lateinit var _binding: SecondPageFragmentBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SecondPageFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
    val dateText = DateTextWatchers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.prevBtn.setOnClickListener {
            findNavController().navigate(R.id.action_secondPageFragment_to_firstPageFragment)
        }

        binding.tietDateYr.addTextChangedListener(DateTextWatcherListenerHandler(
            VarsDateTextWatchers.YEAR, dateText, binding.tietDateYr, binding.tietDateMonth, binding.tietDateYr
        ))

        binding.tietDateMonth.addTextChangedListener(DateTextWatcherListenerHandler(
            VarsDateTextWatchers.MONTH, dateText, binding.tietDateMonth, binding.tietDateMonth, binding.tietDateYr
        ))

        binding.tietDateDay.addTextChangedListener(DateTextWatcherListenerHandler(
            VarsDateTextWatchers.DAY, dateText, binding.tietDateDay, binding.tietDateMonth, binding.tietDateYr
        ))

        binding.submitBtn.setOnClickListener {
            when {
                (dateText.year == null) -> binding.tietDateYr.setError ("Year required")
                (dateText.month == null) -> binding.tietDateMonth.setError ("Month required")
                (dateText.day == null) -> binding.tietDateDay.setError ("Day required")
                else -> {
                    val d =
                        "${dateText.year}-${dateText.month?.let { if (it < 10) "0$it" else it }}-${dateText.day?.let { if (it < 10) "0$it" else it }}"
                    Log.d("2nd", d)
                    binding.tvSubmit.text = d
                }
            }
        }

    }


}