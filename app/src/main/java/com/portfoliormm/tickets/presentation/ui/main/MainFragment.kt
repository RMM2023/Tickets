package com.portfoliormm.tickets.presentation.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.portfoliormm.tickets.R
import com.portfoliormm.tickets.databinding.FragmentMainBinding
import com.portfoliormm.tickets.presentation.ui.adapter.OffersAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()
    private val adapter = OffersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupDepartureEditText()
        setupArrivalEditText()
        setupObservers()

        viewModel.loadOffers()
    }

    private fun setupRecyclerView() {
        binding.offersRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MainFragment.adapter
        }
    }

    private fun setupDepartureEditText() {
        binding.departureEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.let { text ->
                    viewModel.saveDeparture(text)
                }
            }
        })
    }

    private fun setupArrivalEditText() {
        binding.arrivalEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
                val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_rec, null)
                bottomSheetDialog.setContentView(bottomSheetView)

                // Загружаем город из SharedPreferences в from_edit_text
                val searchLayout = bottomSheetView.findViewById<ConstraintLayout>(R.id.search_const)
                val fromEditText = searchLayout?.findViewById<EditText>(R.id.from_edit_text)
                val toEditText = searchLayout?.findViewById<EditText>(R.id.to_edit_text)

                viewModel.lastDeparture.value?.let { city ->
                    fromEditText?.setText(city)
                }

                // Настраиваем клики на города
                bottomSheetView.findViewById<View>(R.id.city_istanbul)?.setOnClickListener {
                    toEditText?.setText("Стамбул")
                }

                bottomSheetView.findViewById<View>(R.id.city_sochi)?.setOnClickListener {
                    toEditText?.setText("Сочи")
                }

                bottomSheetView.findViewById<View>(R.id.city_phuket)?.setOnClickListener {
                    toEditText?.setText("Пхукет")
                }


                // Настраиваем клики на кнопки bar_1
                val bar1View = bottomSheetView.findViewById<View>(R.id.bar_1_rec)

                bar1View?.findViewById<View>(R.id.complexRouteButton)?.setOnClickListener {
                    Toast.makeText(requireContext(), "Сложный маршрут", Toast.LENGTH_SHORT).show()
                }

                bar1View?.findViewById<View>(R.id.anywhereButton)?.setOnClickListener {
                    toEditText?.setText("Куда угодно")
                }

                bar1View?.findViewById<View>(R.id.weekendButton)?.setOnClickListener {
                    Toast.makeText(requireContext(), "Выходные", Toast.LENGTH_SHORT).show()
                }

                bar1View?.findViewById<View>(R.id.hotDealsButton)?.setOnClickListener {
                    Toast.makeText(requireContext(), "Горящие билеты", Toast.LENGTH_SHORT).show()
                }

                // Добавляем дебаунс для toEditText
                var searchJob: Job? = null
                toEditText?.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable?) {
                        searchJob?.cancel()
                        searchJob = lifecycleScope.launch {
                            delay(1000) // 1 секунда дебаунс
                            bottomSheetDialog.dismiss()
                            viewModel.saveArrival(toEditText.text.toString())
                            findNavController().navigate(R.id.action_mainFragment_to_countrySearchFragment)
                        }
                    }
                })

                // Делаем BottomSheet полноэкранным
                val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT

                bottomSheetDialog.show()

                // Устанавливаем состояние EXPANDED сразу после показа
                bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetDialog.behavior.skipCollapsed = true
            }
        }
    }

    private fun setupObservers() {
        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            adapter.setOffers(offers)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }

        viewModel.lastDeparture.observe(viewLifecycleOwner) { lastDeparture ->
            if (binding.departureEditText.text.toString() != lastDeparture) {
                binding.departureEditText.setText(lastDeparture)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
