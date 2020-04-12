package com.pouyaa.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private val metricView = "Metric View"
    private val usView = "US view"
    private var currentUnitView = metricView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        setSupportActionBar(toolbarBMIActivity)

        val actionbar = supportActionBar

        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = getString(R.string.calculateBmi)
        }
        toolbarBMIActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateBMI.setOnClickListener {

            if (currentUnitView == metricView){

                if (validateMetricUnits()){
                    calculateBMIMetric()
                }else{
                    Toast.makeText(this,getString(R.string.pleaseEnterValidValues),Toast.LENGTH_SHORT).show()
                }

            }else {
                if (validateUsUnits()){
                    calculateUsMetric()
                }else{
                    Toast.makeText(this,getString(R.string.pleaseEnterValidValues),Toast.LENGTH_SHORT).show()
                }

            }

        }

        makeVisibleMetricUnitView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == rbMetricsUnit.id){
                makeVisibleMetricUnitView()
            }else {
                makeVisibleUsUnitView()
            }
        }

    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = getString(R.string.VerySeverelyUnderweight)
            bmiDescription = getString(R.string.VerySeverelyUnderweightDescription)
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = getString(R.string.SeverelyUnderweight)
            bmiDescription = getString(R.string.SeverelyUnderweightDescription)
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = getString(R.string.Underweight)
            bmiDescription = getString(R.string.UnderweightDescription)
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = getString(R.string.Normal)
            bmiDescription = getString(R.string.NormalDescription)
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = getString(R.string.Overweight)
            bmiDescription = getString(R.string.OverweightDescription)
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = getString(R.string.ObeseClass1)
            bmiDescription = getString(R.string.ObeseClass1Description)
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = getString(R.string.ObeseClass2)
            bmiDescription = getString(R.string.ObeseClass2Description)
        } else {
            bmiLabel = getString(R.string.ObeseClass3)
            bmiDescription = getString(R.string.ObeseClass3Description)
        }



        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE


        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }

    private fun validateMetricUnits() : Boolean{
        var isValid = true

        if (etMetricWeight.text.toString().isEmpty()){
            isValid = false
        }else if (etMetricHeight.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUsUnits() : Boolean{
        var isValid = true

        when {
            etUsWeight.text.toString().isEmpty() -> { isValid = false }
            etUsHeightFeet.text.toString().isEmpty() -> { isValid = false }
            etUsHeightInch.text.toString().isEmpty() -> { isValid =false }
        }

        return isValid
    }

    private fun makeVisibleMetricUnitView(){
        currentUnitView = metricView

        etMetricHeight.text!!.clear()
        etMetricWeight.text!!.clear()

        tilMetricHeight.visibility = View.VISIBLE
        tilMetricWeight.visibility = View.VISIBLE


        tilUSWeight.visibility = View.GONE
        llUsHeight.visibility = View.GONE
//        tilUsHeightFeet.visibility = View.GONE
//        tilUsHeightInch.visibility = View.GONE


        tvYourBMI.visibility = View.GONE
        tvBMIValue.visibility = View.GONE
        tvBMIType.visibility = View.GONE
        tvBMIDescription.visibility = View.GONE

    }

    private fun makeVisibleUsUnitView(){
        currentUnitView = usView

        etUsWeight.text!!.clear()
        etUsHeightFeet.text!!.clear()
        etUsHeightInch.text!!.clear()

        tilMetricHeight.visibility = View.GONE
        tilMetricWeight.visibility = View.GONE

        tilUSWeight.visibility = View.VISIBLE
        llUsHeight.visibility = View.VISIBLE
//        tilUsHeightFeet.visibility = View.VISIBLE
//        tilUsHeightInch.visibility = View.VISIBLE

        tvYourBMI.visibility = View.GONE
        tvBMIValue.visibility = View.GONE
        tvBMIType.visibility = View.GONE
        tvBMIDescription.visibility = View.GONE

    }

    private fun calculateBMIMetric(){
        val weight : Float = etMetricWeight.text.toString().toFloat()
        val height : Float = etMetricHeight.text.toString().toFloat() /100
        val bmi = weight /(height * height)
        displayBMIResult(bmi)
    }

    private fun calculateUsMetric(){
        val weight : Float = etUsWeight.text.toString().toFloat()
        val feet : String = etUsHeightFeet.text.toString()
        val inch : String = etUsHeightInch.text.toString()
        val height : Float = (inch.toFloat() + (feet.toFloat() * 12))
        val bmi = 703 * (weight / (height * height))
        displayBMIResult(bmi)
    }

}
