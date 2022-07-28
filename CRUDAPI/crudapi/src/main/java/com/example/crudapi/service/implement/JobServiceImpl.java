package com.example.crudapi.service.implement;

import org.springframework.stereotype.Component;
import com.example.crudapi.entity.Job;
import com.example.crudapi.enums.Location;
import com.example.crudapi.exception.NotFoundException;
import com.example.crudapi.model.dto.JobDto;
import com.example.crudapi.model.mapper.JobMapper;
import com.example.crudapi.model.request.CreateJobRequest;
import com.example.crudapi.model.request.UpdateJobRequest;
import com.example.crudapi.service.JobService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.crudapi.exception.Message.listNoJob;
import static com.example.crudapi.exception.Message.notFoundJobMessage;

@Component
public class JobServiceImpl implements JobService {
    private static final List<Job> jobs = new ArrayList<>();

    static {
        jobs.add(new Job("1", "Tuyển dụng java dev", "Java", Location.HA_NOI, 500, 2000,
                "example@gmail.com"));
        jobs.add(new Job("2", "Tuyển dụng Front end", "JavaScript", Location.HO_CHI_MINH, 400, 1500,
                "example1@gmail.com"));
        jobs.add(new Job("3", "Tuyển dụng back end", "Node.js", Location.DA_NANG, 1000, 2000,
                "example2@gmail.com"));
        jobs.add(new Job("4", "Tuyển dụng auto test, BA", "Automation tester ", Location.DA_NANG, 300, 2500,
                "example3@gmail.com"));
        jobs.add(new Job("5", "Tuyển dụng fullstack", "CSS,JS,HTML,Node.JS", Location.HA_NOI, 100, 1000,
                "example4@gmail.com"));
    }

    @Override
    public List<JobDto> getAllJob() {
        if (!jobs.isEmpty()) {
            List<JobDto> result = new ArrayList<>();
            for (Job job : jobs) {
                result.add(JobMapper.toJobDto(job));
            }
            return result;
        }
        throw new NotFoundException(listNoJob);
    }

    @Override
    public JobDto getJobById(String id) {
        for (Job job : jobs) {
            if (job.getId().equals(id)) {
                return JobMapper.toJobDto(job);
            }
        }
        throw new NotFoundException(notFoundJobMessage);
    }

    @Override
    public List<JobDto> getAllJobSortByLocation() {
        if (!jobs.isEmpty()) {
            List<JobDto> result = new ArrayList<>();
            for (Job job : jobs) {
                result.add(JobMapper.toJobDto(job));
            }
            return result.stream().sorted(Comparator.comparing(JobDto::getLocation)).collect(Collectors.toList());

        }
        throw new NotFoundException(listNoJob);
    }

    @Override
    public List<JobDto> getJobBySalary(int salary) {
        if (!jobs.isEmpty()) {
            List<JobDto> result = new ArrayList<>();
            for (Job job : jobs) {
                result.add(JobMapper.toJobDto(job));
            }
            return result.stream().filter(JobDto -> JobDto.getMinSalary() <= salary)
                    .filter(JobDto -> JobDto.getMaxSalary() >= salary).collect(Collectors.toList());
        }
        throw new NotFoundException(notFoundJobMessage);
    }

    @Override
    public JobDto createNewJob(CreateJobRequest createJobRequest) {
        String uuid = UUID.randomUUID().toString();
        Job newJob = new Job();
        newJob.setId(uuid);
        newJob.setTitle(createJobRequest.getTitle());
        newJob.setDescription(createJobRequest.getDescription());
        newJob.setLocation(createJobRequest.getLocation());
        newJob.setMinSalary(createJobRequest.getMinSalary());
        newJob.setMaxSalary(createJobRequest.getMaxSalary());
        newJob.setEmailTo(createJobRequest.getEmailTo());
        jobs.add(newJob);
        return JobMapper.toJobDto(newJob);
    }

    @Override
    public JobDto updateJob(String id, UpdateJobRequest updateJobRequest) {
        for (Job job : jobs) {
            if (job.getId().equals(id)) {
                job.setTitle(updateJobRequest.getTitle());
                job.setDescription(updateJobRequest.getDescription());
                job.setLocation(updateJobRequest.getLocation());
                job.setMaxSalary(updateJobRequest.getMinSalary());
                job.setMaxSalary(updateJobRequest.getMaxSalary());
                job.setEmailTo(updateJobRequest.getEmailTo());
                return JobMapper.toJobDto(job);
            }
        }
        throw new NotFoundException(notFoundJobMessage);
    }

    @Override
    public JobDto deleteJob(String id) {
        for (Job job : jobs) {
            if (job.getId().equals(id)) {
                jobs.remove(job);
                return JobMapper.toJobDto(job);
            }
        }
        throw new NotFoundException(notFoundJobMessage);
    }

    @Override
    public List<JobDto> getJobByTitleAndDescription(String keyword) {
        List<Job> jobList = jobs.stream()
                .filter(job -> job.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || job.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        if (!jobList.isEmpty()) {
            List<JobDto> result = new ArrayList<>();
            for (Job job : jobList) {
                result.add(JobMapper.toJobDto(job));
            }
            return result;
        }
        throw new NotFoundException(listNoJob);

    }

    @Override
    public List<JobDto> getJobByTitleDescriptionAndLocation(Location location, String keyword) {
        List<Job> jobList = jobs.stream()
                .filter(job -> job.getLocation().equals(location))
                .filter(job -> job.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || job.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        if (!jobList.isEmpty()) {
            List<JobDto> result = new ArrayList<>();
            for (Job job : jobList) {
                result.add(JobMapper.toJobDto(job));
            }
            return result;
        }
        throw new NotFoundException(notFoundJobMessage);
    }
}