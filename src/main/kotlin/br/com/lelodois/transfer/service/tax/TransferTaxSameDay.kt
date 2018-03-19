package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferTaxType
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
@Scope("request")
public class TransferTaxSameDay(val next: TransferTaxNextsDays) :
		TransferTax(TransferTaxType.SAME_DAY) {

	override fun doCalculateTax(transfer: TransferDto) {
		var transferValueWithTax: BigDecimal? = transfer.value?.add(BigDecimal("3.00"))
		super.result = transferValueWithTax?.add(transferValueWithTax.multiply(BigDecimal("0.03")))
	}

	override fun attend(transfer: TransferDto): Boolean = super.getDaysDiff(transfer) == 0L

	override fun next(): TransferTax = next

}