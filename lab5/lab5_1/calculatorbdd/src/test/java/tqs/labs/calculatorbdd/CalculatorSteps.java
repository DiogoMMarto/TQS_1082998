package tqs.labs.calculatorbdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {
    private Calculator calc;
    private boolean errorOccured;
    
    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
        errorOccured = false;
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I substract {int} to {int}")
    public void substract(int arg1, int arg2) {
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }

    @When("I multiply {int} and {int}")
    public void I_multiply_and(int i, int i2) {
        calc.push(i);
        calc.push(i2);
        calc.push("*");
    }

    @Then("the result is {int}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        assertEquals(expected, value);
    }

    @When("I do an invalid operation")
    public void I_do_an_invalid_operation() {
        try{
            calc.push("+");
        } catch (Exception e){
            errorOccured = true;
        }
    }

    @Then("i get an error")
    public void i_get_an_error() {
        assertTrue(errorOccured);
    }

}
