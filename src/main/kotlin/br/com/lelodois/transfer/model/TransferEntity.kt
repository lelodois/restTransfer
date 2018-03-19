package br.com.lelodois.transfer.model

import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
public data class TransferEntity(

		@Id
		@GeneratedValue
		val id: Long? = null,

		@Length(min = 6, max = 6)
		@NotEmpty
		val source: String?,

		@Length(min = 6, max = 6)
		@NotEmpty
		val target: String?,

		@NotNull
		@Enumerated(value = EnumType.STRING)
		val taxType: TransferTaxType? = null,

		val created: LocalDate = LocalDate.now(),

		@NotNull
		val scheduled: LocalDate?,

		@NotNull
		val totalValue: BigDecimal?) {

	@ApiModelProperty(hidden = true)
	fun isValidValue(): Boolean {
		return (totalValue!!.toDouble() > 0)
	}

	override fun equals(other: Any?): Boolean {
		return other === this || (other is TransferEntity && other.id === this.id)
	}

}