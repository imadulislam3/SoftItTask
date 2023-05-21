package com.example.softittask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.softittask.data.db.DbHelper
import com.example.softittask.data.local.Drink
import com.example.softittask.databinding.FragmentHomeBinding
import com.example.softittask.ui.HomeSharedViewModel
import com.example.softittask.ui.common.DialogHelper
import com.example.softittask.ui.common.DrinkAdapter
import com.example.softittask.ui.common.OnDrinkClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnDrinkClickListener {

    private lateinit var homeViewModel: HomeSharedViewModel
    private lateinit var binding: FragmentHomeBinding
    private val tag = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel =
            ViewModelProvider(requireActivity())[HomeSharedViewModel::class.java]

        val adapter = DrinkAdapter(this)
        binding.recyclerView.adapter = adapter

        DialogHelper.showLoadingDialog(requireActivity())
        homeViewModel.getRecipesLiveData().observe(viewLifecycleOwner) {
            DialogHelper.hideLoadingDialog()
            adapter.setAdapterData(it)
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton: RadioButton = group.findViewById<View>(checkedId) as RadioButton
            if(checkedRadioButton.id == binding.byName.id) {
                binding.edittext.setText("margarita")
                homeViewModel.getRecipes("margarita")
            } else {
                homeViewModel.getRecipes("f")
                binding.edittext.setText("f")
            }
        }

        homeViewModel.getRecipes("margarita")
    }

    override fun onItemClick(drink: Drink) {
        if (drink.isFav == 1) {
            drink.isFav = 0
        } else {
            drink.isFav = 1
        }
        CoroutineScope(Dispatchers.IO).launch {
            DbHelper.getInstance(requireActivity()).drinksDao().insertDrink(drink)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DialogHelper.hideLoadingDialog()
    }
}