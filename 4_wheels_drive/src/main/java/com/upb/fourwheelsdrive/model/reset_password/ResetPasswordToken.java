package com.upb.fourwheelsdrive.model.reset_password;

import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class ResetPasswordToken {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reset_password_token_sequence"
    )
    @SequenceGenerator(
            name = "reset_password_token_sequence",
            sequenceName = "reset_password_token_sequence",
            allocationSize = 1
    )
    private Long id;
    private String token;
    private LocalDateTime generatedAt;
    private LocalDateTime expiringAt;
    private boolean used = false;
    @OneToOne
    @JoinColumn(
        nullable = false,
        name="user_id"
    )
    private ApplicationUser applicationUser;

    public ResetPasswordToken(
            final String token,
            final LocalDateTime generatedAt,
            final LocalDateTime expiringAt,
            final ApplicationUser applicationUser
    ) {
        this.token = token;
        this.generatedAt = generatedAt;
        this.expiringAt = expiringAt;
        this.applicationUser = applicationUser;
    }

}
