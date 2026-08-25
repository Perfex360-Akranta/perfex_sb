package com.akranta.perfex_sb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Only the user-editable browser name is accepted from Angular.
 *
 * User identity, employee identity and role information are always
 * resolved by Spring Boot from the authenticated request.
 */
public record BrowserRegistrationCreateRequestDto(

        @NotBlank(message = "Browser name is required.")
        @Size(
                min = 3,
                max = 150,
                message = "Browser name must contain between 3 and 150 characters.")
        String terminalName) {
}
