package br.com.lelodois.transfer.controller

import br.com.lelodois.transfer.model.TransferDto
import br.com.lelodois.transfer.model.TransferEntity
import br.com.lelodois.transfer.service.tax.TransferService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/transfers")
class TransferController(val service: TransferService) {

	@GetMapping("/")
	fun findAll() = service.all()

	@GetMapping("/get/{id}")
	fun getOne(@PathVariable(value = "id") id: Long): ResponseEntity<TransferEntity> =
			ResponseEntity.ok(service.retrieve(id));

	@PostMapping("/new")
	fun neww(@Valid @RequestBody dto: TransferDto): ResponseEntity<TransferEntity> {
		return ResponseEntity.ok(service.save(dto));
	}

}