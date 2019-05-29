package com.server.server.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
