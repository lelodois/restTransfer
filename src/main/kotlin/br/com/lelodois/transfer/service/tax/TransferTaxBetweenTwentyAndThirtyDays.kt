package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferTaxType
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
@Scope("request")
public class TransferTaxBetweenTwentyAndThirtyDays(val next: TransferTaxBetweenThirtyAndFortyDays) :
		TransferTax(TransferTaxType.AFTER_TEN_DAYS) {

	override fun doCalculateTax(transfer: TransferDto) {
		var tax = transfer.value?.multiply(BigDecimal("0.06"))
		super.result = transfer.value?.add(tax)
	}

	override fun attend(transfer: TransferDto): Boolean = getDaysDiff(transfer) >= 21 && getDaysDiff(transfer) <= 30


	override fun next(): TransferTax = next

}