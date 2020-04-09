package project;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DriverTest {

    Driver driver = new Driver();

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testConvertToNum() {
        assertEquals(0, driver.convertToNum('A'));
    }

    @Test
    void testIsSeatTaken() {
        LinkedList<Integer> row = new LinkedList<>();
        LinkedList<Integer> col = new LinkedList<>();
        row.add(5);
        col.add(5);

        assertTrue(driver.isSeatTaken(row, col, 5, 5));
    }
}
