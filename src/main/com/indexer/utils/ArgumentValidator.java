package com.indexer.utils;

import java.util.Arrays;

public class ArgumentValidator {

    public static void validateFreqArguments(String[] args) {
        if (args.length != 3 || !isPositiveNumber(args[1])) {
            throw new IllegalArgumentException("Usage: --freq N FILE. N must be a positive number.");
        }
    }

    public static void validateFreqWordArguments(String[] args) {
        if (args.length != 3 || isNumberOrPunctuation(args[1])) {
            throw new IllegalArgumentException("Usage: --freq-word WORD FILE. WORD must be valid.");
        }
    }

    public static void validateSearchArguments(String[] args) {
        if (args.length < 3 || areAllNumbersOrPunctuation(args[1].split("\\s+"))) {
            throw new IllegalArgumentException("Usage: --search TERM FILE [FILE ...]. TERM must be valid.");
        }
    }

    private static boolean isPositiveNumber(String str) {
        return str.matches("\\d+");
    }

    private static boolean isNumberOrPunctuation(String str) {
        return str.matches("[0-9\\p{Punct}]+");
    }

    private static boolean areAllNumbersOrPunctuation(String[] strings) {
        return Arrays.stream(strings).allMatch(ArgumentValidator::isNumberOrPunctuation);
    }
}
