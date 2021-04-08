package joseq.storeapp.ui.main.adapter

import joseq.storeapp.R
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.expandable_item.*

class ExpandableItem(private val title: String) : Item(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {

            item_expandable_header_title.text = title
            item_expandable_header_icon.setImageResource(getIcon())

            item_expandable_header_root.setOnClickListener {
                expandableGroup.onToggleExpanded()
            }
        }
    }

    override fun getLayout(): Int = R.layout.expandable_item

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    private fun getIcon() = if (expandableGroup.isExpanded) R.drawable.up_arrow else
        R.drawable.down_arrow

}