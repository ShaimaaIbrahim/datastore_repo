package com.google.datastore_repo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

   lateinit var userManager : UserManager

    var name = ""
    var age  = ""
    var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userManager = UserManager(this)

        buttonSave()

        observeData()

    }

    private fun observeData() {

     userManager.userNameFlow.asLiveData().observe(this , Observer{
         name = it
         tv_name.text = name
     })

        userManager.userAgeFlow.asLiveData().observe(this , Observer{
            age = it.toString()
            tv_age.text = age.toString()
        })
        userManager.userGenderFlow.asLiveData().observe(this , Observer{

            gender = if (it) "Male" else "Female"
            tv_gender.text = gender
        })

    }

    private fun buttonSave() {

        btn_save.setOnClickListener {

            name = et_name.text.toString()
            age =et_age.text.toString()
            val isMale = switch_gender.isChecked

            GlobalScope.launch {

                userManager.storeUser(age.toInt() , name , isMale)
            }
        }
    }
}