package br.com.lelodois.transfer

import br.com.lelodois.transfer.except.TransferException
import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferEntity
import br.com.lelodois.transfer.model.TransferTaxType
import br.com.lelodois.transfer.service.tax.TransferConverter
import br.com.lelodois.transfer.service.tax.TransferService
import org.assertj.core.api.JUnitSoftAssertions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = arrayOf(RestTransferApplication::class))
@SpringBootTest
@Rollback
internal class TransferEntityTest() {

	@get:Rule
	var softly = JUnitSoftAssertions()

	@Autowired
	lateinit var service: TransferService

	@Autowired
	lateinit var converterService: TransferConverter

	@Test
	fun testNewEntity() {
		var item: TransferDto = getInstance(LocalDateTime.now().plusDays(1))

		softly.assertThat(item).isNotNull
		softly.assertThat(item.value).isEqualByComparingTo(BigDecimal("1500"))

		var saved: TransferEntity = service.save(item)

		softly.assertThat(saved).isNotNull
		softly.assertThat(saved.id).isNotNull
		softly.assertThat(saved.isValidValue()).isTrue
	}

	@Test
	fun testValueSameDay() {
		var item: TransferDto = getInstance(LocalDateTime.now())
		assert(item, TransferTaxType.SAME_DAY, BigDecimal("1548.09"))
	}

	@Test
	fun testValueNextFiveDay() {
		var item: TransferDto = getInstance(LocalDateTime.now().plusDays(5))
		assert(item, TransferTaxType.NEXTS_DAYS, BigDecimal("1560"))
	}

	@Test
	fun testValueTenDay() {
		var item: TransferDto = getInstance(LocalDateTime.now().plusDays(10))
		assert(item, TransferTaxType.NEXTS_DAYS, BigDecimal("1620"))
	}

	@Test
	fun testValueTwentyDay() {
		var item: TransferDto = getInstance(LocalDateTime.now().plusDays(20))
		assert(item, TransferTaxType.AFTER_TEN_DAYS, BigDecimal("1620"))
	}

	@Test
	fun testValueThirtyDay() {
		var item: TransferDto = getInstance(LocalDateTime.now().plusDays(30))
		assert(item, TransferTaxType.AFTER_TEN_DAYS, BigDecimal("1590"))
	}

	@Test
	fun testValueFortyDay() {
		var item: TransferDto = getInstance(LocalDateTime.now().plusDays(40))
		assert(item, TransferTaxType.AFTER_TEN_DAYS, BigDecimal("1560"))
	}

	@Test(expected = TransferException::class)
	fun testValueFiftyDay() {
		var item: TransferDto = getInstance(LocalDateTime.now().plusDays(50))
		converterService.convert(item)
	}

	@Test
	fun testValueFiftyDayExpensive() {
		var item: TransferDto = getInstance(LocalDateTime.now().plusDays(50), BigDecimal("100001"))
		assert(item, TransferTaxType.AFTER_TEN_DAYS, BigDecimal("102001.02"))
	}

	fun assert(item: TransferDto, expectedType: TransferTaxType, expectedValue: BigDecimal) {
		var entity = converterService.convert(item)
		softly.assertThat(entity.taxType).isEqualTo(expectedType)
		softly.assertThat(entity.totalValue).isEqualByComparingTo(expectedValue)
	}

	fun getInstance(scheduled: LocalDateTime, value: BigDecimal = BigDecimal("1500")): TransferDto {
		var item: TransferDto = TransferDto();
		item.source = "XP0921"
		item.target = "XP0029"
		item.scheduled = scheduled.toLocalDate()
		item.value = value
		return item;
	}
}
