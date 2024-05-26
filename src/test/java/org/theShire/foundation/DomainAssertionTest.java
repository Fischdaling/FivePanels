package org.theShire.foundation;

import org.junit.jupiter.api.Test;
import org.theShire.domain.exception.MedicalCaseException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.theShire.foundation.DomainAssertion.*;

public class DomainAssertionTest {
    private static final Class<MedicalCaseException> exType = MedicalCaseException.class;
    

    @Test
    void isNotNull_shouldReturnStr_WhenStrIsNotNull() {
        String str = "Test";
        assertEquals(str, isNotNull(str,"TestCase", exType));
    }

    @Test
    void isNotNull_shouldThrowException_WhenStrIsNull() {
        String str = null;

        assertThrows(exType, ()->isNotNull(str,"TestCaseThrows",exType));
    }

    @Test
    void isNotNull_shouldReturnInteger_WhenIntegerIsNotNull() {
        Integer i = 1;
        assertEquals(i, isNotNull(i,"TestCaseInteger",exType));
    }

    @Test
    void isNotNull_shouldThrowException_WhenIntegerIsNull() {
        Integer i = null;

        assertThrows(exType, ()->isNotNull(i,"TestCaseIntegerThrows",exType));
    }

    /** IS NOT BLANK**/
    @Test
    void isNotBlank_shouldReturnStr_WhenStrIsNotEmpty() {
        String str = "Test";
        assertEquals(str, isNotBlank(str,"TestCase",exType));
    }

    @Test
    void isNotBlank_shouldThrowException_WhenStrIsNull() {
        String str = null;

        assertThrows(exType, ()->isNotBlank(str,"TestCaseThrows",exType));
    }

    @Test
    void isNotBlank_shouldThrowException_WhenStrIsBlank() {
        String str = "";

        assertThrows(exType, ()->isNotBlank(str,"TestCaseThrows",exType));
    }

    /** HAS MAX LENGTH**/

    @Test
    void hasMaxLength_shouldReturnStr_WhenStrIsValid() {
        String str = "Test";
        assertEquals(str, hasMaxLength(str,4,"TestCase",exType));
    }

    @Test
    void hasMaxLength_shouldThrowException_WhenStrIsNull() {
        String str = null;

        assertThrows(exType, ()->hasMaxLength(str,4,"TestCaseThrows",exType));
    }

    @Test
    void hasMaxLength_shouldThrowException_WhenStrIsBlank() {
        String str = "";

        assertThrows(exType, ()->hasMaxLength(str,4,"TestCaseThrows",exType));
    }
    @Test
    void hasMaxLength_shouldThrowException_WhenStrIsBiggerThenMaxLength() {
        String str = "Test1";

        assertThrows(exType, ()->hasMaxLength(str,4,"TestCaseThrows",exType));
    }

    /** GREATER THAN ZERO**/

    @Test
    void greaterZero_ShouldReturnNumber_WhenNumberAboveZero() {
        Integer i = 1;
        assertEquals(i, greaterZero(i,"TestCaseInteger",exType));

        int in = 1;
        assertEquals(in, greaterZero(in,"TestCaseInt",exType));

        double d = 1.0;
        assertEquals(d, greaterZero(d,"TestCaseDouble",exType));

        float f = 1f;
        assertEquals(f, greaterZero(f,"TestCaseFloat",exType));

        Long l = (long)1;
        assertEquals(l, greaterZero(l,"TestCaseLong",exType));
    }

    @Test
    void greaterZero_ShouldReturnNumber_WhenNumberIsZero() {
        Integer i = 0;
        assertThrows(exType,()->greaterZero(i,"TestCaseInteger",exType));

        int in = 0;
        assertThrows(exType,()->greaterZero(in,"TestCaseInt",exType));

        double d = 0.0;
        assertThrows(exType,()->greaterZero(d,"TestCaseDouble",exType));

        float f = 0f;
        assertThrows(exType,()->greaterZero(f,"TestCaseFloat",exType));

        Long l = (long)0;
        assertThrows(exType,()->greaterZero(l,"TestCaseLong",exType));
    }

    @Test
    void greaterZero_ShouldThrowException_WhenNumberBelowZero() {
        Integer i = -1;
        assertThrows(exType,()->greaterZero(i,"TestCaseInteger",exType));

        int in = -1;
        assertThrows(exType,()->greaterZero(in,"TestCaseInt",exType));

        double d = -1.0;
        assertThrows(exType,()->greaterZero(d,"TestCaseDouble",exType));

        float f = -1f;
        assertThrows(exType,()->greaterZero(f,"TestCaseFloat",exType));

        Long l = (long)-1;
        assertThrows(exType,()->greaterZero(l,"TestCaseLong",exType));
    }

    /** GREATER THAN **/
    @Test
    void greaterThan_ShouldReturnNumber_WhenFirstNumberAboveSecondNumber() {
        Integer i = 1;
        Integer i1 = 0;
        assertEquals(i, greaterThan(i,i1,"TestCaseInteger",exType));

        int in = 1;
        int in1 = 0;
        assertEquals(in, greaterThan(in,in1,"TestCaseInt",exType));

        double d = 1.0;
        double d1 = 0.0;
        assertEquals(d, greaterThan(d,d1,"TestCaseDouble",exType));

        float f = 1f;
        float f1 = 0f;
        assertEquals(f, greaterThan(f,f1,"TestCaseFloat",exType));

        Long l = (long)1;
        Long l1 = (long)0;
        assertEquals(l, greaterThan(l,l1,"TestCaseLong",exType));
    }

