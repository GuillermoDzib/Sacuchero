package com.example.agenda

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sacuchero.databinding.FoodItemBinding
import com.bedu.sacuchero.model.Food
import com.bumptech.glide.Glide
import kotlinx.coroutines.NonDisposableHandle.parent

class HomeAdapter(private val clickListener: (Food) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.FoodViewHolder>() {
    private val foodList = ArrayList<Food>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding: FoodItemBinding =
            FoodItemBinding.inflate(layoutInflater, parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position], clickListener)
    }

    fun setList(food: List<Food>) {
        foodList.clear()
        foodList.addAll(food)
    }
    inner class FoodViewHolder(private val binding: FoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food, clickListener: (Food) -> Unit) {
            binding.tvName.text = food.nam
            binding.tvDescription.text = food.description
            binding.tvPrecio.text = food.price.toString()
            Glide.with(context)
                .load(food.pic)
                .into(binding.ivPic)
            binding.root.setOnClickListener {
                clickListener(food)
            }
        }
    }

}

