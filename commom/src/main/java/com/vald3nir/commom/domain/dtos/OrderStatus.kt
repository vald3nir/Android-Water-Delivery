package com.vald3nir.commom.domain.dtos

enum class OrderStatus(val title: String) {
    OPEN("Aberto"), PROGRESS("Em andamento"), CLOSE("Encerrado")
}