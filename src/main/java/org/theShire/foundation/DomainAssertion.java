package org.theShire.foundation;

import java.sql.Time;
import java.time.Instant;
import java.time.Year;
import java.util.List;
import java.util.function.Supplier;

import static java.lang.StringTemplate.STR;

public abstract class DomainAssertion<T> {

        // Null Assertions -------------------------------------------------------------

        // public static Object isNotNull(Object value, String paramName) {
        public static <T> T isNotNull(T value, String paramName) {
                if (value == null)
                    throw new AssertionException(STR."\{paramName} is null");

                return value;
        }


        // String Assertions -----------------------------------------------------------

        public static String isNotBlank(String value, String paramName) {
            isNotNull(value, paramName);

            if (value.isBlank())
                throw new AssertionException(STR."\{paramName} is blank");

            return value;
        }

        public static String hasMaxLength(String value, int maxLength, String paramName) {

            isNotBlank(value, paramName);

            if (value.length() > maxLength)
                throw new AssertionException(STR."\{paramName} is greater than \{maxLength}");

            return value;
        }

        // number Assertions --------------------------------------------------------------
        public static <T extends Number&Comparable<T> > T greaterZero(T value, String paramName){
            return greaterZero(value, ()->STR."\{paramName} is smaller than 0");
        }

        public static <T extends Number&Comparable<T> > T greaterZero(T value, Supplier<String> errorMsg){

            if (value.doubleValue() <= 0){
                throw new AssertionException(errorMsg.get());
            }
            return value;
        }

        public static <T extends Number&Comparable<T> > T greaterThan(T value,T value1, String paramName) {

            if (value.compareTo(value1) <= 0) {
                throw new AssertionException(STR. "\{ paramName } is smaller than \{value1}" );
            }
            return value;
        }
        // Expression Assertions -------------------------------------------------------

        public static void isTrue(boolean expression, Supplier<String> errorMsg) {
            if (!expression)
                throw new AssertionException(errorMsg.get());
        }

        // list Assertions -------------------------------------------------------------
        public static <T>T isNotInList(T o, List<T> list, String paramName){
            isNotNull(o,paramName);

            if (list.contains(o)) {
                throw new AssertionException(STR. "\{ paramName }is existing in list");
            }
            return o;
        }


        // Time Assertions -------------------------------------------------------------
        public static <T extends Instant>T isBeforeNow(T time, String paramName){
            isNotNull(time,paramName);

            if (time.isBefore(Instant.now()))
                throw new AssertionException(STR."\{paramName} is in the past");
            return time;
        }

        public static <T extends Instant>T isBeforeTime(T time1, T time2, String paramName){
            isNotNull(time1, paramName);
            isNotNull(time2, paramName);

            if (time1.isBefore(time2))
                throw new AssertionException(STR."\{paramName} time 1: \{time1} is before time 2: \{time2}");

            return time1;
        }

    }
