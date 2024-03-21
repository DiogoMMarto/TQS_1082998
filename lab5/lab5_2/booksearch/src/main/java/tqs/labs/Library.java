package tqs.labs;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Library {

    List<Book> books;
    List<Book> lastSearch;

    public Library(List<Book> books) {
        this.books = books;
    }

    public List<Book> response(){
        return lastSearch;
    }

    public void booksByTitle(String title) {
        lastSearch = books.stream()
          .filter(book -> book.getTitle().contains(title))
          .collect(Collectors.toList());
    }

    public void booksByAuthor(String author) {
        lastSearch = books.stream()
          .filter(book -> book.getAuthor().contains(author))
          .collect(Collectors.toList());
    }

    public void booksByPublisher(String publisher) {
        lastSearch = books.stream()
          .filter(book -> book.getPublisher().contains(publisher))
          .collect(Collectors.toList());
    }

    public void booksByTag(String tag) {
        lastSearch = books.stream()
          .filter(book -> book.getTags().contains(tag))
          .collect(Collectors.toList());
    }

    public void booksByYear(int year) {
        lastSearch = books.stream()
          .filter(book -> Objects.equals(year, book.getYear()))
          .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Library [books=" + books + "]";
    }
        
}
