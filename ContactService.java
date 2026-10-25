import java.util.HashMap;
import java.util.Map;

/**
 * CS 320 Project One: Adds, deletes, and updates contacts in memory.
 * Duplicate IDs, null contacts, and IDs that are not stored cause an
 * IllegalArgumentException. No database or user interface is needed.
 */
public class ContactService {
    private final Map<String, Contact> contacts = new HashMap<>();

    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact must not be null.");
        }
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists.");
        }
        contacts.put(contact.getContactId(), contact);
    }

    public Contact getContact(String contactId) {
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new IllegalArgumentException("Contact ID was not found.");
        }
        return contact;
    }

    public void deleteContact(String contactId) {
        // Check first so a missing ID is reported consistently across operations.
        getContact(contactId);
        contacts.remove(contactId);
    }

    public void updateFirstName(String contactId, String firstName) {
        getContact(contactId).setFirstName(firstName);
    }

    public void updateLastName(String contactId, String lastName) {
        getContact(contactId).setLastName(lastName);
    }

    public void updatePhone(String contactId, String phone) {
        getContact(contactId).setPhone(phone);
    }

    public void updateAddress(String contactId, String address) {
        getContact(contactId).setAddress(address);
    }
}
