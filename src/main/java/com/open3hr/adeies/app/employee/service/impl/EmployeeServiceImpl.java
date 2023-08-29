package com.open3hr.adeies.app.employee.service.impl;

import com.open3hr.adeies.app.employee.dto.EmployeeDTO;
import com.open3hr.adeies.app.employee.dto.EmployeeSupervisorDTO;
import com.open3hr.adeies.app.employee.dto.miniEmployeeDTO;
import com.open3hr.adeies.app.employee.entity.Employee;
import com.open3hr.adeies.app.employee.repository.EmployeeRepository;
import com.open3hr.adeies.app.employee.service.EmployeeService;
import com.open3hr.adeies.app.enums.Status;
import com.open3hr.adeies.app.exceptions.BadDataException;
import com.open3hr.adeies.app.exceptions.ConflictException;
import com.open3hr.adeies.app.exceptions.NotFoundException;
import com.open3hr.adeies.app.leaveBalance.entity.LeaveBalance;
import com.open3hr.adeies.app.leaveBalance.repository.LeaveBalanceRepository;
import com.open3hr.adeies.app.leaveCategory.entity.LeaveCategory;
import com.open3hr.adeies.app.leaveCategory.repository.LeaveCategoryRepository;
import com.open3hr.adeies.app.leaveRequest.dto.LeaveRequestDTO;
import com.open3hr.adeies.app.leaveRequest.entity.LeaveRequest;
import com.open3hr.adeies.app.leaveRequest.repository.LeaveRequestRepository;
import com.open3hr.adeies.app.user.entity.User;
import com.open3hr.adeies.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private LeaveBalanceRepository balanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveCategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<EmployeeSupervisorDTO> findAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeSupervisorDTO> employeeSupervisorList = employeeList.stream()
                .map(employee -> {
                    if (employee.getSupervisorId() == null) {
                        employee.setSupervisorId(0); // Set supervisorId to 0 if null
                    }
                    String supervisorLastName = employeeRepository.findById(employee.getSupervisorId())
                            .map(Employee::getLastName)
                            .orElse("");// Get supervisor's last name or set to empty string if not found
                    String supervisorFirstName = employeeRepository.findById(employee.getSupervisorId())
                            .map(Employee::getFirstName)
                            .orElse("");
                    return new EmployeeSupervisorDTO(employee, supervisorLastName, supervisorFirstName);
                })
                .collect(Collectors.toList());
        return employeeSupervisorList;
    }

    @Override
    public EmployeeDTO findEmployeeById(Integer id) {
        Optional<Employee> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return new EmployeeDTO(result.get());
        } else throw new NotFoundException("Δε βρέθηκε ο χρήστης με το ζητούμενο id: " + id);
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        return new EmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public LeaveRequestDTO addLeaveRequest(LeaveRequestDTO leaveRequestDTO, int employeeId) throws NotFoundException, ConflictException, BadDataException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);//Search for the employee
        if (optionalEmployee.isPresent()) {
            Optional<LeaveCategory> optionalLeaveCategory = categoryRepository.findCategoryByTitle(leaveRequestDTO.getLeaveTitle());//Search for leave category that has been requested
            if (optionalLeaveCategory.isPresent()) {
                Optional<LeaveBalance> foundBalanceOfEmployee = optionalEmployee.get().getLeaveBalanceList()
                        .stream()
                        .filter(balance -> Objects.equals(balance.getCategory().getTitle(), leaveRequestDTO.getLeaveTitle()))
                        .findAny();//Search for the balance of the leave category for this employee
                if (foundBalanceOfEmployee.isPresent()) {
                    LeaveBalance employeesBalance = foundBalanceOfEmployee.get();
                    Date submitDate = leaveRequestDTO.getSubmitDate();


                    if (leaveRequestDTO.getEndDate().getTime() >= leaveRequestDTO.getStartDate().getTime()) {
                        System.out.println("New leave request posted:");
                        System.out.println("submit date:" + leaveRequestDTO.getSubmitDate());
                        System.out.println("start date:" + leaveRequestDTO.getStartDate());
                        System.out.println("end date:" + leaveRequestDTO.getEndDate());
                        System.out.println(leaveRequestDTO.getStartDate().getTime() >= leaveRequestDTO.getSubmitDate().getTime());
                        if (leaveRequestDTO.getStartDate().getTime() >= leaveRequestDTO.getSubmitDate().getTime()) {

                            if (leaveRequestDTO.getDuration() <= employeesBalance.getDays() - employeesBalance.getDaysTaken()) {
                                leaveRequestDTO.setId(0);
                                leaveRequestDTO.setStatus(Status.PENDING);
                                leaveRequestDTO.setSubmitDate(submitDate);
                                employeesBalance.setDaysTaken(employeesBalance.getDaysTaken() + leaveRequestDTO.getDuration());
                                LeaveRequest leaveRequest = new LeaveRequest(leaveRequestDTO, optionalEmployee.get(), optionalLeaveCategory.get());
                                balanceRepository.save(employeesBalance);
                                leaveRequestRepository.save(leaveRequest);
                                return new LeaveRequestDTO(leaveRequest, optionalLeaveCategory.get());

                            } else
                                throw new ConflictException("Το υπόλοιπο ημερών δεν επαρκεί για την αίτηση άδειας αυτής της κατηγορίας (" + (employeesBalance.getDays() - employeesBalance.getDaysTaken()) + " ημέρες)");
                        } else
                            throw new ConflictException("H έναρξη της άδειας πρέπει να προηγείται της σημερινής ημέρας");
                    } else throw new ConflictException("Η έναρξη της άδειας πρέπει να είναι πριν τη λήξη της.");
                } else throw new BadDataException("Ο εργαζόμενος δεν δικαιούται άδειες αυτής της κατηγορίας.");
            } else throw new BadDataException("Δεν υπάρχει τέτοια κατηγορία άδειας");
        } else throw new NotFoundException("Δε βρέθηκε εργαζόμενους με το ζητούμενο id: " + employeeId);
    }

    @Override
    public List<EmployeeDTO> employeesWithoutAccount() {
        List<Employee> employees = employeeRepository.findEmployeesWithoutUser();
        return employees.stream()
                .map(EmployeeDTO::new)
                .toList();
    }

    @Override
    public EmployeeDTO changeProfile(EmployeeDTO employeeDTO, Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            //αν ο employee ειναι ανενεργος
            if (!employeeDTO.isEnabled()) {
                //αφαιρεσε τον σαν προισταμενο των subordinates του
                List<Employee> subordinates = employeeRepository.findAllSubordinatesOf(employeeDTO.getEmployeeId());
                for (Employee subordinate : subordinates) {
                    subordinate.setSupervisorId(null);
                }
            }
            employeeRepository.save(new Employee(employeeDTO));
            return employeeDTO;
        } else {
            throw new NotFoundException("Δε βρέθηκε ο χρήστης με το ζητούμενο id: " + id);
        }
    }

    @Override
    public EmployeeDTO assignToSupervisor(Integer employeeId, Integer supervisorId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        Optional<Employee> employee = employeeRepository.findById(supervisorId);
        //TODO: check if supervisor has is_supervisor=true !
        if (myEmployee.isPresent()) {
            if (employee.isPresent()) {
                myEmployee.get().setSupervisorId(supervisorId);
                employeeRepository.save(myEmployee.get());
                return new EmployeeDTO(myEmployee.get());
            } else
                throw new NotFoundException("Δε βρέθηκε ο χρήστης με το ζητούμενο id: " + employeeId);
        } else
            throw new NotFoundException("Δε βρέθηκε ο προιστάμενος με το ζητούμενο id: " + supervisorId);
    }

    @Override
    public EmployeeDTO unassignedToSupervisor(Integer employeeId, Integer supervisorId) {
        Optional<Employee> myEmployee = employeeRepository.findById(employeeId);
        if (myEmployee.isPresent()) {
            myEmployee.get().setSupervisorId(null);
            employeeRepository.save(myEmployee.get());
            return new EmployeeDTO(myEmployee.get());
        } else {
            throw new NotFoundException("Δε βρέθηκε ο χρήστης με το ζητούμενο id: " + employeeId);
        }
    }

    @Override
    public List<LeaveRequestDTO> requestHistoryOfEmployee(Integer employeeId) {
        List<LeaveRequest> myLeaveRequestHistory = leaveRequestRepository.leaveRequestHistoryOfEmployee(employeeId);
        return myLeaveRequestHistory.stream()
                .map(leaveRequest -> new LeaveRequestDTO(leaveRequest, leaveRequest.getCategory()))
                .toList();
    }

    @Override
    public EmployeeDTO findEmployeeByUserName(String username) {
        Optional<User> myUser = userRepository.findUserByUsername(username);
        if (myUser.isPresent()) {
            Optional<Employee> myEmployee = employeeRepository.findById(myUser.get().getEmployee().getId());
            if (myEmployee.isPresent()) {
                return new EmployeeDTO(myEmployee.get());
            } else {
                throw new NotFoundException("Δε βρέθηκε ο χρήστης με το ζητούμενο id: " + myUser.get().getEmployee().getId());
            }
        } else {
            throw new NotFoundException("Δε βρέθηκε αίτημα με το ζητούμενο id: " + username);
        }
    }

    @Override
    public LeaveRequestDTO approveLeaveRequest(Integer leaveReqId) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(leaveReqId);
        if (leaveRequest.isPresent()) {
            leaveRequest.get().setStatus(Status.APPROVED);
            leaveRequestRepository.save(leaveRequest.get());
            return new LeaveRequestDTO((leaveRequest.get()));
        }
        throw new NotFoundException("Δε βρέθηκε αίτημα με το ζητούμενο id: " + leaveRequest);
    }

    @Override
    public LeaveRequestDTO declineLeaveRequest(Integer leaveReqId) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(leaveReqId);
        if (leaveRequest.isPresent()) {
            try {
                leaveRequest.get().getEmployee().findBalanceOfCategory(leaveRequest.get().getCategory()).addDaysTaken(-leaveRequest.get().getDuration());
            } catch (Exception e) {
                throw new BadDataException("Δεν ήταν δυνατή η απόρριψη του αιτήματος.");
            }
            leaveRequest.get().setStatus(Status.DENIED);
            leaveRequestRepository.save(leaveRequest.get());
            return new LeaveRequestDTO(leaveRequest.get());
        }
        throw new NotFoundException("Δε βρέθηκε αίτημα με το ζητούμενο id: " + leaveRequest);
    }


    @Override
    public List<EmployeeSupervisorDTO> findAllDirectSubordinates(Integer supervisorId) {
        Optional<Employee> supervisor = employeeRepository.findById(supervisorId);
        if (supervisor.isEmpty())
            throw new NotFoundException("Δε βρέθηκε ο χρήστης με το ζητούμενο id: " + supervisorId);

        List<EmployeeSupervisorDTO> DTOSubordinates = new ArrayList<>();
        List<Employee> subordinatesList = employeeRepository.findAllSubordinatesOf(supervisorId);
        try {
            for (Employee subordinate : subordinatesList) {
                DTOSubordinates.add(new EmployeeSupervisorDTO(subordinate, employeeRepository.findById(subordinate.getSupervisorId()).get().getLastName(), employeeRepository.findById(subordinate.getSupervisorId()).get().getFirstName()));
            }
        } catch (Exception e) {
            throw new NotFoundException("Προέκυψε σφάλμα με την αναζήτηση των υφισταμένων");
        }

        return DTOSubordinates;
    }

    @Override
    public List<EmployeeSupervisorDTO> findAllSubordinates(Integer supervisorId) {
        Optional<Employee> supervisor = employeeRepository.findById(supervisorId);
        if (supervisor.isEmpty())
            throw new NotFoundException("Δε βρέθηκε ο χρήστης με το ζητούμενο id: " + supervisorId);

        List<Employee> subordinatesList = employeeRepository.findAllSubordinatesOf(supervisorId);
        List<EmployeeSupervisorDTO> DTOSubordinates = new ArrayList<>();

        try {
            for (Employee subordinate : subordinatesList) {
                DTOSubordinates.add(new EmployeeSupervisorDTO(subordinate, employeeRepository.findById(subordinate.getSupervisorId()).get().getLastName(), employeeRepository.findById(subordinate.getSupervisorId()).get().getFirstName()));
                if (subordinate.getId() != supervisorId) { //infinite loop prevention
                    DTOSubordinates.addAll(findAllSubordinates(subordinate.getId()));
                }
            }
        } catch (Exception e) {
            throw new NotFoundException("Προέκυψε σφάλμα με την αναζήτηση των υφισταμένων");
        }

        return DTOSubordinates;
    }

    @Override
    public List<miniEmployeeDTO> getFilteredSupervisors(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        List<Employee> employeeList = new ArrayList<>();
        if(employee.isPresent()){
            List<Employee> supervisors = employeeRepository.findAllSupervisors();
             List<EmployeeSupervisorDTO> subordinates =  findAllSubordinates(employee.get().getId());

             for (Employee supervisor: supervisors){
                 boolean flag = true;
                 for(EmployeeSupervisorDTO subordinate : subordinates){
                     if(Objects.equals(subordinate.getEmployeeId(), supervisor.getId())){
                         flag= false;
                         break;
                     }
                 }
                 if (flag) {
                     employeeList.add(supervisor);
                 }
             }
             return employeeList.stream()
                    .map(miniEmployeeDTO::new)
                    .toList();
        }
        else throw new NotFoundException("Δε βρέθηκε υπάλληλος με το ζητούμενο id: " + id);
    }

    @Override
    public List<miniEmployeeDTO> findAllSupervisors() {
        List<Employee> supervisors = employeeRepository.findAllSupervisors();

        return supervisors.stream()
                .map(miniEmployeeDTO::new)
                .toList();
    }


}
