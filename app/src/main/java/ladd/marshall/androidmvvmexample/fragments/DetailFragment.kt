package ladd.marshall.androidmvvmexample.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ladd.marshall.androidmvvmexample.R

class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
