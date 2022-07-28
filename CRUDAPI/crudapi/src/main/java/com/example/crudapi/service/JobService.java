package com.example.crudapi.service;

import org.springframework.stereotype.Service;
import com.example.crudapi.enums.Location;
import com.example.crudapi.model.dto.JobDto;
import com.example.crudapi.model.request.CreateJobRequest;
import com.example.crudapi.model.request.UpdateJobRequest;

import java.util.List;

@Service
public interface JobService {
    List<JobDto> getAllJob();

    JobDto getJobById(String id);

    List<JobDto> getAllJobSortByLocation();

    List<JobDto> getJobBySalary(int salary);

    JobDto createNewJob(CreateJobRequest createJobRequest);

    JobDto updateJob(String id, UpdateJobRequest UpdateJobRequest);

    JobDto deleteJob(String id);

    List<JobDto> getJobByTitleAndDescription(String keyword);

    List<JobDto> getJobByTitleDescriptionAndLocation(Location location, String keywordDescription);
}
