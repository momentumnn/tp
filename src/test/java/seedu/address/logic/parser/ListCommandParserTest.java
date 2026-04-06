//@@author bingmybongg
package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.Skill;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    // ===================== No Filter Tests =====================

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    public void parse_emptyOrWhitespaceArgs_returnsListCommand(String input) {
        assertParseSuccess(parser, input, new ListCommand());
    }

    // ===================== Valid Skill Filter Tests =====================

    @ParameterizedTest
    @ValueSource(strings = {"beginner", "intermediate", "expert"})
    public void parse_validSkillFilter_returnsListCommand(String skill) {
        ListCommand expectedCommand = new ListCommand(List.of(new Skill(skill)));
        assertParseSuccess(parser, " s/" + skill, expectedCommand);
    }

    @ParameterizedTest
    @ValueSource(strings = {"   s/intermediate   ", " s/intermediate", " s/iNTerMedIate", " s/Intermediate  "})
    public void parse_validSkill_returnsListCommand(String input) {
        ListCommand expectedCommand = new ListCommand(List.of(new Skill("intermediate")));
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_multipleValidSkillFilters_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(
                new Skill("beginner"), new Skill("expert")));
        assertParseSuccess(parser, " s/beginner s/expert", expectedCommand);
    }

    @Test
    public void parse_threeSkillFilters_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(
                new Skill("beginner"),
                new Skill("intermediate"),
                new Skill("expert")));
        assertParseSuccess(parser, " s/beginner s/intermediate s/expert", expectedCommand);
    }

    // ===================== Invalid Skill Value Tests =====================

    @ParameterizedTest
    @ValueSource(strings = {" s/", " s/   ", " s/advanced", " s/pro s/beginner", " s/beginner  s/pro "})
    public void parse_invalidSkillValue_throwsParseException(String input) {
        assertParseFailure(parser, input, Skill.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleSkillFiltersOneEmpty_throwsParseException() {
        assertParseFailure(parser, " s/beginner s/", Skill.MESSAGE_CONSTRAINTS);
    }

    // ===================== Invalid Prefix Tests =====================

    @ParameterizedTest
    @ValueSource(strings = {
        " n/John",
        " p/12345678",
        " e/john@example.com",
        " a/123 Street",
        " ts/mon:0900-1000",
        " t/marathon",
        " pr/20%",
        " i/broken bones"
    })
    public void parse_invalidPrefix_throwsParseException(String input) {
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        " s/beginner n/John",
        " s/beginner p/12345678",
        " s/beginner e/john@example.com",
        " s/beginner a/123 Street",
        " s/beginner ts/mon:0900-1000",
        " s/beginner t/marathon",
        " s/beginner pr/50%",
        " s/beginner i/dead"
    })
    public void parse_validSkillWithInvalidPrefix_throwsParseException(String input) {
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        " n/John p/12345678",
        " n/John e/john@example.com",
        " n/John a/123 Street",
        " n/John p/12345678 e/john@example.com a/123 Street ts/Mon:1 t/marathon pr/90% i/brain dead"
    })
    public void parse_multipleInvalidPrefixes_throwsParseException(String input) {
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    // ===================== Preamble Tests =====================

    @ParameterizedTest
    @ValueSource(strings = {
        "sometext",
        "sometext s/beginner",
        "sometext n/John",
        "extra text s/expert",
        "invalid s/beginner"
    })
    public void parse_invalidPreamble_throwsParseException(String input) {
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