    @Test
    void greaterThan_ShouldThrowException_WhenFirstNumberIsSecondNumber() {
        Integer i = 1;
        Integer i1 = 1;
        assertThrows(exType, ()->greaterThan(i,i1,"TestCaseInteger",exType));

        int in = 1;
        int in1 = 1;
        assertThrows(exType, ()->greaterThan(in,in1,"TestCaseInt",exType));

        double d = 1.0;
        double d1 = 1.0;
        assertThrows(exType, ()->greaterThan(d,d1,"TestCaseDouble",exType));

        float f = 1f;
        float f1 = 1f;
        assertThrows(exType, ()->greaterThan(f,f1,"TestCaseFloat",exType));

        Long l = (long)1;
        Long l1 = (long)1;
        assertThrows(exType, ()->greaterThan(l,l1,"TestCaseLong",exType));
    }

    @Test
    void greaterThan_ShouldThrowException_WhenFirstNumberSmallerThanSecondNumber() {
        Integer i = 1;
        Integer i1 = 2;
        assertThrows(exType, ()->greaterThan(i,i1,"TestCaseInteger",exType));

        int in = 1;
        int in1 = 2;
        assertThrows(exType, ()->greaterThan(in,in1,"TestCaseInt",exType));

        double d = 1.0;
        double d1 = 2.0;
        assertThrows(exType, ()->greaterThan(d,d1,"TestCaseDouble",exType));

        float f = 1f;
        float f1 = 2f;
        assertThrows(exType, ()->greaterThan(f,f1,"TestCaseFloat",exType));

        Long l = (long)1;
        Long l1 = (long)2;
        assertThrows(exType, ()->greaterThan(l,l1,"TestCaseLong",exType));
    }

    //LesserThan

    @Test
    void lesserThan_ShouldThrowException_WhenFirstNumberGreaterThanSecondNumber() {
        Integer i = 2;
        Integer i1 = 1;
        assertThrows(exType, ()->lesserThan(i,i1,"TestCaseInteger",exType));

        int in = 2;
        int in1 = 1;
        assertThrows(exType, ()->lesserThan(in,in1,"TestCaseInt",exType));

        double d = 2.0;
        double d1 = 1.0;
        assertThrows(exType, ()->lesserThan(d,d1,"TestCaseDouble",exType));

        float f = 2f;
        float f1 = 1f;
        assertThrows(exType, ()->lesserThan(f,f1,"TestCaseFloat",exType));

        Long l = (long)2;
        Long l1 = (long)1;
        assertThrows(exType, ()->lesserThan(l,l1,"TestCaseLong",exType));
    }

    @Test
    void isTrue_ShouldThrow_WhenIsFalse(){
        boolean b = false;

        assertThrows(exType,()-> isTrue(b,()->"TestCase",exType));
    }
    @Test
    void isTrue_ShouldNotThrow_WhenIsTrue(){
        boolean b = true;
        try {
            isTrue(b, () -> "TestCase",exType);
        }catch (RuntimeException e){
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

        assertEquals(i1, isInCollection(i1,list,"TestCaseList",exType));
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

        assertThrows(exType,()-> isInCollection(i1,list,"TestCaseList",exType));
    }

    @Test
    void isBeforeNow_ShouldThrow_WhenIsBeforeNow(){
        Instant instant = Instant.ofEpochSecond(50).minusSeconds(10);
        assertThrows(exType,()-> isBeforeNow(instant,"TestCaseInstant",exType));
    }

    @Test
    void isBeforeNow_ShouldReturnTime_WhenIsAfterNow(){
        Instant instant = Instant.now().plusSeconds(10);
        assertEquals(instant, isBeforeNow(instant,"TestCaseInstant",exType));
    }

    @Test
    void isBeforeNow_ShouldThrown_WhenIsNow(){
        Instant instant = Instant.ofEpochSecond(50);
        Instant.ofEpochSecond(50);
        assertThrows(exType,()-> isBeforeNow(instant,"TestCaseInstant",exType));
    }

    @Test
    void isBeforeTime_ShouldThrow_WhenIsAfterTime(){
        Instant instant = Instant.ofEpochSecond(50).minusSeconds(10);
        Instant instant1 = Instant.ofEpochSecond(50);
        assertThrows(exType,()-> isBeforeTime(instant,instant1,"TestCaseInstant",exType));
    }

    @Test
    void isBeforeTime_ShouldReturnTime_WhenIsBeforeTime(){
        Instant instant = Instant.ofEpochSecond(50);
        Instant instant1 = Instant.ofEpochSecond(50).minusSeconds(10);
        assertEquals(instant, isBeforeTime(instant,instant1,"TestCaseInstant",exType));
    }

    @Test
    void isZxcvbn3Confirm_ShouldThrow_WhenIsNotZxcvbn3Confirm(){
        String zxcvbn3Confirm = "abc";
        assertThrows(exType, ()->isZxcvbn3Confirm(zxcvbn3Confirm,"zxcvbn3Confirm",exType));
    }

    @Test
    void isZxcvbn3Confirm_ShouldReturnHashedPassword_WhenIsZxcvbn3Confirm(){
        String zxcvbn3Confirm = "Spengergasse";
        assertEquals(zxcvbn3Confirm ,isZxcvbn3Confirm(zxcvbn3Confirm,"zxcvbn3Confirm",exType));
    }
}