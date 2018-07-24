package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.except.TransferException
import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferTaxType
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
@Scope("request")
class TransferTaxAfterFortyDays(val next: TransferTaxNotFound) :
		TransferTax(TransferTaxType.AFTER_TEN_DAYS) {

	override fun doCalculateTax(transfer: TransferDto) {
		var transferValue = transfer.value ?: throw TransferException("nullable value")

		transferValue = transferValue.multiply(BigDecimal("0.02"))
		super.result = transfer.value?.add(transferValue)
	}

	override fun attend(transfer: TransferDto): Boolean {
		var transferValue = transfer.value ?: throw TransferException("nullable value")
		return (super.getDaysDiff(transfer) >= 41 && transferValue.compareTo(BigDecimal("100000.00")) > 0)
	}

	override fun next(): TransferTax = next

}