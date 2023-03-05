package seedu.modcheck.model;

import java.nio.file.Path;

import seedu.modcheck.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
