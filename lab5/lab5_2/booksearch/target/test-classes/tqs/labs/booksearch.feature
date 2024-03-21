Feature: Book Search
    Background: Book Library
        Given the following books
        |title                                    | author              | tag                            | year | publisher                     |
        |1984                                     | George Orwell       | Dystopian fiction              | 1949 | Secker & Warburg (UK)         |
        |To Kill a Mockingbird                    | Harper Lee          | Bildungsroman, Southern Gothic | 1960 | J.B. Lippincott & Co. (US)    |
        |The Catcher in the Rye                   | J.D. Salinger       | Bildungsroman                  | 1951 | Little, Brown and Company (US)|
        |The Great Gatsby                         | F. Scott Fitzgerald | Literary fiction, Jazz Age     | 1925 | Charles Scribner's Sons (US)  |
        |Harry Potter and the Philosopher's Stone | J.K. Rowling        | Fantasy, Young Adult           | 1997 | Bloomsbury (UK)               |

    Scenario: Search by author 
        When I search by author 'George Orwell'
        Then I get the Book with title '1984'

    Scenario: Search by tags 
        When I search by tag 'Bildungsroman'
        Then I get 2 books

    Scenario: Search by year
        When I search by year 1949
        Then I get the Book with title '1984'

    Scenario: Search by publisher
        When I search by publisher '(UK)'
        Then I get 2 books

    Scenario: Search by title
        When I search by title 'The'
        Then I get 2 books
        And one book is called 'The Catcher in the Rye'
        And one book is called 'The Great Gatsby'