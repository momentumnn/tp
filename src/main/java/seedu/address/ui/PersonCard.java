package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Skill;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private HBox timeslots;
    @FXML
    private Label trainingGoal;
    @FXML
    private Label email;
    @FXML
    private Label injuryStatus;
    @FXML
    private Label skill;
    @FXML
    private Label progressRecord;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        String fullName = person.getName().fullName;
        name.setText(fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        trainingGoal.setText("Goal: " + person.getTrainingGoal().value);
        email.setText(person.getEmail().value);
        injuryStatus.setText("Injury Status: " + person.getInjuryStatus().value);
        progressRecord.setText("Progress: " + person.getProgressRecord().value);
        person.getTimeslots().stream().sorted()
                .forEach(timeslot -> {
                    Label timeslotLabel = new Label(timeslot.toString());
                    timeslotLabel.getStyleClass().add("timeslot-label");
                    timeslots.getChildren().add(timeslotLabel);
                });

        // Customised style for skill
        String skillLevel = person.getSkill().value;
        skill.setText(skillLevel);
        switch (skillLevel.toLowerCase()) {
        case Skill.SKILL_BEGINNER:
            skill.getStyleClass().add("skill-beginner");
            break;
        case Skill.SKILL_INTERMEDIATE:
            skill.getStyleClass().add("skill-intermediate");
            break;
        case Skill.SKILL_EXPERT:
            skill.getStyleClass().add("skill-expert");
            break;
        default:
            skill.getStyleClass().add("skill-beginner");
            break;
        }
    }
}
