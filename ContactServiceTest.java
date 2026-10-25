import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for unique IDs and contact operations selected by ID.
 */
public class ContactServiceTest {
    private ContactService service;

    @BeforeEach
    void setUp() {
        service = new ContactService();
        service.addContact(new Contact("C100", "Andre", "Evans", "0123456789", "123 Main Street"));
        service.addContact(new Contact("C200", "Alex", "Brown", "5551234567", "789 Pine Road"));
    }

    @Test
    void addsAndRetrievesContactsWithDifferentIds() {
        Contact first = service.getContact("C100");
        assertEquals("C100", first.getContactId());
        assertEquals("Andre", first.getFirstName());
        assertEquals("Evans", first.getLastName());
        assertEquals("0123456789", first.getPhone());
        assertEquals("123 Main Street", first.getAddress());

        assertSecondContactUnchanged();
    }

    @Test
    void acceptsMaximumLengthContactId() {
        service.addContact(new Contact("1234567890", "Sam", "Lee", "5559991234", "15 Oak Lane"));
        assertEquals("1234567890", service.getContact("1234567890").getContactId());
    }

    @Test
    void rejectsDuplicateIdWithoutReplacingOriginalContact() {
        Contact duplicate = new Contact("C100", "Jordan", "Smith", "9876543210", "456 Oak Avenue");
        assertThrows(IllegalArgumentException.class, () -> service.addContact(duplicate));

        assertEquals("Andre", service.getContact("C100").getFirstName());
        assertEquals("Evans", service.getContact("C100").getLastName());
        assertEquals("0123456789", service.getContact("C100").getPhone());
        assertEquals("123 Main Street", service.getContact("C100").getAddress());
        assertSecondContactUnchanged();
    }

    @Test
    void rejectsAddingTheSameContactTwice() {
        Contact existing = service.getContact("C100");
        assertThrows(IllegalArgumentException.class, () -> service.addContact(existing));
        assertEquals("Andre", service.getContact("C100").getFirstName());
    }

    @Test
    void rejectsNullContact() {
        assertThrows(IllegalArgumentException.class, () -> service.addContact(null));
    }

    @Test
    void deletesOnlyTheContactWithTheRequestedId() {
        service.deleteContact("C100");

        assertThrows(IllegalArgumentException.class, () -> service.getContact("C100"));
        assertSecondContactUnchanged();
    }

