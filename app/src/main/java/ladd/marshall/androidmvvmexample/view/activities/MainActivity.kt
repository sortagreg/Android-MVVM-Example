package ladd.marshall.androidmvvmexample.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ladd.marshall.androidmvvmexample.R

/**
 * The Activity that is loaded immediately after the splash screen.
 *
 * It is responsible for coordinating the Navigation Component. A link is provided in the README
 * to explain how this component works and is built. Please refer to that for an explanation of
 * how to implement this.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
