package com.open3hr.adeies;

import com.open3hr.adeies.app.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc

class AdeiesApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private EmployeeRepositoryH2 employeeRepositoryH2;
	@Autowired
	private LeaveBalanceRepositoryH2 leaveBalanceRepositoryH2;
	@Autowired
	private LeaveCategoryRepositoryH2 leaveCategoryRepositoryH2;
	@Autowired
	private LeaveRequestRepositoryH2 leaveRequestRepositoryH2;
	@Autowired
	private UserRepositoryH2 userRepositoryH2;

}
