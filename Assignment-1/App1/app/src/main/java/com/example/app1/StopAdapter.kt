//////package com.example.app1
//////
//////import android.view.LayoutInflater
//////import android.view.View
//////import android.view.ViewGroup
//////import android.widget.TextView
//////import androidx.recyclerview.widget.RecyclerView
//////
//////class StopAdapter(private val stops: List<Stop>) : RecyclerView.Adapter<StopAdapter.ViewHolder>() {
//////
//////    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//////        val stopName: TextView = view.findViewById(android.R.id.text1)
//////    }
//////
//////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//////        val view = LayoutInflater.from(parent.context)
//////            .inflate(android.R.layout.simple_list_item_1, parent, false)
//////        return ViewHolder(view)
//////    }
//////
//////    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//////        val stop = stops[position]
//////        holder.stopName.text = "${stop.name} - Visa Required: ${if (stop.visaRequired) "Yes" else "No"} - Distance: ${stop.distance} Km"
//////    }
//////
//////    override fun getItemCount(): Int = stops.size
//////}
////
////
////package com.example.app1
////
////import android.graphics.Color
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import android.widget.TextView
////import androidx.recyclerview.widget.RecyclerView
////
////class StopAdapter(private val stops: List<Stop>) : RecyclerView.Adapter<StopAdapter.ViewHolder>() {
////
////    private var highlightedPosition = -1
////
////    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
////        val stopName: TextView = view.findViewById(android.R.id.text1)
////    }
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
////        val view = LayoutInflater.from(parent.context)
////            .inflate(android.R.layout.simple_list_item_1, parent, false)
////        return ViewHolder(view)
////    }
////
////    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////        val stop = stops[position]
////        holder.stopName.text = "${stop.name} - Visa Required: ${if (stop.visaRequired) "Yes" else "No"} - Distance: ${stop.distance} Km"
////
////        // Highlight the current stop
////        if (position == highlightedPosition) {
////            holder.stopName.setBackgroundColor(Color.YELLOW)
////        } else {
////            holder.stopName.setBackgroundColor(Color.TRANSPARENT)
////        }
////    }
////
////    override fun getItemCount(): Int = stops.size
////
////    fun highlightStop(position: Int) {
////        highlightedPosition = position
////        notifyDataSetChanged()
////    }
////}
//
//
package com.example.app1

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class StopAdapter(private var stops: List<Stop>, private var isKm: Boolean) : RecyclerView.Adapter<StopAdapter.ViewHolder>() {

    private var highlightedPosition = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stopCard: CardView = view.findViewById(R.id.stopCard) // CardView
        val stopName: TextView = view.findViewById(R.id.stopName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stop, parent, false)  // Assuming item_stop.xml contains a CardView
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stop = stops[position]
        val conversionFactor = if (isKm) 1.0 else 0.621371
        val unitText = if (isKm) "Km" else "Miles"

        // Create the full text with placeholders
        val fullText = "${stop.name}\nVisa Required: ${if (stop.visaRequired) "Yes" else "No"}\nDistance: ${(stop.distance * conversionFactor).roundToInt()} $unitText"

        // Create a SpannableString to apply styles
        val spannableString = SpannableString(fullText)

        // Apply style to stop.name (larger size and dark black color)
        val stopNameStart = 0
        val stopNameEnd = stop.name.length
        spannableString.setSpan(AbsoluteSizeSpan(20, true), stopNameStart, stopNameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)  // Larger font size for stop name
        spannableString.setSpan(ForegroundColorSpan(Color.BLACK), stopNameStart, stopNameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)  // Dark black color for stop name

        // Apply style to Visa Required (smaller size and green color)
        val visaRequiredStart = stop.name.length + 1  // After the stop name and first line break
        val visaRequiredEnd = fullText.indexOf("\n", visaRequiredStart)  // End of Visa Required text (next line break)
        spannableString.setSpan(AbsoluteSizeSpan(12, true), visaRequiredStart, visaRequiredEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)  // Smaller font size for Visa Required
        spannableString.setSpan(ForegroundColorSpan(Color.GREEN), visaRequiredStart, visaRequiredEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)  // Green color for Visa Required

        // Apply style to Distance (same font size and green color as Visa Required)
        val distanceStart = visaRequiredEnd + 1  // After the line break (and Visa Required text)
        val distanceEnd = fullText.length  // End of the text
        spannableString.setSpan(AbsoluteSizeSpan(12, true), distanceStart, distanceEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)  // Smaller font size for Distance
        spannableString.setSpan(ForegroundColorSpan(Color.GREEN), distanceStart, distanceEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)  // Green color for Distance

        // Set the formatted text to the TextView inside CardView
        holder.stopName.text = spannableString

        // Set the background color for the highlighted stop inside the CardView
        holder.stopCard.setCardBackgroundColor(if (position == highlightedPosition) Color.GRAY else Color.WHITE)
    }






    override fun getItemCount(): Int = stops.size

    fun updateUnit(isKm: Boolean) {
        this.isKm = isKm
        notifyDataSetChanged()
    }

    fun setHighlightedStop(position: Int) {
        highlightedPosition = position
        notifyDataSetChanged()
    }
}
