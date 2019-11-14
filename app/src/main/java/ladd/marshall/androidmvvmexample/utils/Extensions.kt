package ladd.marshall.androidmvvmexample.utils

import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.toInt(): Int {
    return this.text.toString().toInt()
}
