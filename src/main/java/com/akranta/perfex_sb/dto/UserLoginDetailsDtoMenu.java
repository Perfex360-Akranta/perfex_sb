package com.akranta.perfex_sb.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record UserLoginDetailsDtoMenu(String keyid, String username, String privilege, Long authversion, String sessionid) {
}
