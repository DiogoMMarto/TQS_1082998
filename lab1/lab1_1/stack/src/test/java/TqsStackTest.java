import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tqs.lab1.stack.TqsStack;

class TqsStackTest {

    TqsStack<String> wordstack;

    @BeforeEach
    void setup(){
        wordstack = new TqsStack<>();
    }

    @Test
    @DisplayName("A stack is empty on contruction.")
    void isEmpty() {
        assertTrue( wordstack.isEmpty() );
    }

    @Test
    @DisplayName("A stack has size 0 on construction.")
    void hasSizeZero() {
        assertTrue( wordstack.size() == 0 );
    }

    @Test
    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n.")
    void sizeNAfterNPushes() {
        int n = 10;
        for(int i = 0; i < n; i++)
            wordstack.push(String.valueOf(i));
        assertTrue( wordstack.size() == n );
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x.")
    void pushPop() {
        wordstack.push("Alpha");
        assertEquals("Alpha", wordstack.pop());
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
    void pushPeek() {
        wordstack.push("Alpha");
        int size = wordstack.size();
        assertTrue( wordstack.peek().equals("Alpha") && wordstack.size() == size );
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0.")
    void emptyAfterNPops() {
        int n = 10;
        for(int i = 0; i < n; i++)
            wordstack.push(String.valueOf(i));
        int size = wordstack.size();
        for(int i = 0; i < size; i++)
            wordstack.pop();
        assertTrue( wordstack.size() == 0 && wordstack.isEmpty() );
    }

    @Test
    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    void exceptionFromEmptyStackPop() {
        assertThrows(NoSuchElementException.class, () -> {wordstack.pop();});
    }

    @Test
    @DisplayName("Peeking from an empty stack does throw a NoSuchElementException")
    void exceptionFromEmptyStackPeek() {
        assertThrows(NoSuchElementException.class, () -> {wordstack.peek();});
    }

}