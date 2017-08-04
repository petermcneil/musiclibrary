package petermcneil.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class BioTest {
    @Test
    public void copy() throws Exception {

        //Tests the .biography
        Bio queen =  Bio.bioBuilder()
                .biography("Freddie Mercury is the singer")
                .image("https://goo.gl/images/FTifSf")
                .website("https://www.queenonline.com")
                .build();

        Bio queen2 = queen
                .copy()
                .build();

        assertEquals(queen, queen2);
        assertFalse(queen == queen2);

        Bio queen3 = queen2
                .copy()
                .biography("Freddie Mercury is no longer in the band")
                .image("https://goo.gl/images/GTt9ST")
                .build();

        assertNotEquals(queen2, queen3);
    }

}