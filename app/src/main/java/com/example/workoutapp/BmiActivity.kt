package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {
    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val IMPERIAL_UNITS_VIEW = "IMPERIAL_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView : String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmi)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.toolbarBmi?.setNavigationOnClickListener{
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener{_,
            checkedId:Int -> if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleImperialUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener{
            if(validateMetricUnits()){
                calculateBMIMetric()
                displayBMIResults(calculateBMIMetric())
            }else if(validateImperialUnits()){
                calculateBMIImperial()
                displayBMIResults(calculateBMIImperial())
            }
            else{
                Toast.makeText(this@BmiActivity, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricHeight?.visibility = View.VISIBLE
        binding?.tilMetricWeight?.visibility = View.VISIBLE
        binding?.tilImperialHeightFeet?.visibility = View.INVISIBLE
        binding?.tilImperialHeightInches?.visibility = View.INVISIBLE
        binding?.tilImperialWeight?.visibility = View.INVISIBLE

        binding?.etMetricHeight?.text!!.clear()
        binding?.etMetricWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }
    private fun makeVisibleImperialUnitsView(){
        currentVisibleView = IMPERIAL_UNITS_VIEW
        binding?.tilMetricHeight?.visibility = View.INVISIBLE
        binding?.tilMetricWeight?.visibility = View.INVISIBLE
        binding?.tilImperialHeightFeet?.visibility = View.VISIBLE
        binding?.tilImperialHeightInches?.visibility = View.VISIBLE
        binding?.tilImperialWeight?.visibility = View.VISIBLE

        binding?.etImperialHeightFeet?.text!!.clear()
        binding?.etImperialHeightInches?.text!!.clear()
        binding?.etImperialWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true
        if(binding?.etMetricWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etMetricHeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateImperialUnits(): Boolean{
        var isValid = true
        if(binding?.etImperialWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etImperialHeightFeet?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etImperialHeightInches?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }


    private fun calculateBMIMetric() : Float{
        var weight : Float = binding?.etMetricWeight?.text.toString().toFloat()
        var height : Float = binding?.etMetricHeight?.text.toString().toFloat()/100

        val bmi = weight / (height*height)

        return bmi
    }

    private fun calculateBMIImperial(): Float{
        var height : Float = binding?.etImperialHeightFeet?.text.toString().toFloat()*12 + binding?.etImperialHeightInches?.text.toString().toFloat()
        var weight : Float = binding?.etImperialWeight?.text.toString().toFloat()

        val bmi = 703 * weight / (height * height)

        return bmi
    }

    private fun displayBMIResults(bmi : Float){
        val bmiLabel : String
        val bmiDescription : String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(15f)> 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(16f)> 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(18.5f)> 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in very good shape!"
        }else if(bmi.compareTo(25f)> 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        }else if(bmi.compareTo(30f)> 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Obese Class I (Moderately Obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        }else if(bmi.compareTo(35f)> 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Obese Class II (Severely Obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }else{
            bmiLabel = "Obese Class III (Very Severely Obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE

        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription


    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}