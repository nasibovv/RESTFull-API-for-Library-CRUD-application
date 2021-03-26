package libraryapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import libraryapp.constant.BookLibraryStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PICKUP_DROPOFF")
public class PickDrop extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonManagedReference("book-library-status")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference("user-library-status")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private BookLibraryStatus status;

    @Temporal(TemporalType.DATE)
    private Date pickUpDate;

    @Temporal(TemporalType.DATE)
    private Date dropOffDate;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookLibraryStatus getStatus() {
        return status;
    }

    public void setStatus(BookLibraryStatus status) {
        this.status = status;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }
}
