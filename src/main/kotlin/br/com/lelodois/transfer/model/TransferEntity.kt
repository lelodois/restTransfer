package br.com.lelodois.transfer.model

import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
data class TransferEntity(val created: LocalDate = LocalDate.now()) {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Length(min = 6, max = 6)
    @NotEmpty
    var source: String? = null

    @Length(min = 6, max = 6)
    @NotEmpty
    var target: String? = null

    @NotNull
    var taxType: TransferTaxType? = null

    @NotNull
    var scheduled: LocalDate? = null

    @NotNull
    var totalValue: BigDecimal? = null

    @ApiModelProperty(hidden = true)
    fun isValidValue(): Boolean {
        return (totalValue!!.toDouble() > 0)
    }

}