    @Test
    void rejectsDeletingAnUnknownId() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("missing"));
        assertEquals("Andre", service.getContact("C100").getFirstName());
        assertSecondContactUnchanged();
    }

    @Test
    void rejectsDeletingAContactTwice() {
        service.deleteContact("C100");
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("C100"));
    }

    @Test
    void deletedIdCanBeUsedForANewContact() {
        service.deleteContact("C100");
        service.addContact(new Contact("C100", "Sam", "Lee", "5559991234", "15 Oak Lane"));

        assertEquals("Sam", service.getContact("C100").getFirstName());
        assertSecondContactUnchanged();
    }

    @Test
    void updatesOnlyTheRequestedFirstName() {
        service.updateFirstName("C100", "ABCDEFGHIJ");

        Contact updated = service.getContact("C100");
        assertEquals("C100", updated.getContactId());
        assertEquals("ABCDEFGHIJ", updated.getFirstName());
        assertEquals("Evans", updated.getLastName());
        assertEquals("0123456789", updated.getPhone());
        assertEquals("123 Main Street", updated.getAddress());
        assertSecondContactUnchanged();
    }

    @Test
    void updatesOnlyTheRequestedLastName() {
        service.updateLastName("C100", "KLMNOPQRST");

        Contact updated = service.getContact("C100");
        assertEquals("C100", updated.getContactId());
        assertEquals("Andre", updated.getFirstName());
        assertEquals("KLMNOPQRST", updated.getLastName());
        assertEquals("0123456789", updated.getPhone());
        assertEquals("123 Main Street", updated.getAddress());
        assertSecondContactUnchanged();
    }

    @Test
    void updatesOnlyTheRequestedPhone() {
        service.updatePhone("C100", "0987654321");

        Contact updated = service.getContact("C100");
        assertEquals("C100", updated.getContactId());
        assertEquals("Andre", updated.getFirstName());
        assertEquals("Evans", updated.getLastName());
        assertEquals("0987654321", updated.getPhone());
        assertEquals("123 Main Street", updated.getAddress());
        assertSecondContactUnchanged();
    }

    @Test
    void updatesOnlyTheRequestedAddress() {
        service.updateAddress("C100", "123456789012345678901234567890");

        Contact updated = service.getContact("C100");
        assertEquals("C100", updated.getContactId());
        assertEquals("Andre", updated.getFirstName());
        assertEquals("Evans", updated.getLastName());
        assertEquals("0123456789", updated.getPhone());
        assertEquals("123456789012345678901234567890", updated.getAddress());
        assertSecondContactUnchanged();
    }

    @Test
    void rejectsInvalidFirstNameUpdates() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("C100", null));
        assertEquals("Andre", service.getContact("C100").getFirstName());
        assertThrows(IllegalArgumentException.class,
                () -> service.updateFirstName("C100", "ABCDEFGHIJK"));
        assertEquals("Andre", service.getContact("C100").getFirstName());
    }

    @Test
    void rejectsInvalidLastNameUpdates() {
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("C100", null));
        assertEquals("Evans", service.getContact("C100").getLastName());
        assertThrows(IllegalArgumentException.class,
                () -> service.updateLastName("C100", "ABCDEFGHIJK"));
        assertEquals("Evans", service.getContact("C100").getLastName());
    }

    @Test
    void rejectsInvalidPhoneUpdates() {
        String[] invalidPhones = {null, "", "123456789", "12345678901", "123456789A", "12345 7890"};
        for (String phone : invalidPhones) {
            assertThrows(IllegalArgumentException.class, () -> service.updatePhone("C100", phone));
            assertEquals("0123456789", service.getContact("C100").getPhone());
        }
    }

    @Test
    void rejectsInvalidAddressUpdates() {
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("C100", null));
        assertEquals("123 Main Street", service.getContact("C100").getAddress());
        assertThrows(IllegalArgumentException.class,
                () -> service.updateAddress("C100", "1234567890123456789012345678901"));
        assertEquals("123 Main Street", service.getContact("C100").getAddress());
    }

    @Test
    void rejectsUpdatesForAnUnknownId() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("missing", "Sam"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("missing", "Lee"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("missing", "5559991234"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("missing", "15 Oak Lane"));
        assertSecondContactUnchanged();
    }

    @Test
    void rejectsNullIdsForLookupDeleteAndUpdates() {
        assertThrows(IllegalArgumentException.class, () -> service.getContact(null));
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact(null));
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName(null, "Sam"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName(null, "Lee"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone(null, "5559991234"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress(null, "15 Oak Lane"));
    }

    @Test
    void rejectsUpdatingADeletedContact() {
        service.deleteContact("C100");
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("C100", "Sam"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("C100", "Lee"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("C100", "5559991234"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("C100", "15 Oak Lane"));
    }

    @Test
    void serviceInstancesKeepSeparateContactCollections() {
        ContactService otherService = new ContactService();
        otherService.addContact(new Contact("C100", "Sam", "Lee", "5559991234", "15 Oak Lane"));

        assertEquals("Andre", service.getContact("C100").getFirstName());
        assertEquals("Sam", otherService.getContact("C100").getFirstName());
    }

    // Reused to verify that operating on C100 never changes C200.
    private void assertSecondContactUnchanged() {
        Contact second = service.getContact("C200");
        assertEquals("C200", second.getContactId());
        assertEquals("Alex", second.getFirstName());
        assertEquals("Brown", second.getLastName());
        assertEquals("5551234567", second.getPhone());
        assertEquals("789 Pine Road", second.getAddress());
    }
}
