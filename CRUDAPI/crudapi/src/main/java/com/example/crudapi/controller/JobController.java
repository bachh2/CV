package com.example.crudapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.crudapi.enums.Location;
import com.example.crudapi.exception.ErrorResponse;
import com.example.crudapi.exception.NotFoundException;
import com.example.crudapi.model.dto.JobDto;
import com.example.crudapi.model.request.CreateJobRequest;
import com.example.crudapi.model.request.UpdateJobRequest;
import com.example.crudapi.service.JobService;

import javax.validation.Valid;
import java.util.List;

import static com.example.crudapi.exception.Message.*;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<?> getAllJob() {
        try {
            List<JobDto> listJob = jobService.getAllJob();
            return ResponseEntity.ok(listJob);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listNoJob);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable("id") String id) {
        try {
            JobDto job = jobService.getJobById(id);
            return ResponseEntity.ok(job);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND, jobWithID + id + " not found"));
        }
    }

    @GetMapping("/sortbylocation")
    public ResponseEntity<?> getAllJobSortByLocation() {
        try {
            List<JobDto> listJobSortByLocation = jobService.getAllJobSortByLocation();
            return ResponseEntity.status(HttpStatus.OK).body(listJobSortByLocation);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listNoJob);
        }
    }

    @GetMapping("/salary/{salary}")
    public ResponseEntity<?> getJobBySalary(@PathVariable(value = "salary") int salary) {
        try {
            List<JobDto> listJobBySalary = jobService.getJobBySalary(salary);
            return ResponseEntity.status(HttpStatus.OK).body(listJobBySalary);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no job in the salary range");
        }
    }

    @PostMapping
    public ResponseEntity<?> createNewJob(@RequestBody @Valid CreateJobRequest createJobRequest) {
        JobDto newJob = jobService.createNewJob(createJobRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable("id") String id,
            @RequestBody @Valid UpdateJobRequest UpdateJobRequest) {
        try {
            JobDto jobDto = jobService.updateJob(id, UpdateJobRequest);
            return ResponseEntity.status(HttpStatus.OK).body(jobDto);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobWithID + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable("id") String id) {
        try {
            JobDto deletedJob = jobService.deleteJob(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedJob);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobWithID + id + " not found");
        }
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<?> getJobByTitleAndDescription(@PathVariable("keyword") String keyword) {
        try {
            List<JobDto> listJobByTitleAndDescription = jobService.getJobByTitleAndDescription(keyword);
            return ResponseEntity.status(HttpStatus.OK).body(listJobByTitleAndDescription);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No job contains " + keyword + " in title and description");
        }
    }

    @GetMapping("/query")
    public ResponseEntity<?> getJobByTitleDescriptionAndLocation(@RequestParam(name = "location") Location location,
            @RequestParam(name = "keyword") String keyword) {
        try {
            List<JobDto> listJobByTitleAndDescription = jobService.getJobByTitleDescriptionAndLocation(location,
                    keyword);
            return ResponseEntity.status(HttpStatus.OK).body(listJobByTitleAndDescription);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundJobMessage);
        }
    }
}