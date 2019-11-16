package ladd.marshall.androidmvvmexample.utils

import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.toInt(): Int {
    return when {
        this.text.toString().isBlank() -> 0
        else -> this.text.toString().toInt()
    }
}
