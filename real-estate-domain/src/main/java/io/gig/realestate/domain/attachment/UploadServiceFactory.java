package io.gig.realestate.domain.attachment;

import io.gig.realestate.domain.attachment.types.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2023/10/04
 */
@Component
@RequiredArgsConstructor
public class UploadServiceFactory {

    private final ImageUploadService imageUploadService;

    public UploadService create(FileType fileType) {
        switch (fileType) {
            case Image:
                return imageUploadService;
            case Document:
                return null;
        }

        return null;
    }

}
