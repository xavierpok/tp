package connexion.model.person;

import static connexion.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MarkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Mark(null));
    }

    @Test
    public void equals() {
        Mark mark = new Mark(true);

        // same values -> returns true
        assertTrue(mark.equals(new Mark(true)));

        // same object -> returns true
        assertTrue(mark.equals(mark));

        // null -> returns false
        assertFalse(mark.equals(null));

        // different types -> returns false
        assertFalse(mark.equals(5.0f));

        // different values -> returns false
        assertFalse(mark.equals(new Mark(false)));
    }

    @Test
    public void toString_markedStatus() {
        Mark markedMark = new Mark(true);
        assertEquals("\u2605", markedMark.toString());
    }

    @Test
    public void toString_unmarkedStatus() {
        Mark unmarkedMark = new Mark(false);
        assertEquals("\u2606", unmarkedMark.toString());
    }

    @Test
    void getDetailString_markedStatus() {
        Mark markedMark = new Mark(true);
        assertEquals("\u2605", markedMark.getDetailString());
    }
    @Test
    void getDetailString_unmarkedStatus() {
        Mark unmarkedMark = new Mark(false);
        assertEquals("\u2606", unmarkedMark.getDetailString());
    }

    @Test
    void getValue_markedStatus() {
        Mark markedMark = new Mark(true);
        assertEquals(true, markedMark.getValue());
    }
    @Test
    void getValue_unmarkedStatus() {
        Mark unmarkedMark = new Mark(false);
        assertEquals(false, unmarkedMark.getValue());
    }


    @Test
    void getListString_markedStatus() {
        Mark markedMark = new Mark(true);
        assertEquals("\u2605", markedMark.getListString());
    }
    @Test
    void getListString_unmarkedStatus() {
        Mark unmarkedMark = new Mark(false);
        assertEquals("\u2606", unmarkedMark.getListString());
    }
}
