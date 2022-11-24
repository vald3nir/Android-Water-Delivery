package com.vald3nir.diskwater.domain.dtos

enum class OrderStatus(val title: String) {
    OPEN("Aberto"), PROGRESS("Em andamento"), CLOSE("Encerrado")
}