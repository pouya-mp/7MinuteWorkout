package com.pouyaa.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbarHistoryActivity)
        var actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = getString(R.string.history)
            }
        toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        viewAllCompletedDates()

    }

    private fun viewAllCompletedDates(){
        val dbhandler = SqLiteOpenHelper(this,null)
        val completedDates = dbhandler.getAllCompletedDates()

        if (completedDates.size > 0 ){
            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE
            noDataHistory.visibility = View.GONE

            rvHistory.layoutManager = LinearLayoutManager(this)
            var historyAdapter = HistoryAdapter(this,completedDates)
            rvHistory.adapter = historyAdapter

        }else{
            tvHistory.visibility = View.GONE
            rvHistory.visibility = View.GONE
            noDataHistory.visibility = View.VISIBLE
        }

    }

}
