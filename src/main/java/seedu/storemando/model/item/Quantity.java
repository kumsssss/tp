package seedu.storemando.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.storemando.commons.util.AppUtil.checkArgument;

/**
 * Represents a Item's quantity number in the storemando.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 * >>>>>>> mid-1.2-base-refactor:src/main/java/seedu/storemando/model/item/Quantity.java
 */
public class Quantity {


    public static final String MESSAGE_CONSTRAINTS =
        "Quantity numbers should only contain numbers, and it should be at least 1 digit long";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity number.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        value = quantity;
    }

    /**
     * Returns true if a given string is a valid quantity number.
     */
    public static boolean isValidQuantity(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            long value = Long.parseLong(test);
            if (value > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Quantity // instanceof handles nulls
            && value.equals(((Quantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}