package com.pouyaa.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        setSupportActionBar(toolbarFinishActivity)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
         toolbarFinishActivity.setNavigationOnClickListener {
             onBackPressed()
         }

        btnFinish.setOnClickListener {
            finish()
        }
        addDateToDatabase()
    }

    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        val sdf = SimpleDateFormat("dd MM YYYY HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqLiteOpenHelper(this,null)
        dbHandler.addDate(date)

    }

}
