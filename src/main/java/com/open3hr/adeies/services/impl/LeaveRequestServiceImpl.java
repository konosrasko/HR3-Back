package com.open3hr.adeies.services.impl;

import com.open3hr.adeies.dto.EmployeeDTO;
import com.open3hr.adeies.dto.LeaveRequestDTO;
import com.open3hr.adeies.entities.Employee;
import com.open3hr.adeies.entities.LeaveRequest;
import com.open3hr.adeies.repositories.EmployeeRepository;
import com.open3hr.adeies.repositories.LeaveRequestRepository;
import com.open3hr.adeies.services.EmployeeService;
import com.open3hr.adeies.services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService{

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public LeaveRequestDTO save(LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest = new LeaveRequest(leaveRequestDTO);
        leaveRequestRepository.save(leaveRequest);
        return leaveRequestDTO;
    }

    @Override
    public List<LeaveRequestDTO> findAll(){
        return leaveRequestRepository.findAll().stream().map(LeaveRequestDTO::new).toList();
    }

    @Override
    public LeaveRequestDTO findById(Integer id) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(id);
        if (leaveRequest.isPresent()){
            return new LeaveRequestDTO(leaveRequest.get());
        }else {
            throw new RuntimeException("Couldn't find request with id: "+id);
        }
    }

    @Override
    public void deleteById(Integer id){
        this.leaveRequestRepository.deleteById(id);
    }

    @Override
    public List<LeaveRequestDTO> findRequestsForAnEmployee(int id) {

       Optional<Employee> employeeOptional= employeeRepository.findById(id);
       if(employeeOptional.isPresent())
       {
           return filterTheListById(id,employeeOptional.get().getId());
       }
     else
         return null;
    }

    public List<LeaveRequestDTO>filterTheListById(int id,int employeeId)
    {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        leaveRequests.stream().filter(leaveRequest -> employeeId == id).collect(Collectors.toList());
        return leaveRequests.stream().map(LeaveRequestDTO::new).toList();
    }
}
