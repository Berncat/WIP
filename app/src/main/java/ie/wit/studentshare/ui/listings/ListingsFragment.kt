package ie.wit.studentshare.ui.listings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ie.wit.studentshare.databinding.FragmentListingsBinding

class ListingsFragment : Fragment() {

    private var _binding: FragmentListingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listingsViewModel =
            ViewModelProvider(this).get(ListingsViewModel::class.java)

        _binding = FragmentListingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textListings
        listingsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}