package com.portfoliormm.tickets.presentation.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.portfoliormm.tickets.R
import com.portfoliormm.tickets.databinding.FragmentCountrySearchBinding
import com.portfoliormm.tickets.presentation.ui.adapter.TicketOffersAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class CountrySearchFragment : Fragment() {

    private var _binding: FragmentCountrySearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountrySearchViewModel by viewModel()
    private val adapter = TicketOffersAdapter()
    private val dateFormat = SimpleDateFormat("dd MMM, EE", Locale("ru"))
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountrySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        setupDateButton()
    }

    private fun setupRecyclerView() {
        binding.ticketsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CountrySearchFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.fromCity.observe(viewLifecycleOwner) { city ->
            binding.fromCity.setText(city)
        }

        viewModel.toCity.observe(viewLifecycleOwner) { city ->
            binding.toCity.setText(city)
        }

        viewModel.departureDate.observe(viewLifecycleOwner) { calendar ->
            binding.departureDateButton.text = dateFormat.format(calendar.time).lowercase()
        }

        viewModel.tickets.observe(viewLifecycleOwner) { tickets ->
            adapter.setOffers(tickets)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.swapCitiesButton.setOnClickListener {
            val fromCity = binding.fromCity.text.toString()
            val toCity = binding.toCity.text.toString()

            binding.fromCity.setText(toCity)
            binding.toCity.setText(fromCity)

            // Сохраняем измененные значения
            viewModel.saveCities(toCity, fromCity)
        }

        binding.closeButton.setOnClickListener {
            binding.toCity.setText("")
            // Сохраняем пустое значение города прибытия
            viewModel.saveCities(binding.fromCity.text.toString(), "")
        }

        binding.lineImage.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.viewAllTicketsButton.setOnClickListener {
            findNavController().navigate(R.id.action_countrySearchFragment_to_ticketViewingFragment)
        }
    }


    private fun setupDateButton() {
        binding.departureDateButton.setOnClickListener {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = viewModel.departureDate.value?.timeInMillis ?: System.currentTimeMillis()
            }

            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    viewModel.saveDepartureDate(calendar)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
