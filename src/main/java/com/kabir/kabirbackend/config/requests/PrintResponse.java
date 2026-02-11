package com.kabir.kabirbackend.config.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintResponse {
    String etatName;
    byte[] responseBytes;
}
