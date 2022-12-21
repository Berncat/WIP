package ie.wit.studentshare.ui.add

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ie.wit.studentshare.R
import ie.wit.studentshare.databinding.FragmentAddBinding
import ie.wit.studentshare.models.StudentShareModel
import ie.wit.studentshare.ui.auth.LoggedInViewModel
import ie.wit.studentshare.ui.search.SearchFragmentDirections

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var addViewModel: AddViewModel
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        addViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })
        setUpAddButton(binding)
        setUpLocationButton(binding)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    Toast.makeText(context, "HEY HERE", Toast.LENGTH_LONG).show()
                }
            }
            false -> Toast.makeText(context, getString(R.string.donationError), Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpAddButton(layout: FragmentAddBinding) {
        val letting = StudentShareModel()
        layout.addButton.setOnClickListener {
            if (validateFields(layout)) {
                letting.street = layout.street.text.toString()
                letting.cost = layout.cost.text.toString()
                letting.details = layout.details.text.toString()
                letting.phone = layout.phone.text.toString()
                println(letting.toString())
                addViewModel.addLetting(loggedInViewModel.liveFirebaseUser, letting)
            }
        }
    }

    private fun setUpLocationButton(layout: FragmentAddBinding) {
        layout.locationButton.setOnClickListener {
            val action = AddFragmentDirections.actionNavAddToLocationFragment()
            findNavController().navigate(action)
        }
    }

    private fun validateFields(layout: FragmentAddBinding): Boolean {
        resetErrors(layout)
        var count = 0
        if (layout.street.length() == 0) {
            layout.streetBox.error = "Required field"
            count += 1
        }
        if (layout.cost.length() == 0) {
            layout.costBox.error = "Required field"
            count += 1
        }
        if (layout.details.length() == 0) {
            layout.detailsBox.error = "Required field"
            count += 1
        }
        if (layout.phone.length() == 0) {
            layout.phoneBox.error = "Required field"
            count += 1
        }
        if (count > 0) {
            return false
        }
        return true
    }

    private fun resetErrors(layout: FragmentAddBinding) {
        layout.streetBox.error = null
        layout.costBox.error = null
        layout.detailsBox.error = null
        layout.phoneBox.error = null
    }

}