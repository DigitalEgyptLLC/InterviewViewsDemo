package com.intercept.viewsdemo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intercept.viewsdemo.R
import com.intercept.viewsdemo.ui.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessage: TextView
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        errorMessage = view.findViewById(R.id.error_message)

        val adapter = HomeAdapter {
            homeViewModel.onDetailsClicked()
        }
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is HomeUIState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        errorMessage.visibility = View.GONE
                    }

                    is HomeUIState.Success -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        errorMessage.visibility = View.GONE
                        adapter.submitList(uiState.list)

                    }

                    is HomeUIState.Failure -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = uiState.errorMessage
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.actions.collect { action ->
                if (action is HomeNavigationAction.NavigateToDetails) {
                    startActivity(Intent(requireContext(), DetailsActivity::class.java))
                }
            }
        }
    }

}