package com.JavaBookstoreAdmin.service;

import com.JavaBookstoreAdmin.domain.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    //defining from code in bookcontroller (and work in bookserviceimpl)
    List<Book> findAll();

    Book findById(Long id);

}
