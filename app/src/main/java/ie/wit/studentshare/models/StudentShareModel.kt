package ie.wit.studentshare.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentShareModel(
    var uid: String? = "",
    var street: String = "",
    var cost: String = "",
    var details: String = "",
    var phone: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var favourites: MutableList<String?> = emptyList<String?>().toMutableList(),
    var userId: String? = "",
) : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "street" to street,
            "cost" to cost,
            "details" to details,
            "phone" to phone,
            "lat" to lat,
            "lng" to lng,
            "favourites" to favourites,
            "userId" to userId
        )
    }
}

@Parcelize
data class Coordinates(
    var title: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var radius: Double = 0.0
) : Parcelable

@Parcelize
data class InstitutionModel(
    var title: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0
) : Parcelable {
    override fun toString(): String {
        return title
    }
}
