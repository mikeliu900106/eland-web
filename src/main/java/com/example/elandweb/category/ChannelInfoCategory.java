package com.example.elandweb.category;

import com.example.elandweb.model.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelInfoCategory {
    private String sourceId;
    private boolean isUsed;
    private Optional<TypeEnum> typeEnum;
}
