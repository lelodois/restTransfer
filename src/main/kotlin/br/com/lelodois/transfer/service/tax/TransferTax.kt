package br.com.lelodois.transfer.service.tax

import br.com.lelodois.transfer.except.TransferException
import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferTaxType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

public abstract class TransferTax(val type: TransferTaxType) {

	protected var result: BigDecimal? = null;

	public final fun calculateTax(transfer: TransferDto) : TransferTax{
		if (this.attend(transfer)) {
			this.doCalculateTax(transfer);
			return this;
		} else {
			return this.next().calculateTax(transfer);
		}
	}

	protected final fun getDaysDiff(transfer: TransferDto): Long {
		return ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate().atStartOfDay(), transfer.scheduled?.atStartOfDay());
	}

	protected abstract fun next(): TransferTax

	protected abstract fun attend(transfer: TransferDto): Boolean

	protected abstract fun doCalculateTax(transfer: TransferDto)

	public fun getResultValue(): BigDecimal? = result ?: throw TransferException("Result not found")

}