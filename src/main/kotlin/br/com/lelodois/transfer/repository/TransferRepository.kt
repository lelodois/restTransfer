package br.com.lelodois.transfer.repository

import br.com.lelodois.transfer.model.TransferEntity
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional(Transactional.TxType.MANDATORY)
interface TransferRepository : CrudRepository<TransferEntity, Long> 