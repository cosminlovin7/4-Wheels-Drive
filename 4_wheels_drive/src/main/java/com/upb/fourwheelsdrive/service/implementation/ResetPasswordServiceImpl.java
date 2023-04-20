package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.reset_password.ResetPasswordRequest;
import com.upb.fourwheelsdrive.model.reset_password.ResetPasswordToken;
import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import com.upb.fourwheelsdrive.repository.ResetPasswordRepository;
import com.upb.fourwheelsdrive.service.EmailSender;
import com.upb.fourwheelsdrive.service.ResetPasswordService;
import com.upb.fourwheelsdrive.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final ApplicationUserServiceImpl applicationUserService;
    private final ResetPasswordRepository resetPasswordRepository;
    private final EmailSender emailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public String handleResetPasswordRequest(String username) {
        //throws UsernameNotFoundException, no need to check again
        ApplicationUser userDetails = (ApplicationUser)applicationUserService.loadUserByUsername(username);

        ResetPasswordToken newResetPasswordToken = applicationUserService.generateResetPasswordToken(userDetails);
        Optional<ResetPasswordToken> resetPasswordToken = resetPasswordRepository.findByUserId(userDetails.getId());

        resetPasswordToken.ifPresent(passwordToken -> newResetPasswordToken.setId(passwordToken.getId()));

        resetPasswordRepository.save(newResetPasswordToken);

        String resetPasswordLink = "http://localhost:8080/reset-password/reset?token="
                + newResetPasswordToken.getToken()
                + "&username=" + username;

        log.info(resetPasswordLink);

        emailSender.sendResetPasswordEmail(userDetails.getEmail(), buildEmail(username, resetPasswordLink));

        return Constants.RESET_PASSWORD_EMAIL_SENT;
    }

    @Override
    @Transactional
    public String resetPassword(String token, String username, ResetPasswordRequest resetPasswordRequest) {
        ApplicationUser applicationUser = (ApplicationUser)applicationUserService.loadUserByUsername(username);

        Optional<ResetPasswordToken> resetPasswordToken
                = resetPasswordRepository.findByTokenAndUserId(token, applicationUser.getId());

        if (resetPasswordToken.isPresent()
        && !resetPasswordToken.get().isUsed()
        && resetPasswordToken.get().getExpiringAt().isAfter(LocalDateTime.now())) {
            String newPassword = bCryptPasswordEncoder.encode(resetPasswordRequest.getPassword());
            applicationUser.setPassword(newPassword);
            resetPasswordToken.get().setUsed(true);

            resetPasswordRepository.save(resetPasswordToken.get());
            applicationUserService.saveUser(applicationUser);
        } else {
            throw new BaseException(Constants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }

        return Constants.PASSWORD_RESET_SUCCESSFULLY;
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reset your password</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Please, use the link below to reset your password: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Reset password</a> </p></blockquote>\n" + link + "\n\nLink will expire in 15 minutes. <p>See you soon!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
