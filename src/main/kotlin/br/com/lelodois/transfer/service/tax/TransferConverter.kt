package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("request")
open class TransferConverter(@Autowired val first: TransferTaxSameDay) {

	public fun convert(dto: TransferDto): TransferEntity {

		var taxRuleFound : TransferTax = first.calculateTax(dto);

		return TransferEntity(
				source = dto.source,
				target = dto.target,
				scheduled = dto.scheduled,
				taxType = taxRuleFound.type,
				totalValue = taxRuleFound.getResultValue()
		)
	}
}