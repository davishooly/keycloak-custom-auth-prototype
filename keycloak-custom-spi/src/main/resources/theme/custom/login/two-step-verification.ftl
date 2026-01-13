<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('phoneNumber','verificationCode'); section>
    <#if section = "header">
        <#if step == "1">
            ${msg("twoStepVerificationTitle")}
        <#else>
            ${msg("twoStepVerificationCodeTitle")}
        </#if>
    <#elseif section = "form">
        <form id="kc-form-login" action="${url.loginAction}" method="post">
            <#if step == "1">
                <div class="${properties.kcFormGroupClass!}">
                    <label for="phoneNumber" class="${properties.kcLabelClass!}">
                        ${msg("phoneNumber")}
                    </label>
                    <input type="tel" id="phoneNumber" name="phoneNumber"
                           class="${properties.kcInputClass!}"
                           autofocus
                           placeholder="+1234567890"
                           pattern="^\+?[1-9]\d{1,14}$"
                           title="${msg('phoneNumberFormat')}"
                           aria-invalid="<#if messagesPerField.existsError('phoneNumber')>true</#if>"/>
                    <#if messagesPerField.existsError('phoneNumber')>
                        <span class="${properties.kcInputErrorMessageClass!}"
                              aria-live="polite">
                            ${kcSanitize(messagesPerField.get('phoneNumber'))?no_esc}
                        </span>
                    </#if>
                    <small class="${properties.kcFormHelperTextClass!}">
                        ${msg("phoneNumberHelp")}
                    </small>
                </div>
            <#else>
                <div class="${properties.kcFormGroupClass!}">
                    <p>${msg("codeSentTo", phoneNumber)}</p>

                    <#if verificationCode??>
                        <div style="background: #fef3cd; padding: 15px; border-radius: 8px; margin: 15px 0; border: 1px solid #f0e68c;">
                            <strong>🔐 Demo Mode</strong><br/>
                            ${msg("demoModeCode", verificationCode)}
                        </div>
                    </#if>

                    <#if remainingAttempts??>
                        <div style="background: #fff3cd; padding: 10px; border-radius: 4px; margin-bottom: 15px;">
                            ⚠️ ${msg("attemptsRemaining", remainingAttempts)}
                        </div>
                    </#if>

                    <label for="verificationCode" class="${properties.kcLabelClass!}">
                        ${msg("verificationCode")}
                    </label>
                    <input type="text" id="verificationCode" name="verificationCode"
                           class="${properties.kcInputClass!}"
                           autofocus
                           placeholder="000000"
                           maxlength="6"
                           pattern="\d{6}"
                           inputmode="numeric"
                           autocomplete="one-time-code"
                           aria-invalid="<#if messagesPerField.existsError('verificationCode')>true</#if>"/>
                    <#if messagesPerField.existsError('verificationCode')>
                        <span class="${properties.kcInputErrorMessageClass!}"
                              aria-live="polite">
                            ${kcSanitize(messagesPerField.get('verificationCode'))?no_esc}
                        </span>
                    </#if>
                    <small class="${properties.kcFormHelperTextClass!}">
                        ${msg("codeExpiresIn")}
                    </small>
                </div>
            </#if>

            <div class="${properties.kcFormGroupClass!}" style="margin-top: 20px;">
                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                           type="submit"
                           value="<#if step == '1'>${msg('doSubmit')}<#else>${msg('doVerify')}</#if>"/>
                    <#if step == "2">
                        <button type="button"
                                class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                                onclick="history.back();"
                                style="margin-top: 10px;">
                            ${msg("backToPhoneNumber")}
                        </button>
                    </#if>
                </div>
            </div>
        </form>
    </#if>
</@layout.registrationLayout>