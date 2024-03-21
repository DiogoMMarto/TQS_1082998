package tqs.labs;

import java.util.Arrays;
import java.util.HashSet;

public class Book {
    
    // title , author , publisher , tags , year 
    private String title;
    private String author;
    private String publisher;
    private HashSet<String> tags;
    private int year;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public HashSet<String> getTags() {
        return tags;
    }
    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public Book(String title, String author, String publisher, String tags, int year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.tags = new HashSet<>(Arrays.asList( tags.split(",") ));
        this.year = year;
    }
    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", publisher=" + publisher + ", tags=" + tags + ", year="
                + year + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + year;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        if (year != other.year)
            return false;
        return true;
    } 
    
    

}