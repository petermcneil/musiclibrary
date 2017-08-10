package petermcneil.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ArtistTest {
    String string1 = "queen";
    String string2 = "bob";

    Artist queen = Artist.artistBuilder()
            .name(string1)
            .type("band")
            .build();
    @Test
    public void copy() throws Exception {
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

    @Test
    public void copyAndChange() throws Exception{
        Artist queen2 = queen
                .copy()
                .build();

        Artist bob = queen2
                .copy()
                .name(string2)
                .type("solo")
                .build();

        assertNotEquals(queen2, bob);
        assertThat(bob.getName(), is(equalTo(string2)));
    }


    @Test
    public void attributeSaving() throws Exception{
        assertThat(queen.getName(), is(equalTo(string1)));
    }
}