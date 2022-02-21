package com.example.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson7databasepassport.databinding.ItemCitizenBinding
import com.example.room.entity.Citizen

class CitizenRVAdapter(var context: Context, var citizenList: List<Citizen>) :
    RecyclerView.Adapter<CitizenRVAdapter.CitizenViewHolder>() {
    lateinit var itemAdapter: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(citizen: Citizen)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemAdapter = listener
    }

    inner class CitizenViewHolder(var binding: ItemCitizenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(citizen: Citizen, position: Int) {
            binding.citizenFullName.text =
                "${position + 1}. ${citizen.firstname} ${citizen.lastname}"
            binding.passportNumber.text = citizen.passportNumber
            binding.root.setOnClickListener {
                itemAdapter.onClick(citizen)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitizenViewHolder {
        return CitizenViewHolder(ItemCitizenBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: CitizenViewHolder, position: Int) {
        val citizen = citizenList[position]
        holder.onBind(citizen, position)
    }

    override fun getItemCount(): Int = citizenList.size
}