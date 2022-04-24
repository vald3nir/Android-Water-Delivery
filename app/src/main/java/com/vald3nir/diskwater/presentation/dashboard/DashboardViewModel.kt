package com.vald3nir.diskwater.presentation.dashboard

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.componets.CustomSheetDialog
import com.vald3nir.diskwater.common.core.BaseViewModel
import com.vald3nir.diskwater.common.utils.MAX_VALUE_POWER_CONSUMPTION
import com.vald3nir.diskwater.common.utils.getColorPowerValue
import com.vald3nir.diskwater.data.dto.ConsumptionRealTimeDTO
import com.vald3nir.diskwater.data.dto.ItemChartRealTimeDTO
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import com.vald3nir.diskwater.domain.use_cases.consumption.ConsumptionUseCase
import kotlinx.coroutines.launch
import kotlin.math.abs

class DashboardViewModel(
    private val screenNavigation: ScreenNavigation,
    private val consumptionUseCase: ConsumptionUseCase,
    private val authUseCase: AuthUseCase,
) : BaseViewModel() {

    private val _consumptionRealTimeDTO = MutableLiveData<ConsumptionRealTimeDTO>()
    val consumptionRealTimeDTO: LiveData<ConsumptionRealTimeDTO> = _consumptionRealTimeDTO

    fun subscriberConsumptionRealTime() {
        viewModelScope.launch {
            viewModelScope.launch {
                consumptionUseCase.subscriberConsumptionRealTime {
                    _consumptionRealTimeDTO.postValue(it)
                }
            }
        }
    }



    fun testFirebase() {
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents -> ${exception.message}")
            }


//        val database = Firebase.database
//        val collection = database
//            .getReference("consumption")
//            .child("a32ab0af-970c-11ec-9779-a463a116a9e2")
//
//        // Read from the database
//        collection.addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val gson = Gson()
//                val dataObj = JSONObject(gson.toJson(snapshot.value))
//
//                val firebaseID = dataObj.keys().next()
//                val consumptions =
//                    (dataObj.get(firebaseID) as JSONArray).toListDeviceConsumptionDTO()
//                print(consumptions)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                print(error)
//            }
//        })
    }

    private fun insertData() {
        val db = Firebase.firestore
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )
        db.collection("users").add(user)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }
    }


    fun getMenuItems(): List<CustomSheetDialog.CustomItemSheet> {
        return listOf(
            CustomSheetDialog.CustomItemSheet(
                icon = R.drawable.ic_exit,
                title = getString(R.string.disconnect),
                action = { disconnectUser() }
            ),
        )
    }

    private fun disconnectUser() {
        viewModelScope.launch {
            authUseCase.disconnect()
            screenNavigation.redirectToLogin(view)
        }
    }
}