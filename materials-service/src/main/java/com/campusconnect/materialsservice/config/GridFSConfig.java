package com.campusconnect.materialsservice.config;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Configuration
public class GridFSConfig {

    /**
     * Expose a GridFSBucket so you can @Autowired it anywhere.
     */
    @Bean
    public GridFSBucket gridFSBucket(MongoDatabaseFactory mongoDbFactory) {
        return GridFSBuckets.create(mongoDbFactory.getMongoDatabase());
    }
}
