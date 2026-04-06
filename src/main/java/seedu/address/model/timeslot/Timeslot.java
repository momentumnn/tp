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
    public static final String MESSAGE_CONSTRAINTS = "Timeslot should be in the format "
            + "day:start-end OR day:slotNumber separated by semicolons.\n"
            + "Example: mon:0900-1000,1200-1300;tue:9;wed:7\n"
            + "- Days (must be unique): 3-letter abbreviation (mon, tue, wed, thu, fri, sat, sun) case-insensitive.\n"
            + "- Times (must be unique): "
            + "in 24-hour format (HHMM-HHMM) from 0800 to 2000 OR slot numbers (from 1 to 12).\n"
            + "- Hourly Boundaries: If using HHMM-HHMM, times must start and end exactly on the hour. "
            + "The minutes field must be '00' (e.g., 0800, 1400).\n"
            + "- Multiple timings in a day must be separated by commas with no whitespaces in between timings.\n"
            + "- It cannot be left completely empty.";
    /*
     * Regex breakdown:
     * - Slot: matches 0800 to 2000 OR numbers 1-12
     * - Day: (mon|tue|wed|thu|fri|sat|sun)
     * - Single Slot: day:time-time | day:slotNumber
     * - Full String: single slot followed by optionally more (,slot)
     */
    private static final String SLOT_REGEX = "(((0[8-9]|1[0-9])00-(0[9]|1[0-9]|20)00)|([1-9]|1[0-2]))";
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
        String[] slotParts = parts[1].split(",");
        Set<Slot> slots = new TreeSet<>();

        for (String slot : slotParts) {
            if (!isValidSlot(slot)) {
                return false;
            }
            if (!isUniqueSlot(slot, slots)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a given string is a valid slot.
     *
     * @param slot String.
     */
    public static boolean isValidSlot(String slot) {
        if (slot.contains("-")) {
            String[] times = slot.split("-");
            int startTime = Integer.parseInt(times[0]);
            int endTime = Integer.parseInt(times[1]);

            if (startTime >= endTime) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a given string is unique in {@code existingSlots}.
     *
     * @param slot String.
     * @param existingSlots Set of slots to be tested against for uniqueness.
     */
    public static boolean isUniqueSlot(String slot, Set<Slot> existingSlots) {
        try {
            Slot newSlot = Slot.toSlot(slot);
            if (!existingSlots.add(newSlot)) {
                return false;
            }
        } catch (IllegalArgumentException e) {
            // catch time ranges more than an hour
            return false;
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

    /**
     * Returns a string representation of the timeslot, formatted for storage.
     */
    public String toStorageString() {
        List<String> slotStrings = new ArrayList<>();
        for (Slot s : slots) {
            slotStrings.add(s.toString());
        }
        return day + ":" + String.join(",", slotStrings);
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
