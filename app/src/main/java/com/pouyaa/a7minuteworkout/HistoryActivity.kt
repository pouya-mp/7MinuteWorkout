package com.pouyaa.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbarHistoryActivity)
        val actionBar = supportActionBar
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
        val dbHandler = SqLiteOpenHelper(this,null)
        val completedDates = dbHandler.getAllCompletedDates()

        if (completedDates.size > 0 ){
            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE
            noDataHistory.visibility = View.GONE

            rvHistory.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this,completedDates)
            rvHistory.adapter = historyAdapter

        }else{
            tvHistory.visibility = View.GONE
            rvHistory.visibility = View.GONE
            noDataHistory.visibility = View.VISIBLE
        }

    }

}
