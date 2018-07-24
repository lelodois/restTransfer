package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.except.TransferException
import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferTaxType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

abstract class TransferTax(val type: TransferTaxType) {

    protected var result: BigDecimal? = null

    fun calculateTax(transfer: TransferDto): TransferTax {
        return if (this.attend(transfer)) {
            this.doCalculateTax(transfer)
            this
        } else {
            this.next().calculateTax(transfer)
        }
    }

    protected fun getDaysDiff(transfer: TransferDto): Long {
        return ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate().atStartOfDay(), transfer.scheduled?.atStartOfDay());
    }

    protected abstract fun next(): TransferTax

    protected abstract fun attend(transfer: TransferDto): Boolean

    protected abstract fun doCalculateTax(transfer: TransferDto)

    fun getResultValue(): BigDecimal? = result ?: throw TransferException("Result not found")

}