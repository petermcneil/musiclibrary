package petermcneil.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class BioTest {
    String string1 = "image string";
    String string2 = "string of image";


    Bio queen =  Bio.bioBuilder()
            .biography("Freddie Mercury is the singer")
            .image(string1)
            .website("https://www.queenonline.com")
            .build();

    @Test
    public void exactCopy() throws Exception {
        Bio queen2 = queen
                .copy()
                .build();

        assertEquals(queen, queen2);
        assertFalse(queen == queen2);
    }


    @Test
    public void copyAndChange() throws Exception{
        Bio queen2 = queen
                .copy()
                .build();

        Bio queen3 = queen2
                .copy()
                .biography("Freddie Mercury is no longer in the band")
                .image(string2)
                .build();

        assertNotEquals(queen2, queen3);
        assertThat(queen3.getImage(), is(equalTo(string2)));
    }

    @Test
    public void attributeSaving() throws Exception{
        assertThat(queen.getImage(), is(equalTo(string1)));
    }
}
