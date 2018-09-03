package com.JavaBookstoreAdmin.service.impl;

;
import com.JavaBookstoreAdmin.domain.Book;
import com.JavaBookstoreAdmin.repository.BookRepository;
import com.JavaBookstoreAdmin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book save(Book book) {
		return bookRepository.save(book);
	}
}
