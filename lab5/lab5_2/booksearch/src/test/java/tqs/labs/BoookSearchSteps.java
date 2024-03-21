package tqs.labs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BoookSearchSteps {
    private Library library;

    @DataTableType
    public Book bookEntry(Map<String,String> entry){
        return new Book(entry.get("title"), entry.get("author"), entry.get("publisher"), entry.get("tag"), Integer.parseInt(entry.get("year")));
    }
    @Given("the following books")
    public void setup(List<Book> books) {
        library = new Library(books);
    }

    @When("I search by author {string}")
    public void I_search_by_author(String s) {
        library.booksByAuthor(s);;
    }

    @When("I search by tag {string}")
    public void searchByTag(String tags){
        library.booksByTag(tags);
    }

    @Then("I get {int} books")
    public void size_response(int expected){
        assertEquals(expected, library.response().size());
    }

    @Then("I get the Book with title {string}")
    public void I_get_the_Book_with_title(String s) {
        assertTrue(library.response().size()>0);
        assertEquals(s, library.response().get(0).getTitle());
    }

    @When("I search by year {int}")
    public void I_search_by_year(int i) {
        library.booksByYear(i);
    }

    @When("I search by publisher {string}")
    public void I_search_by_publisher(String s) {
        library.booksByPublisher(s);
    }

    @When("I search by title {string}")
    public void I_search_by_title(String s) {
        library.booksByTitle(s);
    }

    @Then("one book is called {string}")
    public void one_book_is_called(String s) {
        assertTrue(
            library.response().stream()
            .map(Book::getTitle)
            .filter(
                name -> name.equals(s)
            )
            .count() > 0
        );
    }

}


