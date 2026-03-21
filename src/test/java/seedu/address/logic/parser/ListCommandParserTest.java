package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.FilteredSkill;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArgs_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand());
    }

    @Test
    public void parse_whitespaceArgs_returnsListCommand() {
        assertParseSuccess(parser, "   ", new ListCommand());
    }

    @Test
    public void parse_validSkillFilterBeginner_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(new FilteredSkill("beginner")));
        assertParseSuccess(parser, " s/beginner", expectedCommand);
    }

    @Test
    public void parse_validSkillFilterIntermediate_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(new FilteredSkill("intermediate")));
        assertParseSuccess(parser, " s/intermediate", expectedCommand);
    }

    @Test
    public void parse_validSkillFilterExpert_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(new FilteredSkill("expert")));
        assertParseSuccess(parser, " s/expert", expectedCommand);
    }

    @Test
    public void parse_validSkillFilterWithWhitespace_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(new FilteredSkill("intermediate")));
        assertParseSuccess(parser, "   s/intermediate   ", expectedCommand);
    }

    @Test
    public void parse_validSkillFilterMixedCase_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(new FilteredSkill("ExPeRt")));
        assertParseSuccess(parser, " s/ExPeRt", expectedCommand);
    }

    @Test
    public void parse_customSkillValue_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(new FilteredSkill("advanced")));
        assertParseSuccess(parser, " s/advanced", expectedCommand);
    }

    @Test
    public void parse_multipleValidSkillFilters_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(
                new FilteredSkill("beginner"), new FilteredSkill("expert")));
        assertParseSuccess(parser, " s/beginner s/expert", expectedCommand);
    }

    @Test
    public void parse_multipleSkillFiltersMixedCase_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(
                new FilteredSkill("BEGINNER"), new FilteredSkill("eXpErT")));
        assertParseSuccess(parser, " s/BEGINNER s/eXpErT", expectedCommand);
    }

    @Test
    public void parse_threeSkillFilters_returnsListCommand() {
        ListCommand expectedCommand = new ListCommand(List.of(
                new FilteredSkill("beginner"),
                new FilteredSkill("intermediate"),
                new FilteredSkill("expert")));
        assertParseSuccess(parser, " s/beginner s/intermediate s/expert", expectedCommand);
    }

    @Test
    public void parse_emptySkillValue_throwsParseException() {
        assertParseFailure(parser, " s/", FilteredSkill.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_whitespaceSkillValue_throwsParseException() {
        assertParseFailure(parser, " s/   ", FilteredSkill.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleSkillFiltersOneEmpty_throwsParseException() {
        assertParseFailure(parser, " s/beginner s/", FilteredSkill.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        assertParseFailure(parser, "invalid s/beginner",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " n/John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleInvalidPrefixes_throwsParseException() {
        assertParseFailure(parser, " n/John p/12345678",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validPrefixWithInvalidPrefix_throwsParseException() {
        assertParseFailure(parser, " s/beginner n/John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preambleWithValidPrefix_throwsParseException() {
        assertParseFailure(parser, "extra text s/expert",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
