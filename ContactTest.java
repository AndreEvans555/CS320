import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for contact creation, field limits, and validated updates.
 */
public class ContactTest {
    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact("C100", "Andre", "Evans", "0123456789", "123 Main Street");
    }

    @Test
    void createsContactWithValidFields() {
        assertEquals("C100", contact.getContactId());
        assertEquals("Andre", contact.getFirstName());
        assertEquals("Evans", contact.getLastName());
        assertEquals("0123456789", contact.getPhone());
        assertEquals("123 Main Street", contact.getAddress());
    }

    @Test
    void acceptsFieldsAtMaximumLengths() {
        Contact boundary = new Contact("1234567890", "ABCDEFGHIJ", "KLMNOPQRST",
                "0000000000", "123456789012345678901234567890");

        assertEquals("1234567890", boundary.getContactId());
        assertEquals("ABCDEFGHIJ", boundary.getFirstName());
        assertEquals("KLMNOPQRST", boundary.getLastName());
        assertEquals("0000000000", boundary.getPhone());
        assertEquals("123456789012345678901234567890", boundary.getAddress());
    }

    @Test
    void rejectsNullContactId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact(null, "Andre", "Evans", "0123456789", "123 Main Street"));
    }

    @Test
    void rejectsContactIdLongerThanTenCharacters() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("12345678901", "Andre", "Evans", "0123456789", "123 Main Street"));
    }

    @Test
    void rejectsNullFirstName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", null, "Evans", "0123456789", "123 Main Street"));
    }

    @Test
    void rejectsFirstNameLongerThanTenCharacters() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "ABCDEFGHIJK", "Evans", "0123456789", "123 Main Street"));
    }

    @Test
    void rejectsNullLastName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "Andre", null, "0123456789", "123 Main Street"));
    }

    @Test
    void rejectsLastNameLongerThanTenCharacters() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "Andre", "ABCDEFGHIJK", "0123456789", "123 Main Street"));
    }

    @Test
    void rejectsNullPhone() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "Andre", "Evans", null, "123 Main Street"));
    }

    @Test
    void rejectsPhoneWithNineDigits() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "Andre", "Evans", "123456789", "123 Main Street"));
    }

    @Test
    void rejectsPhoneWithElevenDigits() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "Andre", "Evans", "12345678901", "123 Main Street"));
    }

    @Test
    void rejectsEmptyPhone() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "Andre", "Evans", "", "123 Main Street"));
    }

    @Test
    void rejectsPhoneWithNondigitCharacters() {
        String[] invalidPhones = {"123456789A", "12345 7890", "123-456789", "+123456789", "123456789\n"};
        for (String phone : invalidPhones) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Contact("C100", "Andre", "Evans", phone, "123 Main Street"));
        }
    }

    @Test
    void rejectsNullAddress() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "Andre", "Evans", "0123456789", null));
    }

    @Test
    void rejectsAddressLongerThanThirtyCharacters() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C100", "Andre", "Evans", "0123456789",
                        "1234567890123456789012345678901"));
    }

    @Test
    void updatesFirstNameToMaximumLength() {
        contact.setFirstName("ABCDEFGHIJ");
        assertEquals("ABCDEFGHIJ", contact.getFirstName());
    }

    @Test
    void updatesLastNameToMaximumLength() {
        contact.setLastName("KLMNOPQRST");
        assertEquals("KLMNOPQRST", contact.getLastName());
    }

    @Test
    void updatesPhoneAndPreservesLeadingZero() {
        contact.setPhone("0987654321");
        assertEquals("0987654321", contact.getPhone());
    }

    @Test
    void updatesAddressToMaximumLength() {
        contact.setAddress("123456789012345678901234567890");
        assertEquals("123456789012345678901234567890", contact.getAddress());
    }

    @Test
    void invalidFirstNameUpdatesPreserveOriginalValue() {
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
        assertEquals("Andre", contact.getFirstName());
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("ABCDEFGHIJK"));
        assertEquals("Andre", contact.getFirstName());
    }

    @Test
    void invalidLastNameUpdatesPreserveOriginalValue() {
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null));
        assertEquals("Evans", contact.getLastName());
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName("ABCDEFGHIJK"));
        assertEquals("Evans", contact.getLastName());
    }

    @Test
    void invalidPhoneUpdatesPreserveOriginalValue() {
        String[] invalidPhones = {null, "", "123456789", "12345678901", "123456789A", "123-456789"};
        for (String phone : invalidPhones) {
            assertThrows(IllegalArgumentException.class, () -> contact.setPhone(phone));
            assertEquals("0123456789", contact.getPhone());
        }
    }

    @Test
    void invalidAddressUpdatesPreserveOriginalValue() {
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(null));
        assertEquals("123 Main Street", contact.getAddress());
        assertThrows(IllegalArgumentException.class,
                () -> contact.setAddress("1234567890123456789012345678901"));
        assertEquals("123 Main Street", contact.getAddress());
    }

    @Test
    void contactIdStaysTheSameAfterAllFieldUpdates() {
        contact.setFirstName("Jordan");
        contact.setLastName("Smith");
        contact.setPhone("9876543210");
        contact.setAddress("456 Oak Avenue");

        assertEquals("C100", contact.getContactId());
    }

    @Test
    void contactIdIsFinalAndHasNoSetter() throws NoSuchFieldException {
        int modifiers = Contact.class.getDeclaredField("contactId").getModifiers();
        org.junit.jupiter.api.Assertions.assertTrue(java.lang.reflect.Modifier.isPrivate(modifiers));
        org.junit.jupiter.api.Assertions.assertTrue(java.lang.reflect.Modifier.isFinal(modifiers));
        org.junit.jupiter.api.Assertions.assertFalse(java.lang.reflect.Modifier.isStatic(modifiers));
        assertThrows(NoSuchMethodException.class,
                () -> Contact.class.getMethod("setContactId", String.class));
    }

}
