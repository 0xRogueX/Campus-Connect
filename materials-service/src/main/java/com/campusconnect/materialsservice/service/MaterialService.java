package com.campusconnect.materialsservice.service;

import com.campusconnect.materialsservice.model.Material;
import com.campusconnect.materialsservice.repository.MaterialRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MaterialService {

    private final GridFsTemplate gridFsTemplate;
    private final MaterialRepository materialRepository;
    private final com.campusconnect.materialsservice.service.JWTService jwtService;
    private final HttpServletRequest request;

    @Autowired
    public MaterialService(GridFsTemplate gridFsTemplate,
                           MaterialRepository materialRepository,
                           com.campusconnect.materialsservice.service.JWTService jwtService,
                           HttpServletRequest request) {
        this.gridFsTemplate   = gridFsTemplate;
        this.materialRepository = materialRepository;
        this.jwtService       = jwtService;
        this.request          = request;
    }

    /**
     * Only FACULTY or ADMIN may upload.
     */
    public Material uploadMaterial(MultipartFile file, Material material) throws IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null
                || !jwtService.isAdminOrFaculty(authHeader)
                || jwtService.isTokenExpired(authHeader)) {
            throw new AccessDeniedException("Only faculty or admin may upload materials");
        }

        // store in GridFS
        Object fileId = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        );

        // save metadata
        material.setFileId(fileId.toString());
        material.setFileName(file.getOriginalFilename());
        return materialRepository.save(material);
    }

    /**
     * Everyone may download.
     */
    public Resource downloadMaterial(String fileId) throws IOException {
        GridFSFile gridFsFile = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(new ObjectId(fileId)))
        );

        if (gridFsFile == null) {
            throw new IOException("File not found: " + fileId);
        }

        GridFsResource resource = gridFsTemplate.getResource(gridFsFile);
        return resource;
    }

    /**
     * Retrieve only the metadata document.
     */
    public Material getMaterialMetadata(String fileId) {
        return materialRepository.findByFileId(fileId);
    }
}
