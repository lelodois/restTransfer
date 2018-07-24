package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferTaxType
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
@Scope("request")
class TransferTaxNextsDays(val next: TransferTaxBetweenTenAndTwentyDays) :
        TransferTax(TransferTaxType.NEXTS_DAYS) {

    override fun doCalculateTax(transfer: TransferDto) {
        var tax = BigDecimal("12.00").multiply(BigDecimal.valueOf(super.getDaysDiff(transfer)))
        super.result = transfer.value?.add(tax)
    }

    override fun attend(transfer: TransferDto): Boolean = super.getDaysDiff(transfer) in 1..10

    override fun next(): TransferTax = next


}