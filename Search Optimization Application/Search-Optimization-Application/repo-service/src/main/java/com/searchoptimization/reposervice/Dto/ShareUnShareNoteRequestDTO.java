package com.searchoptimization.reposervice.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShareUnShareNoteRequestDTO {
    private String recipientEmail;
    private int noteId;

}
