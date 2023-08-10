package com.example.TeleMedicine.PatientRegistration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientRegisterService {
	@Autowired
	private PatientRegisterRepository registerRepository;
	private Logger logger = LoggerFactory.getLogger(PatientRegisterService.class);

	public synchronized Map<String, Object> otpRequest(String mobile) {
		Map<String, Object> m = new LinkedHashMap<>();
		try {
			Map<String, Object> stat = registerRepository.otpRequest(mobile);
			String status = stat.get("status").toString();

			if (status.equalsIgnoreCase("0")) {
				m.put("message", "Patient Exists, Please Login");
				m.put("flag", false);
				m.put("status", 0);
			} else if (status.equalsIgnoreCase("1")) {
				String error = stat.get("error").toString();
				
				if (error.equalsIgnoreCase("000")) {
					m.put("message", "OTP Sent Successfully");
					m.put("flag", true);
					m.put("status", 1);
					m.put("time", 5);
					logger.debug("Registration OTP sent to " + mobile);
				} else if (error.equalsIgnoreCase("13")) {
					m.put("message", "Mobile Number Does'nt Exists");
					m.put("flag", false);
					m.put("status", 0);
				} else {
					m.put("message", "Error, while sending OTP");
					m.put("flag", false);
					m.put("status", 0);
				}
			} else if (status.equalsIgnoreCase("2")) {
				m.put("message", "Mobile Number is Not Valid");
				m.put("flag", false);
				m.put("status", 0);
			} else {
				m.put("message", "Error, while sending OTP");
				m.put("flag", false);
				m.put("status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("flag", false);
			m.put("message", "Error, while sending OTP");
			m.put("status", 0);
			logger.error("Error sending register OTP to " + mobile);
		}
		return m;
	}

	public synchronized Map<String, Object> verifyOtp(Map<String, Object> otpVerify) {
		Map<String, Object> m = new LinkedHashMap<>();
		try {
			Map<String, Object> otp = registerRepository.verifyOtp(otpVerify);
			String storedOtp = otp.get("otp").toString();
			String inputOtp = otpVerify.get("otp").toString();

			if (otp.get("validity").toString().equalsIgnoreCase("1")) {
				if (storedOtp.equals(inputOtp)) {
					m.put("message", "OTP Verification Successful");
					m.put("flag", true);
					m.put("status", 1);
				} else {
					m.put("message", "Incorrect OTP");
					m.put("flag", true);
					m.put("status", 2);
				}
			} else {
				if (storedOtp.equals(inputOtp)) {
					m.put("message", "OTP Expired");
					m.put("flag", true);
					m.put("status", 3);
				} else {
					m.put("message", "Incorrect OTP");
					m.put("flag", true);
					m.put("status", 4);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("message", "Error, during otp verification");
			m.put("flag", false);
			m.put("status", 0);
		}
		return m;
	}

	public synchronized Map<String, Object> register(PatientRegister register) {
		Map<String, Object> m = new LinkedHashMap<>();
		try {
			m.put("status", registerRepository.register(register));
			m.put("flag", true);
			m.put("message", "Registation Successfull");
			logger.debug(register.getMobile() + " Registation Successfull");
		} catch (Exception e) {
			e.printStackTrace();
			m.put("flag", false);
			m.put("message", "Registation Failed");
			m.put("status", 0);
			logger.error(register.getMobile() + " Registation Failed");
		}
		return m;
	}

}
