package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.except.TransferException
import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferTaxType
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("request")
class TransferTaxNotFound() :
		TransferTax(TransferTaxType.NONE) {

	override fun doCalculateTax(transfer: TransferDto) {
		throw TransferException("Implemented not found")
	}

	override fun attend(transfer: TransferDto): Boolean {
		throw TransferException("Implementation not found value: " + transfer.value + " - date: " + transfer.scheduled)
	}

	override fun next(): TransferTax {
		throw TransferException("Implemented not found")
	}

}