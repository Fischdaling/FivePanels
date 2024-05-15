package org.theShire.foundation;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.function.Supplier;

import static java.lang.StringTemplate.STR;

public abstract class DomainAssertion<T> {
        //Variable Exceptions
        private static <E extends RuntimeException> E variableException(Class<E> clazz, String message) {
            try {
                java.lang.reflect.Constructor<E> constructor = clazz.getConstructor(String.class);
                return constructor.newInstance(message);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException("Failed to instantiate exception", e);
            }
        }
            // Null Assertions -------------------------------------------------------------

        // public static Object isNotNull(Object value, String paramName) {
        public static <T, E extends RuntimeException> T isNotNull(T value, String paramName, Class<E> clazz) {
                if (value == null) {
                    throw variableException(clazz,STR."\{paramName} cannot be null");
                }
                return value;
        }


        // String Assertions -----------------------------------------------------------

        public static <E extends RuntimeException>String isNotBlank(String value, String paramName, Class<E> clazz) {
            isNotNull(value, paramName, clazz);

            if (value.isBlank())
                throw variableException(clazz,STR."\{paramName} is blank");

            return value;
        }

        public static <E extends RuntimeException> String hasMaxLength(String value, int maxLength, String paramName, Class<E> clazz) {

            isNotBlank(value, paramName, clazz);

            if (value.length() > maxLength)
                throw variableException(clazz,STR."\{paramName} is greater than \{maxLength}");

            return value;
        }

        public static <E extends RuntimeException> String hasMinLength(String value, int minLength, String paramName, Class<E> clazz) {
            isNotBlank(value, paramName, clazz);

            if (value.length() <= minLength)
                throw variableException(clazz,STR."\{paramName} is smaller than \{minLength}");

            return value;
        }

        public static <E extends RuntimeException> String containsSymbol(String value, String paramName, Class<E> clazz, char symbol) {
            isNotBlank(value, paramName, clazz);
            if (!value.contains(String.valueOf(symbol))){
                throw variableException(clazz, STR."\{paramName} does not contain \{symbol}");
            }
            return value;
        }

        public static <E extends RuntimeException> String containsSymbols(String value, String paramName, Class<E> clazz, char...symbol){
            for (int i = 0; i < symbol.length; i++) {
                containsSymbol(value, paramName, clazz, symbol[i]);
            }
            return value;
        }
        // number Assertions --------------------------------------------------------------
        public static <T extends Number&Comparable<T>, E extends RuntimeException> T greaterZero(T value, String paramName, Class<E> clazz){
            return greaterZero(value, ()->STR."\{paramName} is smaller or Equal than 0", clazz);
        }

        public static <T extends Number&Comparable<T>, E extends RuntimeException> T greaterZero(T value, Supplier<String> errorMsg, Class<E> clazz){
            isNotNull(value,errorMsg.get(),clazz);

            if (value.doubleValue() <= 0){
                throw variableException(clazz,errorMsg.get());
            }
            return value;
        }

        public static <T extends Number&Comparable<T>, E extends RuntimeException> T greaterEqualsZero(T value, String paramName, Class<E> clazz){
            return greaterEqualsZero(value, ()->STR."\{paramName} is smaller to Zero",clazz);
        }

        public static <T extends Number&Comparable<T>, E extends RuntimeException> T greaterEqualsZero(T value, Supplier<String> errorMsg, Class<E> clazz){
            isNotNull(value,errorMsg.get(),clazz);
            if (value.doubleValue() < 0){
                throw variableException(clazz,errorMsg.get());
            }
            return value;
        }

        public static <T extends Number&Comparable<T>, E extends RuntimeException> T greaterThan(T value,T value1, String paramName, Class<E> clazz) {
            isNotNull(value,paramName,clazz);
            if (value.compareTo(value1) <= 0) {
                throw variableException(clazz,STR. "\{ paramName } is smaller than \{value1}" );
            }
            return value;
        }
    // Expression Assertions -------------------------------------------------------

        public static <E extends RuntimeException> void isTrue(boolean expression, Supplier<String> errorMsg, Class<E> clazz) {
            if (!expression)
                throw variableException(clazz,errorMsg.get());
        }

        // list Assertions -------------------------------------------------------------
        // Type erasure
        public static <T, E extends RuntimeException>T isNotInCollection(T o, Collection<T> list, String paramName, Class<E> clazz){
            isNotNull(o,paramName, clazz);

            if (list.contains(o)) {
                throw variableException(clazz,STR. "\{ paramName }is existing in list");
            }
            return o;
        }

        //TODO TESTS:

        // Time Assertions -------------------------------------------------------------
        public static <T extends Instant, E extends RuntimeException>T isBeforeNow(T time, String paramName, Class<E> clazz){
            isNotNull(time,paramName, clazz);

            if (time.isBefore(Instant.now()))
                throw variableException(clazz,STR."\{paramName} is in the past");
            return time;
        }

        public static <T extends Instant, E extends RuntimeException>T isBeforeTime(T time1, T time2, String paramName, Class<E> clazz){
            isNotNull(time1, paramName, clazz);
            isNotNull(time2, paramName, clazz);

            if (time1.isBefore(time2))
                throw variableException(clazz,STR."\{paramName} time 1: \{time1} is before time 2: \{time2}");

            return time1;
        }

        // Password Assertions--------------------------------------------------------------
        public static <E extends RuntimeException> byte[] isZxcvbn3Confirm(String value, String paramName, Class<E> clazz) {
            isNotNull(value, paramName, clazz);
            Zxcvbn zxcvbn = new Zxcvbn();
            Strength strength = zxcvbn.measure(value);
            if (strength.getScore() <3){
                throw variableException(clazz, strength.getFeedback().toString());
            }
            return BCrypt.withDefaults().hash(6, value.getBytes(StandardCharsets.UTF_8));
        }
    }

