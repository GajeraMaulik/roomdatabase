package com.example.roomdatabase

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.roomdatabase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch



@AndroidEntryPoint
class MainActivity : AppCompatActivity(),PersonAdapter.RowClickListener {

    val viewModel: PersonViewModel by viewModels()

    lateinit var personAdapter : PersonAdapter

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        /** initialize our _list for observing data from the Room*/
        viewModel.getAllPersons()

        /** attach adapter to the recycler view*/
        room_recycler.apply {
            personAdapter = PersonAdapter(this@MainActivity)
            adapter = personAdapter
        }
      //  val name = person_name?.text.toString()
        //val age = person_age?.text.toString().toInt()
        /** handle the save button */
        save_data_button.setOnClickListener {

             if (person_name.text.isEmpty() || person_age.text.isEmpty()) {

                 Toast.makeText(this,"Please Enter name & age ",Toast.LENGTH_LONG).show()
             }else{

                 if (save_data_button.text.equals("Save") ) {
                     val person = Person(0,name = person_name.text.toString(), age = person_age.text.toString().toInt())

                     Toast.makeText(this, "Add $person", Toast.LENGTH_LONG).show()

                    viewModel.insert(person)


                     person_name.text.clear()
                     person_age.text.clear()
                 }else{
                     val person = Person( person_name.getTag(person_name.id).toString().toInt(),name = person_name.text.toString(), age = person_age.text.toString().toInt())

                     Toast.makeText(this,"Updated $person",Toast.LENGTH_LONG).show()

                     viewModel.update(person)
                     save_data_button.text = "Save"

                     person_name.text.clear()
                     person_age.text.clear()
                 }



             }
        }


        /** observe the viewmodel list */
        viewModel.list.observe(this) {
            it?.let {
                personAdapter.setContentList(it)
            }
        }

        /** delete the adapter list item*/
        personAdapter.setOnLongItemClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
           builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to Delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { dialog, id -> viewModel.delete(it)
                        Toast.makeText(this,"Removed $it",Toast.LENGTH_LONG).show()})
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
            val alert = builder.create()
            alert.show()

        }



    }

    override fun onItemClickListener(person: Person) {
        person_name.setText(person.name)
        person_age.setText(Integer.toString(person.age))
        person_name.setTag(person_name.id,person.id)
        save_data_button.setText("Update")
    }

}
