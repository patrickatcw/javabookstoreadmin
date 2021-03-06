package com.JavaBookstoreAdmin.controller;

import com.JavaBookstoreAdmin.domain.Book;
import com.JavaBookstoreAdmin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
            return "addBook";

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBookPost
            (@ModelAttribute("book") Book book, HttpServletRequest request) {

        bookService.save(book); //saves in database to get id

        MultipartFile bookImage = book.getBookImage();

        try {
            byte[] bytes = bookImage.getBytes();
            String name = book.getId() + ".png";
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:bookList";   /*need to add bookList*/

    }

    @RequestMapping("/bookInfo")
    public String bookInfo(@RequestParam("id") Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);

        return "bookInfo";
    }

    @RequestMapping("/editBookInfo")
    public String updateBook(@RequestParam("id") Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);

        return "editBookInfo";
    }

    @RequestMapping(value="/editBookInfo", method=RequestMethod.POST)
    public String updateBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {
        bookService.save(book);

        MultipartFile bookImage = book.getBookImage();

        if(!bookImage.isEmpty()) {
            try {
                byte[] bytes = bookImage.getBytes();
                String name = book.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/book/"+name));

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/book/bookInfo?id="+book.getId();
    }


    @RequestMapping("/bookList")
    public String bookList(Model model) {

        /*List<Book> bookList = bookService.findAll();*/  //have to define this later
        List<Book> bookList = bookService.findAll();    //define findall in bookService
        //add to model to be able to retrieve
        model.addAttribute("bookList", bookList);

        return "bookList";

    }

}

