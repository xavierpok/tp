package connexion.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;



class ScheduleStubTest {

    // LocalDateTime.of(
    //            2000, 10, 10, 10, 10)
    @Test
    void getDetailString() {
        assertEquals("10/Oct/2000 (in 1 year)", new ScheduleStub().getDetailString());
    }

    @Test
    void getValue() {
        assertEquals(LocalDateTime.of(
                2000, 10, 10, 10, 10),
                new ScheduleStub().getValue());
    }

    @Test
    void getListString() {
        assertEquals("10 Oct (in 1 year)", new ScheduleStub().getListString());
    }
}
