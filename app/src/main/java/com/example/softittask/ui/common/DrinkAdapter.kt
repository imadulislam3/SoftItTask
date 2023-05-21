package com.example.softittask.ui.common

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.softittask.R
import com.example.softittask.data.local.Drink
import com.example.softittask.databinding.RecipeItemBinding

class DrinkAdapter(
    var onDrinkClickListener: OnDrinkClickListener
) :
    RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    private var contxt: Context? = null
    private var drinkList: ArrayList<Drink> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        contxt = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecipeItemBinding.inflate(layoutInflater, parent, false)
        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {

        with(holder) {
            with(drinkList[position]) {
                binding.drinkName.text = this.strDrink
                binding.drinkInfo.text = this.strInstructions

                binding.checkBox.isChecked = TextUtils.equals(this.strAlcoholic, "Alcoholic")

                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)

                if (this.isFav == 1) {
                    binding.favoriteIcon.setImageResource(R.drawable.selected_star)
                } else {
                    binding.favoriteIcon.setImageResource(R.drawable.un_selected_star)
                }

                Glide.with(contxt!!).load(this.strDrinkThumb).apply(options).into(binding.icon);

                binding.favoriteIcon.setOnClickListener {
                    if(this.isFav == 1) {
                        binding.favoriteIcon.setImageResource(R.drawable.un_selected_star)
                    } else {
                        binding.favoriteIcon.setImageResource(R.drawable.selected_star)
                    }
                    onDrinkClickListener.onItemClick(this)
                }
            }
        }
    }

    fun setAdapterData(list: List<Drink>) {
        drinkList.clear()
        drinkList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = drinkList.size

    inner class DrinkViewHolder(val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
