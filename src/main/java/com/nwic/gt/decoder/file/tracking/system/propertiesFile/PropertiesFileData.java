package com.nwic.gt.decoder.file.tracking.system.propertiesFile;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: Vikas Predhva
 * Designation : Software Engineering
 * Organization: Grant Thornton
 * Date: 10-02-2025
 * Description: Properties file Data
 */
@Component
@ConfigurationProperties(prefix = "day")
@Data
public class PropertiesFileData {
    private Integer dayRestriction;

}
