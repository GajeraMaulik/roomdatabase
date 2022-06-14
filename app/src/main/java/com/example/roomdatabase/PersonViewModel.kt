package com.example.roomdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val personRepo:PersonRepo):ViewModel() {
    /** observing this list into MainActivity and update our view*/
    private val _list = MutableLiveData<List<Person>>()
    val list: LiveData<List<Person>> = _list


    fun insert(person: Person) = viewModelScope.launch {
        /** insert data to Room*/
        personRepo.insert(person)
        /** and then update our list which we are observing to the MainActivity*/
        _list.postValue(personRepo.getAllPersons())
    }


    fun delete(person: Person) = viewModelScope.launch {
        personRepo.delete(person)
        _list.postValue(personRepo.getAllPersons())
    }


    fun update(person: Person) = viewModelScope.launch {
        personRepo.update(person)
        _list.postValue(personRepo.getAllPersons())
    }

    fun getAllPersons() = viewModelScope.launch {
        _list.postValue(personRepo.getAllPersons())
    }

}






