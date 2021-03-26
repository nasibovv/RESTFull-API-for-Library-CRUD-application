package libraryapp.helper;

import libraryapp.model.Author;
import libraryapp.model.AuthorBook;
import libraryapp.model.Book;
import libraryapp.model.Category;
import libraryapp.repository.AuthorBookRepository;
import libraryapp.repository.AuthorRepository;
import libraryapp.repository.BookRepository;
import libraryapp.repository.CategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import util.DateUtil;

import java.text.ParseException;

@Component
public class LibraryContent {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final AuthorBookRepository authorBookRepository;

    public LibraryContent(AuthorRepository authorRepository, CategoryRepository categoryRepository, BookRepository bookRepository, AuthorBookRepository authorBookRepository) {
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.authorBookRepository = authorBookRepository;
    }

    @Bean
    public void fillLibraryContent() throws ParseException {
        Author author1 = new Author();
        author1.setName("J.R.R.");
        author1.setSurname("Tolkien");
        author1 = authorRepository.save(author1);

        Category category1 = new Category();
        category1.setName("Sci-Fi");
        category1 = categoryRepository.save(category1);

        Book book1 = new Book();
        book1.setName("The Lord Of The Rings");
        book1.setCategory(category1);
        book1.setPublishDate(DateUtil.getBookPublishDate("July 29, 1954"));
        bookRepository.save(book1);

        AuthorBook authorBook1 = new AuthorBook();
        authorBook1.setAuthor(author1);
        authorBook1.setBook(book1);
        authorBookRepository.save(authorBook1);

        Author author2 = new Author();
        author2.setName("Robert Louis");
        author2.setSurname("Stevenson");
        author2 = authorRepository.save(author2);

        Category category2 = new Category();
        category2.setName("Adventure");
        category2 = categoryRepository.save(category2);

        Book book2 = new Book();
        book2.setName("Treasure Island");
        book2.setCategory(category2);
        book2.setPublishDate(DateUtil.getBookPublishDate("November 14, 1883"));
        bookRepository.save(book2);

        AuthorBook authorBook2 = new AuthorBook();
        authorBook2.setAuthor(author2);
        authorBook2.setBook(book2);
        authorBookRepository.save(authorBook2);

        Author author3 = new Author();
        author3.setName("Tehlor Kay");
        author3.setSurname("Mejia");
        author3 = authorRepository.save(author3);

        Author author4 = new Author();
        author4.setName("Anna-Marie");
        author4.setSurname("McLemore");
        author4 = authorRepository.save(author4);

        Category category3 = new Category();
        category3.setName("Fiction");
        category3 = categoryRepository.save(category3);

        Book book3 = new Book();
        book3.setName("Miss Meteor");
        book3.setCategory(category3);
        book3.setPublishDate(DateUtil.getBookPublishDate("September 22, 2020"));
        bookRepository.save(book3);

        AuthorBook authorBook3 = new AuthorBook();
        authorBook3.setAuthor(author3);
        authorBook3.setBook(book3);
        authorBookRepository.save(authorBook3);

        AuthorBook authorBook4 = new AuthorBook();
        authorBook4.setAuthor(author4);
        authorBook4.setBook(book3);
        authorBookRepository.save(authorBook4);
    }
}
