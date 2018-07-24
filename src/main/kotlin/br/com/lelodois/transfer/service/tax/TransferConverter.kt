package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("request")
class TransferConverter(@Autowired val first: TransferTaxSameDay) {

    fun convert(dto: TransferDto): TransferEntity {

        var taxRuleFound: TransferTax = first.calculateTax(dto)

        var entity = TransferEntity()
        entity.source = dto.source
        entity.target = dto.target
        entity.scheduled = dto.scheduled
        entity.taxType = taxRuleFound.type
        entity.totalValue = taxRuleFound.getResultValue()

        return entity
    }
}