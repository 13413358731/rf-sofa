package com.realfinance.sofa.cg.sup.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.sup.annotation.DecryptRequestBody;
import com.realfinance.sofa.cg.sup.exception.RfCgSupplierException;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAccountFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.common.notice.sms.Sms;
import com.realfinance.sofa.common.notice.sms.SmsSender;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Objects;

import static java.util.concurrent.TimeUnit.MINUTES;

@RestController
@Tag(name = "供应商账号")
@RequestMapping("/cg/sup/account")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    /**
     * 注册账号记录验证码的SESSION KEY
     */
    private static final String SESSION_KEY_SIGN_UP_AUTH_CODE = "SIGN_UP_CODE";
    /**
     * 找回密码记录验证码的SESSION KEY
     */
    private static final String SESSION_KEY_RETRIEVE_AUTH_CODE = "RETRIEVE_CODE";
    /**
     * 找回密码记录用户名的SESSION KEY
     */
    private static final String SESSION_KEY_RETRIEVE_USERNAME = "RETRIEVE_USERNAME";

    /**
     * 验证码有效时间，单位分钟
     */
    @Value("${rf.supweb.auth-code-effective-minute:5}")
    private int authCodeEffectiveMinute;

    /**
     * 返回验证码到前端
     */
    @Value("${rf.supweb.return-auth-code:false}")
    private boolean returnAuthCode;

    @SofaReference(interfaceType = CgSupplierAccountFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAccountFacade cgSupplierAccountFacade;

    @Resource
    private HttpServletRequest request;

    @Resource
    private SmsSender smsSender;

    @GetMapping("/signup/authcode")
    @Operation(summary = "注册-获取手机验证码")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> sendSignUpAuthCode(@RequestParam String mobile,
                                                     @RequestParam String tenantId) {
        HttpSession session = request.getSession(true);
        RpcUtils.setTenantId(tenantId);
        AuthCode authCode = sendAuthCode(mobile);
        session.setAttribute(SESSION_KEY_SIGN_UP_AUTH_CODE, authCode);
        String msg = String.format("验证码%s已发到号码为%s的手机上，%d分钟内有效。",
                returnAuthCode ? authCode.getCode() : "",
                mobile,
                authCodeEffectiveMinute);
        return  ResponseEntity.ok(msg);
    }

    @PostMapping("signup")
    @Operation(summary = "注册")
    @PreAuthorize("permitAll()")
    @DecryptRequestBody
    public ResponseEntity<?> signUp(@RequestBody @Validated SignUpDto signUp,
                                    @RequestParam String tenantId) {
        if (Objects.equals(signUp.getPassword(), signUp.getUsername())) {
            throw new RuntimeException("用户名密码不能一样");
        }
        if (!Objects.equals(signUp.getPassword(), signUp.getPassword2())) {
            throw new RuntimeException("两次密码不相同");
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.badRequest().body("会话不存在");
        }
        AuthCode authCode = (AuthCode) session.getAttribute(SESSION_KEY_SIGN_UP_AUTH_CODE);
        if (authCode == null) {
            return ResponseEntity.badRequest().body("验证码错误");
        }
        if (!Objects.equals(signUp.getMobile(),authCode.getMobile())) {
            throw new RuntimeException("手机号错误");
        }
        if (authCode.getErrorCount() > 5) {
            throw new RuntimeException("验证码错误次数 > 5，请重新获取验证码");
        }
        if ((System.currentTimeMillis() - authCode.getTimestamp()) > MINUTES.toMillis(authCodeEffectiveMinute)) {
            throw new RuntimeException("验证码已超时");
        }
        if (!Objects.equals(signUp.getCode(),authCode.getCode())) {
            session.setAttribute(SESSION_KEY_SIGN_UP_AUTH_CODE, authCode.incrErrorCount());
            throw new RuntimeException("验证码错误");
        }
        RpcUtils.setPrincipalId(0);
        RpcUtils.setTenantId(tenantId);
        CgSupplierAccountCreateDto createDto = new CgSupplierAccountCreateDto();
        createDto.setUsername(signUp.getUsername());
        createDto.setPassword(signUp.getPassword());
        createDto.setMobile(signUp.getMobile());
        createDto.setType("PORTAL");
        try {
            cgSupplierAccountFacade.createAccount(createDto);
            session.removeAttribute(SESSION_KEY_SIGN_UP_AUTH_CODE);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e instanceof RfCgSupplierException) {
                throw e;
            }
            log.error("调用创建账号接口异常", e);
            throw new RuntimeException("注册账号服务异常，请稍后重试");
        }
    }

    @PostMapping("resetpassword")
    @Operation(summary = "修改密码")
    @PreAuthorize("isAuthenticated()")
    @DecryptRequestBody
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDto resetPassword,
                                           Authentication authentication) {
        if ((resetPassword.getNewPassword()).equals(authentication.getName())){
            throw new RuntimeException("用户名密码不能一样");
        }
        try {
            cgSupplierAccountFacade.resetPassword(
                    authentication.getName(),resetPassword.getPassword(),
                    resetPassword.getNewPassword(),resetPassword.getNewPassword2());
        } catch (Exception e) {
            if (e instanceof RfCgSupplierException) {
                throw e;
            }
            log.error("调用修改密码接口异常", e);
            throw new RuntimeException("修改密码服务异常，请稍后重试");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usernameexists")
    @Operation(summary = "检查用户名是否已使用，返回true则为已使用")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Boolean> usernameExists(@RequestParam String username, @RequestParam String tenantId) {
        RpcUtils.setTenantId(tenantId);
        Boolean exist = cgSupplierAccountFacade.existsByUsername(username);
        return ResponseEntity.ok(exist);
    }

    @GetMapping("/retrievepassword/authcode")
    @Operation(summary = "找回密码-获取手机验证码")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> sendRetrieveAuthCode(@RequestParam String username, @RequestParam String tenantId) {
        RpcUtils.setTenantId(tenantId);
        CgSupplierAccountDto account = cgSupplierAccountFacade.getByUsername(username);
        String mobile = account.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            throw new RuntimeException("账号没有绑定手机");
        }
        HttpSession session = request.getSession(true);
        AuthCode authCode = sendAuthCode(mobile);
        session.setAttribute(SESSION_KEY_RETRIEVE_AUTH_CODE, authCode);
        session.setAttribute(SESSION_KEY_RETRIEVE_USERNAME, username);
        StringBuilder replace = new StringBuilder(mobile).replace(mobile.length() - 8, mobile.length() - 3, "*****");
        String msg = String.format("验证码%s已发到号码为%s的手机上，%d分钟内有效。",
                returnAuthCode ? authCode.getCode() : "",
                replace,
                authCodeEffectiveMinute);
        return ResponseEntity.ok(msg);
    }

    @PostMapping("retrievepassword")
    @Operation(summary = "找回密码")
    @PreAuthorize("permitAll()")
    @DecryptRequestBody
    public ResponseEntity<?> retrievePassword(@RequestBody @Validated RetrievePasswordDto retrievePassword,
                                              @RequestParam String tenantId) {
        RpcUtils.setTenantId(tenantId);
        if (!Objects.equals(retrievePassword.getPassword(), retrievePassword.getPassword2())) {
            throw new RuntimeException("两次密码不相同");
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.badRequest().build();
        }
        Object username = session.getAttribute(SESSION_KEY_RETRIEVE_USERNAME);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(retrievePassword.getUsername(), username)) {
            throw new RuntimeException("用户名错误");
        }
        AuthCode authCode = (AuthCode) session.getAttribute(SESSION_KEY_RETRIEVE_AUTH_CODE);
        if (authCode == null) {
            return ResponseEntity.badRequest().build();
        }
        if (authCode.getErrorCount() > 5) {
            throw new RuntimeException("验证码错误次数 > 5，请重新获取验证码");
        }
        if ((System.currentTimeMillis() - authCode.getTimestamp()) > MINUTES.toMillis(authCodeEffectiveMinute)) {
            throw new RuntimeException("验证码已超时");
        }
        if (!Objects.equals(retrievePassword.getCode(),authCode.getCode())) {
            session.setAttribute(SESSION_KEY_RETRIEVE_AUTH_CODE, authCode.incrErrorCount());
            throw new RuntimeException("验证码错误");
        }
        try {
            cgSupplierAccountFacade.resetPassword(retrievePassword.getUsername(),retrievePassword.getPassword());
            session.removeAttribute(SESSION_KEY_RETRIEVE_AUTH_CODE);
            session.removeAttribute(SESSION_KEY_RETRIEVE_USERNAME);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e instanceof RfCgSupplierException) {
                throw e;
            }
            log.error("调用重置密码接口异常", e);
            throw new RuntimeException("重置密码服务异常，请稍后重试");
        }
    }

    /**
     * 发送验证码
     * @param mobile
     * @return
     */
    private AuthCode sendAuthCode(String mobile) {
        AuthCode authCode = new AuthCode();
        authCode.setCode(createCode(6));
        authCode.setMobile(mobile);
        authCode.setTimestamp(System.currentTimeMillis());
        authCode.setErrorCount(0);
        Sms sms = new Sms();
        sms.setTenantId(RpcUtils.requireTenantId());
        // TODO: 2021/2/11 内容修改为配置
        sms.setContent(String.format(authCode.getCode(),authCodeEffectiveMinute));
        sms.setDestAddress(Collections.singletonList(mobile));
        smsSender.send(sms);
        return authCode;
    }

    /**
     * 创建随机字符串
     * @param count
     * @return
     */
    private String createCode(int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(RandomUtils.nextInt(0,9));
        }
        return sb.toString();
    }
}
