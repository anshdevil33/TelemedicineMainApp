package com.example.TeleMedicine;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.TeleMedicine.FieldDoctorsList.DoctorsListRepository;

@SpringBootTest
class TeleMedicineApplicationTests {
	@Autowired
	private DoctorsListRepository doctorListRepository;

	@Nested
	class Feild_Application {
		@Test
		public void Doctor_list() {
			List<Map<String, Object>> list = doctorListRepository.getDoctorsList();
			System.out.println(list);
			assertNotNull(list);
			assertFalse(list.isEmpty());
			System.out.println(list.size());
			for (Map<String, Object> doctor : list) {

				assertNotNull(doctor.get("doctor_id"));
				System.out.println(doctor.get("doctor_id"));
				assertNotNull(doctor.get("doctor_name"));
				assertNotNull(doctor.get("mobile"));
				assertNotNull(doctor.get("email"));
				assertNotNull(doctor.get("img"));
				System.out.println(doctor.get("img"));
				assertNotNull(doctor.get("state"));
				assertNotNull(doctor.get("city"));
				assertNotNull(doctor.get("gender"));
				assertNotNull(doctor.get("license"));
				assertNotNull(doctor.get("cost"));
				assertNotNull(doctor.get("dept_name"));
				assertNotNull(doctor.get("experience"));
				assertNotNull(doctor.get("about"));
				assertNotNull(doctor.get("status"));

				Object session_time = doctor.get("session_time");
				System.out.println(session_time + "session_time");
				if (session_time != null) {
					assertTrue(session_time instanceof Integer);
				}
				Object specilaization = doctor.get("specilaization");
				System.out.println(specilaization + "specilaization");
				if (specilaization != null) {
					assertTrue(specilaization instanceof String);
				}
				Object from_time = doctor.get("from_time");
				System.out.println(from_time + "from_time");
				if (from_time != null) {
					assertFalse(from_time instanceof String);
				}
				Object to_time = doctor.get("to_time");
				System.out.println(to_time + "to_time");
				if (to_time != null) {
					assertFalse(to_time instanceof String);
				}

			}

		}
	}
}