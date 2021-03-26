package libraryapp.dto.library;

import java.util.Date;
import java.util.List;

public class BookDTO {
    private Long id;
    private String name;
    private List<AuthorDTO> authors;
    private Date publishDate;
    private boolean isAvailable;
    private BookPickedUserDTO pickedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public BookPickedUserDTO getPickedUser() {
        return pickedUser;
    }

    public void setPickedUser(BookPickedUserDTO pickedUser) {
        this.pickedUser = pickedUser;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", publishDate=" + publishDate +
                ", isAvailable=" + isAvailable +
                ", pickedUser=" + pickedUser +
                '}';
    }
}
