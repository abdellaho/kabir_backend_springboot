package com.kabir.kabirbackend.config.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS    = "0123456789";
    private static final String SPECIAL   = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final int DEFAULT_LENGTH = 12;
    private static final int MIN_LENGTH     = 8;

    private final SecureRandom random = new SecureRandom();

    // ── Configuration ────────────────────────────────────────────────────────
    private final boolean useUppercase;
    private final boolean useLowercase;
    private final boolean useDigits;
    private final boolean useSpecial;
    private final int     length;

    private PasswordGenerator(Builder builder) {
        this.useUppercase = builder.useUppercase;
        this.useLowercase = builder.useLowercase;
        this.useDigits    = builder.useDigits;
        this.useSpecial   = builder.useSpecial;
        this.length       = builder.length;
    }

    // ── Core Generation ──────────────────────────────────────────────────────

    /**
     * Generates a password guaranteed to contain at least one character
     * from every enabled character set, with the remaining positions filled
     * randomly and the whole result shuffled.
     */
    public String generate() {
        List<Character> pool     = buildPool();
        List<Character> password = new ArrayList<>(length);

        // Guarantee one character per enabled category
        if (useUppercase) password.add(randomChar(UPPERCASE));
        if (useLowercase) password.add(randomChar(LOWERCASE));
        if (useDigits)    password.add(randomChar(DIGITS));
        if (useSpecial)   password.add(randomChar(SPECIAL));

        // Fill the remaining slots from the combined pool
        for (int i = password.size(); i < length; i++) {
            password.add(pool.get(random.nextInt(pool.size())));
        }

        Collections.shuffle(password, random);
        return listToString(password);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private List<Character> buildPool() {
        StringBuilder sb = new StringBuilder();
        if (useUppercase) sb.append(UPPERCASE);
        if (useLowercase) sb.append(LOWERCASE);
        if (useDigits)    sb.append(DIGITS);
        if (useSpecial)   sb.append(SPECIAL);
        List<Character> pool = new ArrayList<>();
        for (char c : sb.toString().toCharArray()) pool.add(c);
        return pool;
    }

    private char randomChar(String source) {
        return source.charAt(random.nextInt(source.length()));
    }

    private String listToString(List<Character> chars) {
        StringBuilder sb = new StringBuilder(chars.size());
        chars.forEach(sb::append);
        return sb.toString();
    }

    // ── Static convenience factory ────────────────────────────────────────────

    /** Generates a secure 12-character password with all character types. */
    public static String generateDefault() {
        return new Builder().build().generate();
    }

    // ── Builder ───────────────────────────────────────────────────────────────

    public static final class Builder {
        private boolean useUppercase = true;
        private boolean useLowercase = true;
        private boolean useDigits    = true;
        private boolean useSpecial   = true;
        private int     length       = DEFAULT_LENGTH;

        public Builder length(int length) {
            if (length < MIN_LENGTH)
                throw new IllegalArgumentException(
                        "Password length must be at least " + MIN_LENGTH);
            this.length = length;
            return this;
        }

        public Builder withUppercase(boolean value) { this.useUppercase = value; return this; }
        public Builder withLowercase(boolean value) { this.useLowercase = value; return this; }
        public Builder withDigits   (boolean value) { this.useDigits    = value; return this; }
        public Builder withSpecial  (boolean value) { this.useSpecial   = value; return this; }

        public PasswordGenerator build() {
            boolean anyEnabled = useUppercase || useLowercase || useDigits || useSpecial;
            if (!anyEnabled)
                throw new IllegalStateException("At least one character set must be enabled.");
            long enabledCount = Stream.of(useUppercase, useLowercase, useDigits, useSpecial).filter(b -> b).count();
            if (length < enabledCount)
                throw new IllegalStateException(
                        "Length must be >= number of enabled character sets (" + enabledCount + ").");
            return new PasswordGenerator(this);
        }
    }

    // ── Demo ─────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        // Default (all sets, length 12)
        System.out.println("Default  : " + PasswordGenerator.generateDefault());

        // Custom: digits + lowercase only, length 16
        String custom = new PasswordGenerator.Builder()
                .length(16)
                .withUppercase(true)
                .withSpecial(false)
                .build()
                .generate();
        System.out.println("Custom   : " + custom);
    }
}