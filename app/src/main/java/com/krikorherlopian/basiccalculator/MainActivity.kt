package com.krikorherlopian.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.krikorherlopian.arithmeticoperations.ArithmeticOperations
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isEmpty: Boolean = true
    var resultVisible: Boolean =
        false // whether its final result in textview or not, so in case it is the result after = was clicked . User clicks digit it starts from scratch and doesnt append.
    var lastDot: Boolean = false // to avoid 0.2.23 scenario, one dot only it should be.
    var lastOperator: Boolean =
        false //to avoid duplicate of operator.Like 4++ should not be allowed after 4+.
    var isRadian: Boolean = true
    var isSinCosTan: Boolean = false
    var operand1: String = ""
    var operand2: String = ""
    var operator: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        //if empty start by number, else append to it.
        // If result of an operation is displayed, and new number clicked = Start a new operation.Clear old operation.
        if (isEmpty || resultVisible) {
            txtInput.text = (view as Button).text
            isEmpty = false
            lastDot = false
            lastOperator = false
            resultVisible = false
        } else {
            isEmpty = false
            txtInput.append((view as Button).text)
            lastOperator = false
        }

    }


    fun onDecimalPoint(view: View) {
        //lastdot to make sure 2 dots not in same number like 3.445.333
        if (!isEmpty && !lastDot) {
            txtInput.append((view as Button).text)
            lastDot = true
            lastOperator = false

        } else if (isEmpty) {//if empty, user can start writing a dot like .2 which means 0.2.
            txtInput.text = (view as Button).text
            isEmpty = false
            lastDot = true
            lastOperator = false
            resultVisible = false
        }
    }


    fun onOperator(view: View) {
        if (!lastOperator && !isSinCosTan) {
            var newOperator = (view as Button).text
            //if empty, and nothing entered yet. User can enter a + or  - number.But not multiply or divide.
            if (isEmpty && (newOperator.equals("+") || newOperator.equals("-")))
                txtInput.text = (view as Button).text
            else if (!isEmpty) {
                if (operand1 == "") {
                    operand1 = "" + txtInput.text
                    operator = "" + newOperator
                    txtInput.append((view as Button).text)
                } else {
                    //if previous operation is there, calculate it. For Ex. 2*2, user clicks + button. We want to transform to 4+.
                    //4 is operand 1 now, and operator is +.
                    val parts = txtInput.text.split("" + operator)
                    operand2 = parts[parts.lastIndex]
                    var result = ArithmeticOperations.binaryOperation(
                        operand1.toDouble(),
                        operand2.toDouble(),
                        operator
                    )
                    operand1 = "" + result
                    operator = "" + (view as Button).text
                    operand2 = ""
                    txtInput.text = operand1 + "" + operator
                }
            }
            resultVisible = false
            lastDot = false
            lastOperator = true
            isEmpty = false
        }
    }


    fun onClear(view: View) {
        this.txtInput.text = ""
        isEmpty = true
        lastOperator = false
        lastDot = false
        resultVisible = false
        isSinCosTan = false
        operand1 = ""
        operator = ""
        operand2 = ""
    }

    fun onSinCosTan(view: View) {
        onClear(view)
        isSinCosTan = true
        isEmpty = false
        this.txtInput.text = "" + (view as Button).text + " "
    }


    //when user clicks on Rad/Deg button, and switches between evaluating trignometric functions in Radian or Degree.
    fun changeMode(view: View) {
        isRadian = !isRadian
        when (isRadian) {
            true -> (view as Button).text = resources.getString(R.string.rad)
            false -> (view as Button).text = resources.getString(R.string.deg)
        }
    }

    //when equal button clicked, if sin cos tan entered it evaluates the trigonometric operation.
    //else it evaluates the basic arithmetic operation.
    fun onEqual(view: View) {
        if (isSinCosTan) {
            val parts = txtInput.text.split(" ")
            if (parts.size > 1 && parts[1] != "") {
                var result: Double?
                var value = parts[1]
                if (!isRadian)
                    result = ArithmeticOperations.trigonometricOperationDegree(
                        value.toDouble(),
                        parts[0]
                    )
                else
                    result = ArithmeticOperations.trigonometricOperationRadian(
                        value.toDouble(),
                        parts[0]
                    )

                showResult(result)
            }
        } else if (operand1 != "" && operator != "") {
            val parts = txtInput.text.split("" + operator)
            operand2 = parts[parts.lastIndex]
            var result = ArithmeticOperations.binaryOperation(
                operand1.toDouble(),
                operand2.toDouble(),
                operator
            )
            showResult(result)
        }
    }

    fun showResult(result: Double) {
        txtInput.text = "" + result
        operand1 = ""
        operator = ""
        operand2 = ""
        resultVisible = true
        lastDot = true
        lastOperator = false
        isSinCosTan = false
    }
}
