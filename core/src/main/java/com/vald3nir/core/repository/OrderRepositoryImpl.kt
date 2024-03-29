package com.vald3nir.core.repository

import com.vald3nir.core_repository.firebase.FirebaseAuthenticator
import com.vald3nir.core_repository.firebase.FirebaseClient
import java.util.*

class OrderRepositoryImpl : OrderRepository {

    private val firebaseClient = FirebaseClient()
    private val firebaseAuthenticator = FirebaseAuthenticator()

    override suspend fun requestOrder(
        orderDTO: com.vald3nir.core.repository.dtos.OrderDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {

        firebaseClient.loadCollection(
            rootPath = "debug",
            document = "usuários",
            collection = "usuários",
            type = com.vald3nir.core.repository.dtos.ClientDTO::class.java,
            onSuccess = { users ->
                val userID = firebaseAuthenticator.getUserID()

                orderDTO.client = users.single { client -> client.userID == userID }

                val cal = Calendar.getInstance()
                orderDTO.date =
                    "${cal.get(Calendar.DATE)}/${cal.get(Calendar.MONTH)}/${cal.get(Calendar.YEAR)} ${
                        cal.get(Calendar.HOUR)
                    }:${cal.get(Calendar.MINUTE)}"

                firebaseClient.insertOrUpdateData(
                    rootPath = "debug",
                    document = "orders",
                    collection = "orders",
                    baseDTO = orderDTO,
                    onSuccess,
                    onError
                )
            },
            onError
        )

    }
}