package com.carRental.domain.dto;

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
    private boolean formatCheck;

    @JsonProperty("smtpCheck")
    private boolean smtpCheck;

    @JsonProperty("dnsCheck")
    private boolean dnsCheck;
}
