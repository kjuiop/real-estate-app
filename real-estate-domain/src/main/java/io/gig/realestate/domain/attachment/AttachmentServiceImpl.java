package io.gig.realestate.domain.attachment;

import io.gig.realestate.domain.attachment.dto.AttachmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentStore attachmentStore;

    @Override
    @Transactional
    public void create(List<AttachmentDto> attachmentList) {

        List<Attachment> attachments = new ArrayList<>();
        for (AttachmentDto dto : attachmentList) {
            Attachment newAttachment = Attachment.Of(dto.getUsageType(), dto.getFileType(),
                    dto.getOriginalFilename(), dto.getSavedFilename(), dto.getFullPath());
            attachments.add(newAttachment);
        }

        attachmentStore.storeAll(attachments);
    }
}
