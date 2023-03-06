package seedu.modcheck.model;

import javafx.collections.ObservableList;
import seedu.modcheck.model.person.Person;

/**
 * Unmodifiable view of an modcheck book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
