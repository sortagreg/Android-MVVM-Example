package ladd.marshall.androidmvvmexample.utils

import com.google.android.material.textfield.TextInputEditText

/**
 * Extension functions allow us to add functions to Classes that we otherwise would not have access
 * to. More information can be found in Kotlin's official documentation.
 */

fun TextInputEditText.toInt(): Int {
    return when {
        this.text.toString().isBlank() -> 0
        else -> this.text.toString().toInt()
    }
}
