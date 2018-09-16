package com.addf.gadget.chart.job;


import com.addf.gadget.chart.job.repository.JobRepository;
import com.addf.gadget.chart.job.domain.Job;
import com.addf.gadget.chart.job.service.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/detail")
public class JobController {

    @Autowired
    FileStorage fileStorageService;

    @Autowired
    JobRepository jobRepository;

    @GetMapping
    public List<Job> getResourceByType(@RequestParam("detailParam") String type,
                                       @RequestParam("detailMetric") String metric) {
        return jobRepository.getByType(type, metric);
    }

    @GetMapping("/{id}")
    public List<Job> getResourceById(@PathVariable("id") String id) {
        return jobRepository.getById(id);

    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}





