package org.theShire.foundation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.theShire.foundation.DomainAssertion.*;

public class DomainAssertionTest {
    

    @Test
    void isNotNull_shouldReturnStr_WhenStrIsNotNull() {
        String str = "Test";
        assertEquals(str, isNotNull(str,"TestCase"));
    }

    @Test
    void isNotNull_shouldThrowException_WhenStrIsNull() {
        String str = null;

        assertThrows(AssertionException.class, ()->isNotNull(str,"TestCaseThrows"));
    }

    @Test
    void isNotNull_shouldReturnInteger_WhenIntegerIsNotNull() {
        Integer i = 1;
        assertEquals(i, isNotNull(i,"TestCaseInteger"));
    }

    @Test
    void isNotNull_shouldThrowException_WhenIntegerIsNull() {
        Integer i = null;

        assertThrows(AssertionException.class, ()->isNotNull(i,"TestCaseIntegerThrows"));
    }

    /** IS NOT BLANK**/
    @Test
    void isNotBlank_shouldReturnStr_WhenStrIsNotEmpty() {
        String str = "Test";
        assertEquals(str, isNotBlank(str,"TestCase"));
    }

    @Test
    void isNotBlank_shouldThrowException_WhenStrIsNull() {
        String str = null;

        assertThrows(AssertionException.class, ()->isNotBlank(str,"TestCaseThrows"));
    }

    @Test
    void isNotBlank_shouldThrowException_WhenStrIsBlank() {
        String str = "";

        assertThrows(AssertionException.class, ()->isNotBlank(str,"TestCaseThrows"));
    }

    /** HAS MAX LENGTH**/

    @Test
    void hasMaxLength_shouldReturnStr_WhenStrIsValid() {
        String str = "Test";
        assertEquals(str, hasMaxLength(str,4,"TestCase"));
    }

    @Test
    void hasMaxLength_shouldThrowException_WhenStrIsNull() {
        String str = null;

        assertThrows(AssertionException.class, ()->hasMaxLength(str,4,"TestCaseThrows"));
    }

    @Test
    void hasMaxLength_shouldThrowException_WhenStrIsBlank() {
        String str = "";

        assertThrows(AssertionException.class, ()->hasMaxLength(str,4,"TestCaseThrows"));
    }
    @Test
    void hasMaxLength_shouldThrowException_WhenStrIsBiggerThenMaxLength() {
        String str = "Test1";

        assertThrows(AssertionException.class, ()->hasMaxLength(str,4,"TestCaseThrows"));
    }

    /** GREATER THAN ZERO**/

    @Test
    void greaterZero_ShouldReturnNumber_WhenNumberAboveZero() {
        Integer i = 1;
        assertEquals(i, greaterZero(i,"TestCaseInteger"));

        int in = 1;
        assertEquals(in, greaterZero(in,"TestCaseInt"));

        double d = 1.0;
        assertEquals(d, greaterZero(d,"TestCaseDouble"));

        float f = 1f;
        assertEquals(f, greaterZero(f,"TestCaseFloat"));

        Long l = (long)1;
        assertEquals(l, greaterZero(l,"TestCaseLong"));
    }

    @Test
    void greaterZero_ShouldReturnNumber_WhenNumberIsZero() {
        Integer i = 0;
        assertThrows(AssertionException.class,()->greaterZero(i,"TestCaseInteger"));

        int in = 0;
        assertThrows(AssertionException.class,()->greaterZero(in,"TestCaseInt"));

        double d = 0.0;
        assertThrows(AssertionException.class,()->greaterZero(d,"TestCaseDouble"));

        float f = 0f;
        assertThrows(AssertionException.class,()->greaterZero(f,"TestCaseFloat"));

        Long l = (long)0;
        assertThrows(AssertionException.class,()->greaterZero(l,"TestCaseLong"));
    }

    @Test
    void greaterZero_ShouldThrowException_WhenNumberBelowZero() {
        Integer i = -1;
        assertThrows(AssertionException.class,()->greaterZero(i,"TestCaseInteger"));

        int in = -1;
        assertThrows(AssertionException.class,()->greaterZero(in,"TestCaseInt"));

        double d = -1.0;
        assertThrows(AssertionException.class,()->greaterZero(d,"TestCaseDouble"));

        float f = -1f;
        assertThrows(AssertionException.class,()->greaterZero(f,"TestCaseFloat"));

        Long l = (long)-1;
        assertThrows(AssertionException.class,()->greaterZero(l,"TestCaseLong"));
    }

