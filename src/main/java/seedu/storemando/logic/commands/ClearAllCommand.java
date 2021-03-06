package seedu.storemando.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.storemando.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;
import java.util.function.Predicate;

import seedu.storemando.logic.commands.exceptions.CommandException;
import seedu.storemando.model.Model;
import seedu.storemando.model.item.Item;

/**
 * Clears the entire storemando inventory.
 */
public class ClearAllCommand extends ClearCommand {

    public static final String CLEAR_MESSAGE_SUCCESS = "All items in the inventory are cleared!";

    /**
     * The predicate that clear command uses to filter the items.
     */
    private final Predicate<Item> predicate;

    public ClearAllCommand() {
        this.predicate = PREDICATE_SHOW_ALL_ITEMS;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Item> currentList = model.getItemList();
        if (currentList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_ITEMS_IN_STOREMANDO);
        }
        model.clearLocation(predicate);
        return new CommandResult(CLEAR_MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof ClearAllCommand; // instanceof handles nulls
    }

}
