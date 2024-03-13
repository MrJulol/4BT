package org.example;

import org.junit.jupiter.api.*;

public class NumberUtilsTest {
    @Test
    @DisplayName("Test isEven")
    public void isEven() {
        NumberUtils nu = new NumberUtils();
        Assertions.assertTrue(nu.isEven(2));
        Assertions.assertTrue(nu.isEven(4));
    }

    @Test
    @DisplayName("Test Sort")
    public void sortArray(){
        int[] ar1 = {9,6,5,3,8,1};
        int[] ar2 = {7,6,5,3,8,1};
        NumberUtils nu = new NumberUtils();
        nu.sortArray(ar1);
        nu.sortArray(ar2);
        Assertions.assertArrayEquals(new int[]{1,3,5,6,8,9}, ar1);
        Assertions.assertArrayEquals(new int[]{1,3,5,6,7,8}, ar2);
    }

}
