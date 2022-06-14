package com.example.roomdatabase

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.PersonViewholderBinding

class PersonAdapter(val listener: RowClickListener):RecyclerView.Adapter<PersonAdapter.MyViewHolder>() {

    private var list = mutableListOf<Person>()

    private var setOnItemLongClickListener : ((Person)->Unit)?=null

    @SuppressLint("NotifyDataSetChanged")
    fun setContentList(list: List<Person>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    fun setOnLongItemClickListener(listner:(Person)->Unit){
        setOnItemLongClickListener=listner
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonAdapter.MyViewHolder {
        val binding= PersonViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewDataBinding.person= list[position]
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(list[position])
        }

        holder.viewDataBinding.root.setOnLongClickListener {
            setOnItemLongClickListener?.let {
                it(list[position])
            }
            return@setOnLongClickListener  true
        }
/*        deleteUserID.setOnClickListener {
            listener.onDeleteUserClickListener(data)
        }*/
    }



    override fun getItemCount(): Int {
        return this.list.size
    }

    inner class MyViewHolder(val viewDataBinding: PersonViewholderBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    interface RowClickListener{
        //fun onDeleteUserClickListener(person: Person)
        fun onItemClickListener(person: Person)

    }

 /*   private fun AlertDialog(position: Int, title: String, btnText: String) {
        val dialog: Dialog = Dialog()
        dialog.setContentView(R.layout.add_item)

        val name: EditText = dialog.findViewById(R.id.person_name)
        val btnaction = dialog.findViewById<Button>(R.id.btnaction)
        val age: EditText = dialog.findViewById(R.id.person_age)
        btnaction.text = btnText

        btnaction.setOnClickListener(
            View.OnClickListener
        {
            var name: String = edtitem.text.toString()
            if (name.length >= 15) {
                edtitem.error = "Maximum Length Upto 15 "
            } else if (name != "") {
                name = edtitem.text.toString().trim()
                items[position] = name
                notifyItemChanged(position)
                dialog.dismiss()

            } else {
                edtitem.error = "Can Not be Blank"
                // Toast.makeText(context, "Please Enter Item", Toast.LENGTH_SHORT).show()
            }



        })
        dialog.show()
    }

*/
}