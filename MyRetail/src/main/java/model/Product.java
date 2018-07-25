package model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Yasir Farhan
 */
public class Product {

    @NotEmpty(message = "You must supply a product id")
    private long id;
    private String name;
    private double current_price;
    private String currency_code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

}
