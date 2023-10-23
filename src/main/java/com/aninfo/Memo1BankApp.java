package com.aninfo;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}

	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);

		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		accountService.deleteById(cbu);
	}

	@PutMapping("/accounts/{cbu}/withdraw")
	public Transaction withdraw(@PathVariable Long cbu, @RequestParam Double sum) {
		Transaction transaction = new Transaction('W', cbu, sum);
		return transactionService.createTransaction(transaction);
	}

	@PutMapping("/accounts/{cbu}/deposit")
	public Transaction deposit(@PathVariable Long cbu, @RequestParam Double sum) {
		Transaction transaction = new Transaction('D', cbu, sum);
		return transactionService.createTransaction(transaction);
	}



	@GetMapping("/transactions/cbu/{cbuAccount}")
	public Collection<Transaction>  getTransactionByCbuAccount(@PathVariable Long cbuAccount) {
		return transactionService.findByCbuAccount(cbuAccount);
	}

	@GetMapping("/transactions/{idTransaction}")
	public ResponseEntity<Transaction> getTransaction(@PathVariable Long idTransaction) {
		Optional<Transaction> transactionOptional = transactionService.findByIdTransaction(idTransaction);
		return ResponseEntity.of(transactionOptional);
	}

	@DeleteMapping("/transactions/{idTransaction}")
	public void deleteTransaction(@PathVariable Long idTransaction) {
		transactionService.deleteByIdTransaction(idTransaction);
	}
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
