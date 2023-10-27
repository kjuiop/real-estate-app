package io.gig.realestate.domain.attachment;

import io.gig.realestate.domain.attachment.types.FileType;
import io.gig.realestate.domain.attachment.types.UsageType;
import io.gig.realestate.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Getter
@SuperBuilder
@Entity
@Table(name = "attachments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

    private String orgFilename;

    private String savedFilename;

    private String fullPath;

    @Enumerated(value = EnumType.STRING)
    private UsageType usageType;

    public static Attachment Of(UsageType usageType, FileType fileType, String orgFilename, String savedFilename, String fullPath) {
        return Attachment.builder()
                .usageType(usageType)
                .fileType(fileType)
                .orgFilename(orgFilename)
                .savedFilename(savedFilename)
                .fullPath(fullPath)
                .build();
    }

}
