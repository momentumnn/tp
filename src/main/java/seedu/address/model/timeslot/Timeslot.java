package seedu.address.model.timeslot;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a Person's allocated timeslot in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeslot(String)}
 */
public class Timeslot implements Comparable<Timeslot> {
    public static final String MESSAGE_CONSTRAINTS = "TODO";
    /*
     * Regex breakdown:
     * - Slot: matches 0800 to 2000 OR numbers 1-12
     * - Day: (mon|tue|wed|thu|fri|sat|sun)
     * - Single Slot: day:time-time | day:slotNumber
     * - Full String: single slot followed by optionally more (,slot)
     */
    private static final String SLOT_REGEX = "((0[8-9]|1[0-9])00-(0[9]|1[0-9]|20)00)|([1-9]|1[0-2])";
    private static final String DAY_REGEX = "(?i)(mon|tue|wed|thu|fri|sat|sun)";
    private static final String SINGLE_TIMESLOT_REGEX = "^" + DAY_REGEX + ":" + SLOT_REGEX + "(," + SLOT_REGEX + ")*";

    private final Day day;
    private final Set<Slot> slots;

    /**
     * Constructs an {@code Timeslot}.
     *
     * @param timeslot A valid timeslot.
     */
    public Timeslot(String timeslot) {
        requireNonNull(timeslot);
        checkArgument(isValidTimeslot(timeslot), MESSAGE_CONSTRAINTS);

        String[] parts = timeslot.split(":");
        this.day = Day.valueOf(parts[0].toUpperCase());

        Set<Slot> slots = new TreeSet<>();
        String[] slotParts = parts[1].split(",");
        for (String slot : slotParts) {
            slots.add(Slot.toSlot(slot));
        }
        this.slots = Collections.unmodifiableSet(slots);
    }

    /**
     * Returns true if a given string is a valid timeslot.
     */
    public static boolean isValidTimeslot(String test) {
        if (!test.matches(SINGLE_TIMESLOT_REGEX)) {
            return false;
        }

        String[] parts = test.split(":");
        Set<Slot> slots = new TreeSet<>();
        String[] slotParts = parts[1].split(",");
        for (String slot : slotParts) {
            if (!slots.add(Slot.toSlot(slot))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        List<String> slotStrings = new ArrayList<>();
        for (Slot s : slots) {
            slotStrings.add(s.toString());
        }
        return day + ": " + String.join(", ", slotStrings);
    }

    @Override
    public int compareTo(Timeslot other) {
        return this.day.compareTo(other.day);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Timeslot)) {
            return false;
        }

        Timeslot otherTimeslot = (Timeslot) other;
        return day.equals(otherTimeslot.day) && slots.equals(otherTimeslot.slots);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(day, slots);
    }
}
