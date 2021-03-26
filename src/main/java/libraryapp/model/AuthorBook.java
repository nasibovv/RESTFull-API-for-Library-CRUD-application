package libraryapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHOR_BOOK")
public class AuthorBook extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonManagedReference("author-book")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonManagedReference("book-author")
    private Book book;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