    /** GREATER THAN **/
    @Test
    void greaterThan_ShouldReturnNumber_WhenFirstNumberAboveSecondNumber() {
        Integer i = 1;
        Integer i1 = 0;
        assertEquals(i, greaterThan(i,i1,"TestCaseInteger"));

        int in = 1;
        int in1 = 0;
        assertEquals(in, greaterThan(in,in1,"TestCaseInt"));

        double d = 1.0;
        double d1 = 0.0;
        assertEquals(d, greaterThan(d,d1,"TestCaseDouble"));

        float f = 1f;
        float f1 = 0f;
        assertEquals(f, greaterThan(f,f1,"TestCaseFloat"));

        Long l = (long)1;
        Long l1 = (long)0;
        assertEquals(l, greaterThan(l,l1,"TestCaseLong"));
    }

    @Test
    void greaterThan_ShouldThrowException_WhenFirstNumberIsSecondNumber() {
        Integer i = 1;
        Integer i1 = 1;
        assertThrows(AssertionException.class, ()->greaterThan(i,i1,"TestCaseInteger"));

        int in = 1;
        int in1 = 1;
        assertThrows(AssertionException.class, ()->greaterThan(in,in1,"TestCaseInt"));

        double d = 1.0;
        double d1 = 1.0;
        assertThrows(AssertionException.class, ()->greaterThan(d,d1,"TestCaseDouble"));

        float f = 1f;
        float f1 = 1f;
        assertThrows(AssertionException.class, ()->greaterThan(f,f1,"TestCaseFloat"));

        Long l = (long)1;
        Long l1 = (long)1;
        assertThrows(AssertionException.class, ()->greaterThan(l,l1,"TestCaseLong"));
    }

    @Test
    void greaterThan_ShouldThrowException_WhenFirstNumberSmallerThanSecondNumber() {
        Integer i = 1;
        Integer i1 = 2;
        assertThrows(AssertionException.class, ()->greaterThan(i,i1,"TestCaseInteger"));

        int in = 1;
        int in1 = 2;
        assertThrows(AssertionException.class, ()->greaterThan(in,in1,"TestCaseInt"));

        double d = 1.0;
        double d1 = 2.0;
        assertThrows(AssertionException.class, ()->greaterThan(d,d1,"TestCaseDouble"));

        float f = 1f;
        float f1 = 2f;
        assertThrows(AssertionException.class, ()->greaterThan(f,f1,"TestCaseFloat"));

        Long l = (long)1;
        Long l1 = (long)2;
        assertThrows(AssertionException.class, ()->greaterThan(l,l1,"TestCaseLong"));
    }

    @Test
    void isTrue_ShouldThrow_WhenIsFalse(){
        boolean b = false;

        assertThrows(AssertionException.class,()-> isTrue(b,()->"TestCase"));
    }
    @Test
    void isTrue_ShouldNotThrow_WhenIsTrue(){
        boolean b = true;
        try {
            isTrue(b, () -> "TestCase");
        }catch (AssertionException e){
            fail();
        }
    }

    @Test
    void isNotInList_ShouldReturnObjectThatIsNotInList_WhenObjectIsNotInCollection() {
        List<Integer> list = new ArrayList<>();
        int i1 = 1;
        int i2 = 2;
        int i3 = 0;

//        list.add(i1);
        list.add(i2);
        list.add(i3);

        assertEquals(i1, isNotInCollection(i1,list,"TestCaseList"));
    }

    @Test
    void isNotInList_ShouldThrow_WhenObjectIsInCollection() {
        List<Integer> list = new ArrayList<>();
        int i1 = 1;
        int i2 = 2;
        int i3 = 0;

        list.add(i1);
        list.add(i2);
        list.add(i3);

        assertThrows(AssertionException.class,()-> isNotInCollection(i1,list,"TestCaseList"));
    }

}