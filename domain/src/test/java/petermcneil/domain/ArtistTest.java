package petermcneil.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArtistTest {
    @Test
    public void copy() throws Exception {
        Artist queen = Artist.artistBuilder()
                .name("queen")
                .type("band")
                .build();

        Artist queen2 = queen
                .copy()
                .build();

        Artist bob = queen2
                .copy()
                .name("bob")
                .type("solo")
                .build();

        assertEquals(queen, queen2);
        assertFalse(queen == queen2);
        assertNotEquals(queen2, bob);
    }

}