package seedu.address.storage;

/**
 * Represents a single serialized person entry that failed validation.
 *
 * <p>The original {@link JsonAdaptedPerson} is retained so the user can inspect
 * the raw fields later, and {@link #getReason()} explains why conversion failed.</p>
 */
public class InvalidPersonRecord {

    private final JsonAdaptedPerson adaptedPerson;
    private final String reason;

    /** Creates a record of the invalid adapted person and rejection reason. */
    public InvalidPersonRecord(JsonAdaptedPerson adaptedPerson, String reason) {
        this.adaptedPerson = adaptedPerson;
        this.reason = reason;
    }

    /** Returns the raw adapted person that failed validation. */
    public JsonAdaptedPerson getAdaptedPerson() {
        return adaptedPerson;
    }

    /** Returns why the adapted person could not be converted. */
    public String getReason() {
        return reason;
    }

}
