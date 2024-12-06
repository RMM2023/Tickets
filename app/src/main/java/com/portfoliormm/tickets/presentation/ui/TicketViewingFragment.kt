package com.portfoliormm.tickets.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfoliormm.tickets.databinding.FragmentTicketViewingBinding
import com.portfoliormm.tickets.presentation.ui.adapter.TicketViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketViewingFragment : Fragment() {

    private var _binding: FragmentTicketViewingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TicketViewingViewModel by viewModel()
    private val ticketAdapter = TicketViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketViewingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        binding.ticketsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = ticketAdapter
        }
    }

    private fun setupObservers() {
        viewModel.routeText.observe(viewLifecycleOwner) { route ->
            binding.routeText.text = route
        }

        viewModel.detailsText.observe(viewLifecycleOwner) { details ->
            binding.detailsText.text = details
        }

        viewModel.tickets.observe(viewLifecycleOwner) { tickets ->
            ticketAdapter.submitList(tickets)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
