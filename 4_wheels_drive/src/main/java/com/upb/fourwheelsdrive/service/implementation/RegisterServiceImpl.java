package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.model.register.RegisterActivationToken;
import com.upb.fourwheelsdrive.model.user.ApplicationUser;
import com.upb.fourwheelsdrive.model.user.ApplicationUserRoles;
import com.upb.fourwheelsdrive.model.register.RegisterRequest;
import com.upb.fourwheelsdrive.service.EmailSender;
import com.upb.fourwheelsdrive.service.RegisterActivationService;
import com.upb.fourwheelsdrive.service.RegisterService;
import com.upb.fourwheelsdrive.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final ApplicationUserServiceImpl applicationUserService;
    private final RegisterActivationService registerActivationService;
    private final EmailSender emailSender;

    @Override
    public String register(RegisterRequest request) {
        String registerActivationToken = applicationUserService
                .registerUser(new ApplicationUser(
                        request.getUsername(),
                        request.getPassword(),
                        request.getEmail(),
                        ApplicationUserRoles.USER));

        String activationLink = "http://localhost:8080/register/confirm?username="
                + request.getUsername()
                + "&token="
                + registerActivationToken;

        log.info(activationLink);

        emailSender.sendEmailActivation(
                request.getEmail(),
                buildEmail(request.getUsername(), activationLink));

        return Constants.ACCOUNT_CREATED;
    }

    @Override
    public String activateAccount(String username, String token) {
        RegisterActivationToken registerActivationToken = registerActivationService
                .getRegisterActivationToken(token)
                .orElseThrow(() -> new BaseException(Constants.INVALID_REGISTRATION_TOKEN, HttpStatus.BAD_REQUEST));

        if (registerActivationToken.isActivated()) {
            throw new BaseException(Constants.ACCOUNT_ALREADY_ACTIVATED, HttpStatus.CONFLICT);
        }

        if (registerActivationToken.getExpiringAt().isBefore(LocalDateTime.now())) {
            throw new BaseException(Constants.REGISTRATION_TOKEN_EXPIRED, HttpStatus.BAD_REQUEST);
        }

        registerActivationService.activateAccount(username, token);

        return Constants.ACCOUNT_ACTIVATED;
    }

    @Override
    @Transactional
    public String resendActivationLink(String username) {
        ApplicationUser applicationUser = (ApplicationUser)applicationUserService.loadUserByUsername(username);

        if (applicationUser.isEnabled()) {
            throw new BaseException(Constants.ACCOUNT_ALREADY_ACTIVATED, HttpStatus.CONFLICT);
        }

        RegisterActivationToken oldRegisterActivationToken =
                registerActivationService.getTokenByUserId(applicationUser.getId());

        RegisterActivationToken newRegisterActivationToken =
                applicationUserService.generateRegisterToken(applicationUser);

        newRegisterActivationToken.setId(oldRegisterActivationToken.getId());

        registerActivationService.saveActivationToken(newRegisterActivationToken);

        String activationLink = "http://localhost:8080/register/confirm?username="
                + username
                + "&token="
                + newRegisterActivationToken.getToken();

        log.info(activationLink);

        emailSender.sendEmailActivation(
                applicationUser.getEmail(),
                buildEmail(username, activationLink));

        return Constants.ACTIVATION_LINK_RESENT;
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>" + link + "\n Link will expire in 15 minutes. <p>See you soon!</p>" +
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
