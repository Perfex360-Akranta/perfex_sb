package com.akranta.perfex_sb.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.AdmTlPwdhistory;
import com.akranta.perfex_sb.model.AdmTlUserRoleLink;
import com.akranta.perfex_sb.model.AdmTlUsercustompages;
import com.akranta.perfex_sb.model.AdmTlUsermst;
import com.akranta.perfex_sb.repository.AdmTlPwdhistoryRepository;
import com.akranta.perfex_sb.repository.AdmTlUserRoleLinkRepository;
import com.akranta.perfex_sb.repository.AdmTlUsercustompagesRepository;
import com.akranta.perfex_sb.repository.AdmTlUsermstRepository;
import com.akranta.perfex_sb.service.DbActionTemplate;
import com.akranta.perfex_sb.service.UserCreationService;
import com.akranta.perfex_sb.util.ValidationUtil;

@Service
public class UserCreationServiceImpl implements UserCreationService {
    @Autowired
    private DbActionTemplate dbActionTemplate;

    @Autowired
    private AdmTlUsercustompagesRepository usercustompagesRepository;

    @Autowired
    private AdmTlPwdhistoryRepository admTlPwdhistoryRepository;

    @Autowired
    private AdmTlUsermstRepository usermstRepository;

    @Autowired
    private AdmTlUserRoleLinkRepository userRoleLinkRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserCreationServiceImpl.class);

    @Override
    @Transactional
    public AdmTlUsermst saveUser(AdmTlUsermst usermst) throws Exception

    {
        String userKeyId = usermst.getKeyid();

        AdmTlUsermst resultUser = new AdmTlUsermst();
        AdmTlUsermst updateResult = new AdmTlUsermst();
        if (!ValidationUtil.isValidKeyId(userKeyId))

        {
            String newUserKeyId = dbActionTemplate.getSequenceNumber("ADM_TL_USERMST", 0, "", "", "");
            usermst.setKeyid(newUserKeyId);
            String pin = newUserKeyId.substring(3);
            int pinNumber = Integer.parseInt(pin);

            usermst.setUserpin(pinNumber);
            logger.info("Printing the User Password {} ", usermst.getPassword());

            String password = usermst.getPassword();
            password = encriptPassword(password, pinNumber);
            logger.info("Encrypted Password {} ", password);
            usermst.setPassword(password);
            usermst.setDefaultpassword(password);

            String customPageKeyId = dbActionTemplate.getSequenceNumber("ADM_TL_USERCUSTOMPAGES", 12, "USC", null,
                    null);
            logger.info("CUSTOM PAGES USER KEYID {} ", customPageKeyId);

            AdmTlUsercustompages usercustompages = new AdmTlUsercustompages();
            usercustompages.setKeyid(customPageKeyId);
            usercustompages.setUsrmKeyid(newUserKeyId);
            usercustompages.setPageuri("empEqp_input.base");
            usercustompages.setParams("{}");
            usercustompages.setFormheader("Home");
            usercustompages.setDisplayorder(1);
            usercustompages.setTempfield1('-');
            usercustompages.setTempfield2('-');
            usercustompages.setTempfield3('-');
            usercustompages.setTempfield4('-');
            usercustompages.setTempfield5('-');
            usercustompages.setActive('Y');
            usercustompages.setCreatedby("EMP0001");
            usercustompages.setCreatedon(usermst.getCreatedon());
            usercustompages.setModifiedon(usermst.getModifiedon());

            usercustompagesRepository.save(usercustompages);

            String pwdHistoryKeyId = dbActionTemplate.getSequenceNumber("ADM_TL_PWDHISTORY", 8, "PWH", null, null);
            AdmTlPwdhistory admTlPwdhistory = new AdmTlPwdhistory();
            admTlPwdhistory.setKeyid(pwdHistoryKeyId);
            admTlPwdhistory.setLastpwdchangedon(usermst.getLastpwdchanged());
            admTlPwdhistory.setPassword(usermst.getPassword());
            admTlPwdhistory.setPasswordno(BigDecimal.ZERO);
            admTlPwdhistory.setUserid(usermst.getKeyid());
            admTlPwdhistory.setActive(
                    usermst.getIsactive() != null && !usermst.getIsactive().isEmpty()
                            ? usermst.getIsactive().charAt(0)
                            : null);
            admTlPwdhistory.setCreatedby(usermst.getCreatedby());
            admTlPwdhistory.setCreatedon(usermst.getCreatedon());
            admTlPwdhistory.setModifiedon(usermst.getModifiedon());

            admTlPwdhistoryRepository.save(admTlPwdhistory);

            resultUser = usermstRepository.save(usermst);
            return resultUser;

        } else {

            if (usermstRepository.existsById(userKeyId)) {
                String keyId = usermst.getKeyid();
                String pin = keyId.substring((3));
                int pinNumber = Integer.parseInt(pin);

                usermst.setUserpin(pinNumber);
                usermst.setLastpwdchanged(LocalDateTime.now());
                usermst.setLoginatempt(0);

                String pwdHistoryKeyId = dbActionTemplate.getSequenceNumber("ADM_TL_PWDHISTORY", 8, "PWH", null, null);
                AdmTlPwdhistory admTlPwdhistory = new AdmTlPwdhistory();
                admTlPwdhistory.setKeyid(pwdHistoryKeyId);
                admTlPwdhistory.setLastpwdchangedon(usermst.getLastpwdchanged());
                admTlPwdhistory.setPassword(usermst.getPassword());
                admTlPwdhistory.setPasswordno(BigDecimal.ZERO);
                admTlPwdhistory.setUserid(usermst.getKeyid());
                admTlPwdhistory.setActive(
                        usermst.getIsactive() != null && !usermst.getIsactive().isEmpty()
                                ? usermst.getIsactive().charAt(0)
                                : null);
                admTlPwdhistory.setCreatedby(usermst.getCreatedby());
                admTlPwdhistory.setCreatedon(usermst.getCreatedon());
                admTlPwdhistory.setModifiedon(usermst.getModifiedon());

                int pwdNo = usermstRepository.getNextPasswordNo(userKeyId);
                int passwordRem = usermstRepository.getPasswordHistoryRememberCount();

                usermstRepository.updatePasswordHistory(userKeyId);

                updateResult = usermstRepository.save(usermst);

                admTlPwdhistoryRepository.save(admTlPwdhistory);

                if (pwdNo != 0) {
                    usermstRepository.deleteOldPasswordHistory(passwordRem, userKeyId);
                }

            }

        }
        return updateResult;

    }

    public String encriptPassword(String password, int userPin) throws Exception {
        int tempUserPin = getFormatedUserPin(userPin);
        System.out.println("USER PASSWORD" + password);
        String encriptPass = "";
        for (int i = 0; i < password.length(); i++) {
            int sum = password.charAt(i) + tempUserPin;
            encriptPass += (sum) < 128 ? (char) (sum) : (char) (32 + (sum - 128) + tempUserPin);
        }
        System.out.println("ENCRIPT PASS " + encriptPass);
        logger.info("ENCRIPT PASS {} ", encriptPass);
        return encriptPass;

    }

    private int getFormatedUserPin(int userPin) throws Exception {
        NumberFormat formatter = new DecimalFormat("0000");
        String pin = formatter.format(userPin);

        if (Integer.parseInt(pin) == 0)
            throw new RuntimeException("tpmusr-000005"); // invalid user pin

        int digit = 0, sum = 0;

        for (int i = 0; i < pin.length(); i++) {
            digit = (int) pin.charAt(i);
            sum += digit;
        }
        String sumStr = Integer.toString(sum);

        do {
            sum = 0;
            for (int i = 0; i < sumStr.length(); i++) {
                digit = (int) (sumStr.charAt(i) - '0');
                sum += digit;
            }
            sumStr = Integer.toString(sum);
        } while (sum > 9);
        return sum;
    }

    // Role Fetch

    public List<Map<String, Object>> findRolesByUserId(String userId) {
        logger.info("Key Id {}", userId);
        List<Map<String, Object>> result = usermstRepository.findRolesByUserId(userId);
        logger.info("RESULT SIZE = {}", result.size());
        logger.info("RESULT DATA = {}", result);
        for (Map<String, Object> row : result) {
            logger.info("roleId   = " + row.get("roleid"));
            logger.info("roleName = " + row.get("rolename"));
            logger.info("---------------");

        }
        return result;

    }

    public List<Map<String, Object>> findMultipleRolesByUserId(String userId) {
        List<AdmTlUserRoleLink> list = userRoleLinkRepository.findByUserid(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (AdmTlUserRoleLink link : list) {
            Map<String, Object> map = new LinkedHashMap<>();

            map.put("userid", link.getUserid());
            map.put("roleid", link.getRoleid());
            map.put("active", link.getActive());
            map.put("createdby", link.getCreatedby());
            map.put("createdon", link.getCreatedon());
            map.put("modifiedon", link.getModifiedon());

            result.add(map);
        }
        return result;

    }

    @Override
    public AdmTlUserRoleLink saveUserRoll(AdmTlUserRoleLink userRoleLink) {

        AdmTlUserRoleLink result = userRoleLinkRepository.save(userRoleLink);
        return result;
    }

    @Override
    public void deleteRole(String userId, String roleId) {

        userRoleLinkRepository.deleteByRoleIdAndUserId(roleId, userId);

    }

    @Override
    public AdmTlUsermst userRecall(String userKeyId) throws Exception {
        logger.info("mst key id {}", userKeyId);
        AdmTlUsermst mst = usermstRepository.findByLoginIdIgnoreCase(userKeyId);
        String password = mst.getPassword();
        String keyId = mst.getKeyid();
        String pin = keyId.substring(3);
        int pinValue = Integer.parseInt(pin);
        String passwordOrginal = decryptPassword(password, pinValue);
        mst.setPassword(passwordOrginal);
        return mst;

    }

    public String decryptPassword(String password, int userPin) throws Exception {
        int tempUserPin = getFormatedUserPin(userPin);

        String decriptPass = "";
        for (int i = 0; i < password.length(); i++) {
            int diff = password.charAt(i) - tempUserPin;
            decriptPass += (diff) > 32 ? (char) (diff) : (char) (127 - ((32 - diff) + tempUserPin));
        }
        return decriptPass;
    }

    @Override
    public AdmTlUserRoleLink updateUserRole(AdmTlUserRoleLink userRoleLink) {
        userRoleLinkRepository.insertUserRole(
                userRoleLink.getUserid(),
                userRoleLink.getRoleid(),
                userRoleLink.getActive(),
                userRoleLink.getCreatedby(),
                userRoleLink.getCreatedon(),
                userRoleLink.getModifiedon());

        return userRoleLink;
    }   

}
