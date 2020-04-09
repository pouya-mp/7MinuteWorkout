package com.pouyaa.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private val metricView = "Metric View"
    private val usView = "US view"
    private var currentUnitView = metricView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)
        setSupportActionBar(toolbarBMIActivity)

        val actionbar = supportActionBar

        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "CALCULATE BMI"
        }
        toolbarBMIActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateBMI.setOnClickListener {

            if (currentUnitView == metricView){

                if (validateMetricUnits()){
                    calculateBMIMetric()
                }else{
                    Toast.makeText(this,"Please enter valid values",Toast.LENGTH_SHORT).show()
                }

            }else {
                if (validateUsUnits()){
                    calculateUsMetric()
                }else{
                    Toast.makeText(this,"Please enter valid values",Toast.LENGTH_SHORT).show()
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
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llBMIResult.visibility = View.VISIBLE

//        tvYourBMI.visibility = View.VISIBLE
//        tvBMIValue.visibility = View.VISIBLE
//        tvBMIType.visibility = View.VISIBLE
//        tvBMIDescription.visibility = View.VISIBLE


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
            etusWeight.text.toString().isEmpty() -> { isValid = false }
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

        llBMIResult.visibility = View.GONE

    }

    private fun makeVisibleUsUnitView(){
        currentUnitView = usView

        etusWeight.text!!.clear()
        etUsHeightFeet.text!!.clear()
        etUsHeightInch.text!!.clear()

        tilMetricHeight.visibility = View.GONE
        tilMetricWeight.visibility = View.GONE

        tilUSWeight.visibility = View.VISIBLE
        llUsHeight.visibility = View.VISIBLE

        llBMIResult.visibility = View.GONE

    }

    private fun calculateBMIMetric(){
        val weight : Float = etMetricWeight.text.toString().toFloat()
        val height : Float = etMetricHeight.text.toString().toFloat() /100
        val bmi = weight /(height * height)
        displayBMIResult(bmi)
    }

    private fun calculateUsMetric(){
        val weight : Float = etusWeight.text.toString().toFloat()
        val feet : String = etUsHeightFeet.text.toString()
        val inch : String = etUsHeightInch.text.toString()
        val height : Float = (inch.toFloat() + (feet.toFloat() * 12))
        val bmi = 703 * (weight / (height * height))
        displayBMIResult(bmi)
    }

}
