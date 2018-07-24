package br.com.lelodois.transfer.model

import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class TransferDto {

    @Length(min = 6, max = 6)
    @NotEmpty
    @ApiModelProperty(example = "XP3124")
    var source: String? = null

    @Length(min = 6, max = 6)
    @NotEmpty
    @ApiModelProperty(example = "XP3121")
    var target: String? = null

    @NotNull
    var scheduled: LocalDate? = null

    @NotNull
    @ApiModelProperty(example = "1400.20")
    var value: BigDecimal? = null
}
