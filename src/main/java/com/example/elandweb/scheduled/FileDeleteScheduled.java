package com.example.elandweb.scheduled;

import com.example.elandweb.dao.TargetDao;
import com.example.elandweb.dao.TargetRepository;
import com.example.elandweb.dto.TargetDto;
import com.example.elandweb.model.TargetEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
public class FileDeleteScheduled {

    private static final Logger logger = Logger.getLogger(FileDeleteScheduled.class);

    @Value("${exportPath}")
    private String exportPath;

    @Value("${cutHour}")
    private int cutHour;

    private final TargetRepository targetRepository;

    private final TargetDao targetDao;

    public FileDeleteScheduled(TargetRepository targetRepository, TargetDao targetDao) {
        this.targetRepository = targetRepository;
        this.targetDao = targetDao;
    }

    @Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
    public void fileDelete() {
        Path dirPath = Paths.get(exportPath);
        try {
            Stream<Path> filePath = Files.walk(dirPath, 3);
            filePath.filter(path -> !path.equals(dirPath))
                    .filter(
                            (path) -> {
                                try {
                                    Object creationTime = Files.getAttribute(path, "creationTime");
                                    FileTime fileTime = (FileTime) creationTime;
//                            logger.debug("檔案名稱:" + path + "檔案創造時間:" + fileTime);
                                    Instant fileInstant = fileTime.toInstant();
                                    Instant nowInstant = Instant.now();
                                    Duration duration = Duration.between(fileInstant, nowInstant);
                                    if (duration.toHours() > cutHour) {
                                        if (Files.isDirectory(path)) {
                                            logger.warn("要被刪除的資料夾:" + path);
                                        } else {
                                            logger.warn("要被刪除的檔案:" + path);
                                        }
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException("刪除重複資料夾" + exportPath + "失敗");
                                }
                            }
                    ).forEach((path -> {
                        logger.warn("檔案是否刪除:" + path.toFile().delete());
                    }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    public void insertDataToTempDataBase() {
        if (targetRepository.count() > 0) {
            logger.warn("target資料表刪除:");
            targetRepository.deleteAll();
        }
        List<TargetDto> targetsDto = targetDao.findTargetByTagNameAndType(null, null);
        List<TargetDto> safeTargetsDto = new CopyOnWriteArrayList<>(targetsDto);

        List<TargetEntity> targetsEntity = safeTargetsDto.stream().parallel().map((targetDto) -> {
            return TargetEntity.builder()
                    .blogCount(targetDto.getBlogCount())
                    .qaCount(targetDto.getQaCount())
                    .forumCount(targetDto.getForumCount())
                    .tagName(targetDto.getTagName())
                    .videoCount(targetDto.getVideoCount())
                    .newsCount(targetDto.getNewsCount())
                    .commentCount(targetDto.getCommentCount())
                    .socialCount(targetDto.getSocialCount())
                    .type(targetDto.getType())
                    .build();
        }).toList();
        logger.debug("插入的資料:" + safeTargetsDto);
        targetRepository.saveAll(targetsEntity);
    }
}
