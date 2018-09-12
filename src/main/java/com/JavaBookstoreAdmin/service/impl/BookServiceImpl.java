package com.JavaBookstoreAdmin.service.impl;


import com.JavaBookstoreAdmin.domain.Book;
import com.JavaBookstoreAdmin.repository.BookRepository;
import com.JavaBookstoreAdmin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;

	@Override
    public Book save(Book book) {
		return bookRepository.save(book);

    }

    //for booklist
    public List<Book> findAll() {

		return (List<Book>) bookRepository.findAll();

	}

}
