package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);

    private BookService bookService;

    public BookShelfController bookShelfController(BookService bookService) {
        this.bookService = bookSevice;
    }

    @GetMapping(value = "/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks())
        return "book_shelf";
    }
    @PostMapping(value = "save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current size of repository: " + bookService.getAllBooks().size());
        return "redirect:/shelf";
    }
}
