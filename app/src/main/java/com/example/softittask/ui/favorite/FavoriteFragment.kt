package com.example.softittask.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.softittask.data.db.DbHelper
import com.example.softittask.data.local.Drink
import com.example.softittask.databinding.FragmentFavoriteBinding
import com.example.softittask.ui.common.DialogHelper
import com.example.softittask.ui.common.DrinkAdapter
import com.example.softittask.ui.common.OnDrinkClickListener
import com.example.softittask.ui.HomeSharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(), OnDrinkClickListener {

    private lateinit var viewModel: HomeSharedViewModel
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(HomeSharedViewModel::class.java)

        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DrinkAdapter(this)
        binding.recyclerView.adapter = adapter

        DialogHelper.showLoadingDialog(requireActivity())
        viewModel.getFavoritesLiveData().observe(viewLifecycleOwner) {
            DialogHelper.hideLoadingDialog()
            adapter.setAdapterData(it)
        }

        viewModel.getLocalFavorites()

    }

    override fun onItemClick(drink: Drink) {
        if(drink.isFav==1) {
            drink.isFav = 0
        } else {
            drink.isFav = 1
        }
        CoroutineScope(Dispatchers.IO).launch {
            DbHelper.getInstance(requireActivity()).drinksDao().insertDrink(drink)
        }
    }
}