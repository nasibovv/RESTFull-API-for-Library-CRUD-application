package libraryapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BOOK")
public class Book extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    @JsonBackReference("book-category")
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @JsonManagedReference("book-author")
    private List<AuthorBook> authors;

    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @JsonManagedReference("book-library-status")
    private List<PickDrop> pickDrops;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<AuthorBook> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorBook> authors) {
        this.authors = authors;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public List<PickDrop> getPickDrops() {
        return pickDrops;
    }

    public void setPickDrops(List<PickDrop> pickDrops) {
        this.pickDrops = pickDrops;
    }
}
