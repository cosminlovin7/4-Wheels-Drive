package com.upb.fourwheelsdrive.model.register;

import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(
        name="registration_token"
)
public class RegisterActivationToken {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "register_activation_token_sequence"
    )
    @SequenceGenerator(
            name = "register_activation_token_sequence",
            sequenceName = "register_activation_token_sequence",
            allocationSize = 1
    )
    private Long id;
    private String token;
    private LocalDateTime generatedAt;
    private LocalDateTime expiringAt;
    private boolean activated = false;
    @OneToOne
    @JoinColumn(
        nullable = false,
        name = "user_id"
    )
    private ApplicationUser applicationUser;

    public RegisterActivationToken(final String token,
                                   final LocalDateTime generatedAt,
                                   final LocalDateTime expiringAt,
                                   final ApplicationUser applicationUser) {
        this.token = token;
        this.generatedAt = generatedAt;
        this.expiringAt = expiringAt;
        this.applicationUser = applicationUser;
    }
}
