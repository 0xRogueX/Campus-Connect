package com.campusconnect.materialsservice.controller;

import com.campusconnect.materialsservice.model.Material;
import com.campusconnect.materialsservice.service.MaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;
    private final GridFsTemplate gridFsTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MaterialController(MaterialService materialService,
                              GridFsTemplate gridFsTemplate,
                              ObjectMapper objectMapper) {
        this.materialService = materialService;
        this.gridFsTemplate = gridFsTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Uploads a file plus JSON metadata.
     * Fallback: metadata is passed as a String field and parsed manually.
     */
    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Material> uploadMaterial(
            @RequestPart("file") MultipartFile file,
            @RequestParam("metadata") @Valid String metadataJson
    ) throws IOException {
        // 1) parse the JSON metadata into a Material instance
        Material material = objectMapper.readValue(metadataJson, Material.class);

        // 2) delegate to service
        Material uploaded = materialService.uploadMaterial(file, material);

        // 3) return CREATED + body
        return ResponseEntity.status(HttpStatus.CREATED).body(uploaded);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadMaterial(@PathVariable String fileId) throws IOException {
        // lookup the GridFS file
        GridFSFile gridFsFile = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(new ObjectId(fileId)))
        );
        if (gridFsFile == null) {
            throw new IOException("File not found: " + fileId);
        }

        // wrap as a Spring Resource
        GridFsResource resource = gridFsTemplate.getResource(gridFsFile);

        // build content-disposition header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\""
        );

        // derive content type
        String ct = gridFsFile.getMetadata().getString("_contentType");
        MediaType mediaType = MediaType.parseMediaType(ct);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(mediaType)
                .body(resource);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Material> getMaterialMetadata(@PathVariable String fileId) {
        Material meta = materialService.getMaterialMetadata(fileId);
        return (meta != null)
                ? ResponseEntity.ok(meta)
                : ResponseEntity.notFound().build();
    }
}
