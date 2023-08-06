package com.gmail.apigeoneer.aesteroids.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.apigeoneer.aesteroids.data.domain.Asteroid
import com.gmail.apigeoneer.aesteroids.databinding.AsteroidItemBinding

class AsteroidAdapter(
    val onClickListener: OnClickListener
) : androidx.recyclerview.widget.ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(DiffCallback){

    // We make this a companion object cuz we want this in the namespace of our class,
    // but it doesn't need a reference to our class
    object DiffCallback: DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id   //
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val view = AsteroidItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(asteroid)
        }
    }

    inner class AsteroidViewHolder(private val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroids = asteroid
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}