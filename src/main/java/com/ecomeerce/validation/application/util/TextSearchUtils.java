package com.ecomeerce.validation.application.util;

import java.text.Normalizer;
import java.util.Locale;

public final class TextSearchUtils {

    private TextSearchUtils() {
    }

    public static String normalize(String value) {

        if (value == null || value.isBlank()) {
            return "";
        }

        String normalized = Normalizer.normalize(
                value,
                Normalizer.Form.NFD
        );

        return normalized
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[,.'´`]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public static boolean flexibleMatch(
            String source,
            String query) {

        String normalizedSource = normalize(source);
        String normalizedQuery = normalize(query);

        if (normalizedQuery.isBlank()) {
            return true;
        }

        // Here we can extract Exact, partial and type-ahead search.
        if (normalizedSource.contains(normalizedQuery)) {
            return true;
        }

        String[] sourceTokens = normalizedSource.split(" ");
        String[] queryTokens = normalizedQuery.split(" ");

        for (String queryToken : queryTokens) {

            boolean tokenMatched = false;

            for (String sourceToken : sourceTokens) {

                if (sourceToken.startsWith(queryToken)
                        || queryToken.startsWith(sourceToken)
                        || isSimilar(sourceToken, queryToken)) {

                    tokenMatched = true;
                    break;
                }
            }

            if (!tokenMatched) {
                return false;
            }
        }

        return true;
    }

    private static boolean isSimilar(
            String source,
            String query) {

        if (source.length() < 4 || query.length() < 4) {
            return false;
        }

        int allowedDistance =
                Math.max(source.length(), query.length()) >= 8
                        ? 2
                        : 1;

        return levenshteinDistance(source, query)
                <= allowedDistance;
    }

    static int levenshteinDistance(
            String left,
            String right) {

        int[] previous = new int[right.length() + 1];
        int[] current = new int[right.length() + 1];

        for (int j = 0; j <= right.length(); j++) {
            previous[j] = j;
        }

        for (int i = 1; i <= left.length(); i++) {

            current[0] = i;

            for (int j = 1; j <= right.length(); j++) {

                int substitutionCost =
                        left.charAt(i - 1) == right.charAt(j - 1)
                                ? 0
                                : 1;

                current[j] = Math.min(
                        Math.min(
                                current[j - 1] + 1,
                                previous[j] + 1
                        ),
                        previous[j - 1] + substitutionCost
                );
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[right.length()];
    }
}