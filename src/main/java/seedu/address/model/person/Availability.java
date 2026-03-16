package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Person's availability in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvailability(String)}
 */
public class Availability {

    public static final String MESSAGE_CONSTRAINTS = "Availability should be in the format "
            + "day:start-end separated by semicolons with no spaces.\n"
            + "Example: mon:0900-1000,1200-1300;tue:1000-1100;wed:1100-1200\n"
            + "- Days must be a 3-letter abbreviation (mon, tue, wed, thu, fri, sat, sun).\n"
            + "- Days should not repeat.\n"
            + "- Times must be in valid 24-hour format (0000-2359).\n"
            + "- Multiple timings in a day must be separated by commas\n"
            + "- Timings in a day should not overlap\n"
            + "- Start time must be strictly before the end time.\n"
            + "- It cannot be left completely empty.";

    /*
     * Regex breakdown:
     * - Time: ([01][0-9]|2[0-3])[0-5][0-9] matches 0000 to 2359
     * - Day: (mon|tue|wed|thu|fri|sat|sun)
     * - Single Slot: day:time-time
     * - Full String: single slot followed by optionally more (,slot)
     */
    private static final String TIME_REGEX = "([01][0-9]|2[0-3])[0-5][0-9]";
    private static final String DAY_REGEX = "(?i)(mon|tue|wed|thu|fri|sat|sun)"; // (?i) makes it case-insensitive
    private static final String TIME_PAIR_REGEX = TIME_REGEX + "-" + TIME_REGEX;
    private static final String SINGLE_SLOT_REGEX = DAY_REGEX + ":" + TIME_PAIR_REGEX + "(," + TIME_PAIR_REGEX + ")*";

    // Validates a proper comma-separated availability list without spaces
    public static final String VALIDATION_REGEX = "^" + SINGLE_SLOT_REGEX + "(;" + SINGLE_SLOT_REGEX + ")*$";

    public final String value;

    /**
     * Constructs an {@code Availability}.
     *
     * @param availability A valid availability.
     */
    public Availability(String availability) {
        requireNonNull(availability);
        checkArgument(isValidAvailability(availability), MESSAGE_CONSTRAINTS);
        value = availability.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid availability.
     */
    public static boolean isValidAvailability(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        String[] slots = test.split(";");
        Set<String> seenDays = new HashSet<>();

        for (String slot : slots) {
            String[] parts = slot.split(":");
            String day = parts[0].toLowerCase();
            String[] timeslots = parts[1].split(",");

            if (!isValidTimeslot(timeslots)) {
                return false;
            }

            if (!seenDays.add(day)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if a given string of timeslots are valid timeslots in a day.
     */
    private static boolean isValidTimeslot(String[] timeslots) {
        List<int[]> parsedTimeslots = new ArrayList<>();

        for (String timeslot : timeslots) {
            String[] times = timeslot.split("-");
            int startTime = Integer.parseInt(times[0]);
            int endTime = Integer.parseInt(times[1]);
            if (startTime >= endTime) {
                return false;
            }

            parsedTimeslots.add(new int[]{startTime, endTime});
        }

        parsedTimeslots.sort(Comparator.comparingInt(timeslot -> timeslot[0]));
        for (int i = 0; i < parsedTimeslots.size() - 1; ++i) {
            int currentSlotEnd = parsedTimeslots.get(i)[1];
            int nextSlotStart = parsedTimeslots.get(i + 1)[0];

            if (nextSlotStart < currentSlotEnd) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Availability)) {
            return false;
        }

        Availability otherAvailability = (Availability) other;
        return value.equals(otherAvailability.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
