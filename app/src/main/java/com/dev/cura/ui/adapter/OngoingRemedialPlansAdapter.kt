package com.dev.cura.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.cura.R
import com.dev.cura.domain.model.RemedialData

class OngoingRemedialPlansAdapter : RecyclerView.Adapter<OngoingRemedialPlansAdapter.PlanViewHolder>() {

    // switch to a mutable list so we can update it dynamically
    private val plans = mutableListOf<RemedialData>()

    // ViewHolder class
    inner class PlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.planIcon)
        val title: TextView = itemView.findViewById(R.id.planTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ongoing_remedial_plan, parent, false)
        return PlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val plan = plans[position]
        holder.icon.setImageResource(plan.iconResId)
        holder.title.text = plan.title
    }

    override fun getItemCount(): Int = plans.size

    /** Call this from your fragment when you have new data: */
    fun updateData(newPlans: List<RemedialData>) {
        plans.clear()
        plans.addAll(newPlans)
        notifyDataSetChanged()
    }
}
