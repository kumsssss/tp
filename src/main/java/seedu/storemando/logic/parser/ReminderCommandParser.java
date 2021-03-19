package seedu.storemando.logic.parser;

import static seedu.storemando.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.storemando.logic.commands.ReminderCommand;
import seedu.storemando.logic.parser.exceptions.ParseException;
import seedu.storemando.model.item.ItemExpiringPredicate;

/**
 * Parses input arguments and creates a new ReminderCommand object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {
    /**
     * Convert the given number and the time unit to the number of days.
     * @param num The number use to covert to days
     * @param timeUnit The time unit in terms of days and weeks
     * @return The number of days
     * @throws ParseException if the user input does not conform the expected keyword
     */
    private long timeConversation(long num, String timeUnit) throws ParseException {
        String timeUnitLowerCase = timeUnit.toLowerCase();
        switch (timeUnitLowerCase) {
        case "days":
            return num;
        case "weeks":
            return num * 7;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ReminderCommand
     * and returns a ReminderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ReminderCommand parse(String args) throws ParseException {
        try {
            long numOfDayFromToday = 0;
            String trimmedArgs = args.trim();
            String[] stringArgsArr = trimmedArgs.split(" ");
            if (stringArgsArr.length == 1) {
                numOfDayFromToday = Long.parseLong(stringArgsArr[0]);
            } else if (stringArgsArr.length == 2) {
                Long timeValue = Long.parseLong(stringArgsArr[0]);
                String timeUnit = stringArgsArr[1];
                numOfDayFromToday = timeConversation(timeValue, timeUnit);
            }

            if (trimmedArgs.isEmpty() || stringArgsArr.length > 2 || numOfDayFromToday <= 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
            }

            return new ReminderCommand(new ItemExpiringPredicate(numOfDayFromToday));
        } catch (NumberFormatException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }

    }
}