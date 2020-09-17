package com.carRental.domain.dto.emailVerificationApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailVerificationDto {

    @JsonProperty("formatCheck")
    private String formatCheck;

    @JsonProperty("smtpCheck")
    private String smtpCheck;

    @JsonProperty("dnsCheck")
    private String dnsCheck;
}
