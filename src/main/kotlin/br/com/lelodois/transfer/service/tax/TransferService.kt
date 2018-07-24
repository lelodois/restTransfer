package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferEntity
import br.com.lelodois.transfer.repository.TransferRepository
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class TransferService(val repo: TransferRepository, var context: ApplicationContext) {

    fun all(): MutableIterable<TransferEntity>? {
        return repo.findAll()
    }

    fun retrieve(id: Long): TransferEntity? {
        return repo.findById(id).get()
    }

    fun save(dto: TransferDto): TransferEntity {
        var converter: TransferConverter = context.getBean("transferConverter") as TransferConverter
        return repo.save(converter.convert(dto))
    }
}