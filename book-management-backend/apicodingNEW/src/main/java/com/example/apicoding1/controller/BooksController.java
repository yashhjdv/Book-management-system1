package com.example.apicoding1.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apicoding1.entity.books;
import com.example.apicoding1.repo.BooksRepository;

@RestController
@RequestMapping("/books")
@SecurityRequirement(name = "bearerAuth")
public class BooksController {
	
	@Autowired
	private BooksRepository brepo;
	
	@PostMapping()
	public books addbooks(@RequestBody books book) {
		return brepo.save(book);
	}
	
	@GetMapping("/all")
	public List<books> getallbooks() {
		return brepo.findAll();
	}
	
	@GetMapping("/{isbn}")
	public ResponseEntity<books> getbyid(@PathVariable int isbn){
		Optional<books> bk = brepo.findById(isbn);
		if (bk.isPresent()) {
	        return ResponseEntity.ok(bk.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }	
	}
	
	
	@PutMapping("/{isbn}")
	public ResponseEntity<books> updatebooks(@PathVariable int isbn,@RequestBody books ubook){
		Optional<books> bookOpt = brepo.findById(isbn);
		if(bookOpt.isPresent()) {
			books bk = bookOpt.get();
			bk.setTitle(ubook.getTitle());
			bk.setAuthor(ubook.getAuthor());
			bk.setPublicationYear(ubook.getPublicationYear());
			return ResponseEntity.ok(brepo.save(bk));
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/{isbn}")
	public  ResponseEntity<?> deletebooks(@PathVariable int isbn){
		if(brepo.existsById(isbn)) {
			brepo.deleteById(isbn);
			return ResponseEntity.ok("book deleted successfully");
		}
		else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	

	
	
	
}


