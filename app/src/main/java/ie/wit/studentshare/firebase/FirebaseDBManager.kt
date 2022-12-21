package ie.wit.studentshare.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.studentshare.models.*
import timber.log.Timber

object FirebaseDBManager : StudentShareStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAllInstitutions (institutionsList: MutableLiveData<List<InstitutionModel>>) {
        database.child("institutions")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Institutions error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<InstitutionModel>()
                    val children = snapshot.children
                    children.forEach {
                        val institution = it.getValue(InstitutionModel::class.java)
                        localList.add(institution!!)
                    }
                    database.child("institutions")
                        .removeEventListener(this)

                    institutionsList.value = localList
                }
            })
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, letting: StudentShareModel) {
        println("CREATE DB MANGER")
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("lettings").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        letting.uid = key
        letting.userId = uid
        letting.favourites.add(uid)
        val lettingValues = letting.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/lettings/$key"] = lettingValues

        database.updateChildren(childAdd)
    }

}