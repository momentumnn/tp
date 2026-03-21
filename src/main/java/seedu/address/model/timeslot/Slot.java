package seedu.address.model.timeslot;

public enum Slot {
    SLOT_1("0800-0900"),
    SLOT_2("0900-1000"),
    SLOT_3("1000-1100"),
    SLOT_4("1100-1200"),
    SLOT_5("1200-1300"),
    SLOT_6("1300-1400"),
    SLOT_7("1400-1500"),
    SLOT_8("1500-1600"),
    SLOT_9("1600-1700"),
    SLOT_10("1700-1800"),
    SLOT_11("1800-1900"),
    SLOT_12("1900-2000");

    private final String slot;

    Slot(String slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return slot;
    }

    public static Slot toSlot(String input) {
        for (Slot s : Slot.values()) {
            String slotNumber = String.valueOf(s.ordinal() + 1);
            if (s.slot.equals(input) || slotNumber.equals(input)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid slot input");
    }
}
