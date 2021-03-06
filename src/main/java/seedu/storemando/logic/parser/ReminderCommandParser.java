package seedu.storemando.logic.parser;

import static seedu.storemando.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.storemando.logic.commands.ReminderCommand;
import seedu.storemando.logic.parser.exceptions.ParseException;
import seedu.storemando.model.expirydate.predicate.ItemExpiringPredicate;

/**
 * Parses input arguments and creates a new ReminderCommand object.
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {

    public static final String DAY_KEYWORD = "day";
    public static final String WEEK_KEYWORD = "week";
    public static final String DAYS_KEYWORD = "days";
    public static final String WEEKS_KEYWORD = "weeks";
    public static final String NUMBER_VALIDATION_REGEX = "^(\\+|-)?\\d+$";

    /**
     * Parses the given {@code String} of arguments in the context of the ReminderCommand
     * and returns a ReminderCommand object for execution.
     *
     * @param args Parses the given {@code String} of arguments in the context of the ReminderCommand.
     * @return a ReminderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public ReminderCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
            }

            String[] wordsInTrimmedArgs = trimmedArgs.replaceAll("\\s{2,}", " ").split(" ");
            if (!isNumber(wordsInTrimmedArgs[0]) || wordsInTrimmedArgs.length > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
            }

            long parsedNum = Long.parseLong(wordsInTrimmedArgs[0]);
            if (parsedNum > 365 || parsedNum < -365) {
                throw new ParseException(ReminderCommand.MESSAGE_INCORRECT_INTEGER);
            }
            String timeUnit = wordsInTrimmedArgs[1];
            long numOfDaysFromToday = timeConversion(parsedNum, timeUnit);
            return new ReminderCommand(new ItemExpiringPredicate(numOfDaysFromToday), parsedNum, timeUnit);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        } catch (NumberFormatException ex) {
            throw new ParseException(ReminderCommand.MESSAGE_INCORRECT_INTEGER);
        }
    }

    /**
     * Converts the given number and the time unit to the number of days.
     *
     * @param parsedNum The number that is use to covert to days.
     * @param timeUnit  The time unit in terms of days and weeks.
     * @return the number of days.
     * @throws ParseException if the user input does not conform the expected keyword.
     */
    private long timeConversion(Long parsedNum, String timeUnit) throws ParseException {
        if (isSingular(parsedNum)) {
            return singularTimeUnitConversion(parsedNum, timeUnit);
        } else {
            return pluralTimeUnitConversion(parsedNum, timeUnit);
        }
    }

    /**
     * Checks if the given number is singular.
     *
     * @param number The number that is given.
     * @return A boolean that says if the number is singular.
     */
    private boolean isSingular(Long number) {
        return number >= -1 && number <= 1;
    }

    /**
     * Converts number into proper number of days based on the keyword given.
     *
     * @param parsedNum The number that is use to covert to days.
     * @param timeUnit  The time unit in terms of days and weeks.
     * @return the number of days.
     * @throws ParseException if the user input does not conform the expected keyword.
     */
    private long singularTimeUnitConversion(Long parsedNum, String timeUnit) throws ParseException {
        if (timeUnit.equalsIgnoreCase(DAY_KEYWORD) || timeUnit.equalsIgnoreCase(DAYS_KEYWORD)) {
            return parsedNum;
        } else if (timeUnit.equalsIgnoreCase(WEEK_KEYWORD) || timeUnit.equalsIgnoreCase(WEEKS_KEYWORD)) {
            return parsedNum * 7;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Converts number into proper number of days based on the keyword given.
     *
     * @param parsedNum The number that is use to covert to days.
     * @param timeUnit  The time unit in terms of days and weeks.
     * @return the number of days.
     * @throws ParseException if the user input does not conform the expected keyword.
     */
    private long pluralTimeUnitConversion(Long parsedNum, String timeUnit) throws ParseException {
        if (timeUnit.equalsIgnoreCase(DAYS_KEYWORD)) {
            return parsedNum;
        } else if (timeUnit.equalsIgnoreCase(WEEKS_KEYWORD)) {
            return parsedNum * 7;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if the input provided is an integer.
     *
     * @param input The string provided by the user.
     * @return true if the input is an integer, false otherwise.
     */
    private boolean isNumber(String input) {
        return input.matches(NUMBER_VALIDATION_REGEX);
    }
}
