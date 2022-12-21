package ie.wit.studentshare.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface StudentShareStore {
    fun findAllInstitutions(institutionsList: MutableLiveData<List<InstitutionModel>>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, letting: StudentShareModel)
}