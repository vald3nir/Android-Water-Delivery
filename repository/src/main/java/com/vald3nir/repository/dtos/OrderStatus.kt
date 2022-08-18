package com.vald3nir.repository.dtos

enum class OrderStatus(val title: String) {
    OPEN("Aberto"), PROGRESS("Em andamento"), CLOSE("Encerrado")
}