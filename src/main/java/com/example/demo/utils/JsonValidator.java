package com.example.demo.utils;


import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashSet;
import java.util.Set;

public class JsonValidator {
    public static Set<ConstraintViolation> validateParam(JsonNode param, String... fieldNames) {
        Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
        for (String fieldName : fieldNames) {
            if (!param.has(fieldName)) {
                violations.add(new MyConstraintViolation<>(fieldName + " is null"));
            } else {
                if (isBlank(param.get(fieldName).asText())) {
                    violations.add(new MyConstraintViolation<>(param.get(fieldName).asText() + " is blank"));
                }
            }
        }
        return violations;
    }
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    private static class MyConstraintViolation<Object> implements ConstraintViolation<Object> {
        private final String message;

        public MyConstraintViolation(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;
        }

        @Override
        public Object getRootBean() {
            return null;
        }

        @Override
        public Object getLeafBean() {
            return null;
        }

        @Override
        public String getInvalidValue() {
            return message;
        }

        @Override
        public java.lang.Object[] getExecutableParameters() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public java.lang.Object getExecutableReturnValue() {
            // TODO Auto-generated method stub
            return message;
        }

        @Override
        public <U> U unwrap(Class<U> type) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getMessageTemplate() {
            // TODO Auto-generated method stub
            return message;
        }

        @Override
        public Path getPropertyPath() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Class<Object> getRootBeanClass() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
