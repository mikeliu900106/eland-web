package com.example.elandweb.service.impl;

import com.example.elandweb.category.ChannelInfoCategory;
import com.example.elandweb.config.DataNotFoundException;
import com.example.elandweb.dao.ChannelInfoRepository;
import com.example.elandweb.dao.TargetRepository;
import com.example.elandweb.dto.PageDataDto;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.ChannelInfoEntity;
import com.example.elandweb.model.TagNameEnum;
import com.example.elandweb.model.TargetEntity;
import com.example.elandweb.model.TypeEnum;
import com.example.elandweb.service.ChannelInfoService;
import com.example.elandweb.util.TargetUtil;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelInfoServiceImpl implements ChannelInfoService {

    private Lock lock = new ReentrantLock(true);
    private static final Logger logger = Logger.getLogger(ChannelInfoServiceImpl.class);
    private final ChannelInfoRepository channelInfoRepository;
    private final TargetRepository targetRepository;
    @Value("${exportPath}")
    private String EXPORT_PATH;

    @Override
    public ResponseDto findTargetByTagNameAndType(Optional<TypeEnum> typeCategoryEnum, Optional<TagNameEnum> tagNameEnum, String handleDownload) {
        List<TargetEntity> targetsEntity = targetRepository.findAll();
        logger.debug("TargetsEntity:" + targetsEntity);
        TypeEnum type = typeCategoryEnum.isPresent() ? typeCategoryEnum.get() : null;
        TagNameEnum tagName = tagNameEnum.isPresent() ? tagNameEnum.get() : null;

        if (type != null) {
            try {
                TargetUtil targetUtil = new TargetUtil();
                List<String> BeChooseTypes = targetUtil.chooseNotInType(type);
                logger.debug("被選擇type的list" + BeChooseTypes);
                logger.debug("還沒被變成0的list" + targetsEntity);
                targetUtil.setChooseFieldsToZero(BeChooseTypes, targetsEntity);
                logger.debug("已被變成0的list" + targetsEntity);
            } catch (ClassNotFoundException e) {
                logger.warn("找不到該class");
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                logger.warn("方法反射異常");
                throw new RuntimeException(e);
            }
        }
        if (tagName != null) {
            logger.debug("targetsEntity前面:" + targetsEntity);

            TargetEntity beCatchEntity = targetsEntity.stream().filter((targetEntity) -> {
                return targetEntity.getTagName().equals(tagName.toString());
            }).findAny().orElseThrow(() -> new DataNotFoundException("找不到此tag"));

            TargetEntity cloneTargetEntity = beCatchEntity.clone();
            TargetEntity noParameterConstructorTargetEntity = new TargetEntity();
            if (beCatchEntity.getType() == 1) {
                cloneTargetEntity.setTagName("內容標籤");
                noParameterConstructorTargetEntity.setTagName("屬性標籤");
            } else {
                cloneTargetEntity.setTagName("屬性標籤");
                noParameterConstructorTargetEntity.setTagName("內容標籤");
            }
            logger.debug("targetsEntity clear前:" + targetsEntity);
            targetsEntity.clear();
            logger.debug("targetsEntity clear後:" + targetsEntity);
            targetsEntity.add(beCatchEntity);
            targetsEntity.add(noParameterConstructorTargetEntity);
            targetsEntity.add(cloneTargetEntity);
        }
        logger.debug("targetsEntity最後:" + targetsEntity);

        if (handleDownload.equals("true")) {
            logger.warn("匯出csv檔案");
            exportToCsv(targetsEntity, EXPORT_PATH);
        }
        return getRestDto(targetsEntity, "查詢完成");
    }

    @Override
    public ResponseDto findAllPage(int page, int size) {
        int offset = getSelectOffset(page, size);
        logger.debug("page:" + page);
        logger.debug("offset:" + offset);
        List<ChannelInfoEntity> channelInfosEntity = channelInfoRepository.findAllPage(size, offset).orElseThrow(() -> new DataNotFoundException("資料沒找到"));
        long total = channelInfoRepository.count();
        PageDataDto pageDataDto = getPageDataDto(page, size, total, channelInfosEntity);
        logger.debug("channelInfosEntity:" + channelInfosEntity);
        return getRestDto(pageDataDto, "查詢成功");
    }

    @Override
    public ResponseDto findChannelInfo(String sourceAreaId) {
        ChannelInfoEntity channelInfoEntity = channelInfoRepository.findById(sourceAreaId).orElseThrow(() -> new DataNotFoundException("沒有該筆資料"));
        return getRestDto(channelInfoEntity, "查詢成功");
    }

    @Override
    public ResponseDto createChannelInfo(ChannelInfoCategory channelInfoCategory) {
        ChannelInfoEntity insertChannelInfoEntity = null;
        Optional<ChannelInfoEntity> lastChannelInfo = channelInfoRepository
                .findLastChannelInfo(channelInfoCategory.getSourceId());
        logger.debug(channelInfoCategory.getSourceId());
        ChannelInfoEntity channelInfoEntity =
                saveChannelInfo(insertChannelInfoEntity, channelInfoCategory, lastChannelInfo);
        channelInfoRepository.save(channelInfoEntity);
        return getRestDto(channelInfoEntity, "新增成功");
    }

    @Override
    public ResponseDto createChannelInfos(List<ChannelInfoCategory> channelInfosCategory) {
        List<ChannelInfoCategory> safeChannelInfoCategory = new CopyOnWriteArrayList<>(channelInfosCategory);
        List<ChannelInfoEntity> ChannelInfosEntity = safeChannelInfoCategory.stream().parallel().map((channelInfoCategory) -> {
            ChannelInfoEntity insertChannelInfoEntity = null;
            Optional<ChannelInfoEntity> lastChannelInfo = channelInfoRepository.findLastChannelInfo(channelInfoCategory.getSourceId());
            logger.debug("sourceID:" + channelInfoCategory.getSourceId());
            ChannelInfoEntity channelInfoEntity = saveChannelInfo(insertChannelInfoEntity, channelInfoCategory, lastChannelInfo);
            return channelInfoEntity;
        }).collect(Collectors.toList());
        channelInfoRepository.saveAll(ChannelInfosEntity);
        return getRestDto(ChannelInfosEntity, "新增成功");
    }

    private ChannelInfoEntity saveChannelInfo(ChannelInfoEntity insertChannelInfoEntity, ChannelInfoCategory channelInfoCategory, Optional<ChannelInfoEntity> lastChannelInfo) {
        String ptype = null;
        if (channelInfoCategory.getTypeEnum().isEmpty()) {
            ptype = null;
        } else {
            ptype = channelInfoCategory.getTypeEnum().get().getCode();
        }
        if (lastChannelInfo.isEmpty()) {
            StringBuilder sourceAreaId = new StringBuilder(channelInfoCategory.getSourceId())
                    .append("_0001");
            insertChannelInfoEntity = ChannelInfoEntity
                    .builder()
                    .source_id(channelInfoCategory.getSourceId())
                    .sourceAreaId(sourceAreaId.toString())
                    .isUsed(true)
                    .PType2(ptype)
                    .build();
        } else {
            String LastChannelInfoSourceAreaId = lastChannelInfo.get().getSourceAreaId();
            int cutWay = LastChannelInfoSourceAreaId.lastIndexOf("_");
            String cutNumber = LastChannelInfoSourceAreaId.substring(cutWay + 1);
            String sourceId = lastChannelInfo.get().getSource_id();
            String newChannelInfoSourceAreaId = "";
            int number = Integer.parseInt(cutNumber);
            number = number + 1;
            System.out.println(number);
            if (number > 1000) {
                newChannelInfoSourceAreaId = sourceId + "_" + number;
            } else if (number > 100) {
                newChannelInfoSourceAreaId = sourceId + "_0" + number;
            } else if (number > 10) {
                newChannelInfoSourceAreaId = sourceId + "_00" + number;
            } else if (number > 1) {
                newChannelInfoSourceAreaId = sourceId + "_000" + number;
            }
            logger.debug("sourceAreaId:" + newChannelInfoSourceAreaId);
            insertChannelInfoEntity = ChannelInfoEntity
                    .builder()
                    .source_id(channelInfoCategory.getSourceId())
                    .sourceAreaId(newChannelInfoSourceAreaId)
                    .isUsed(true)
                    .PType2(ptype)
                    .build();
        }
        return (insertChannelInfoEntity);
    }

    @Override
    public ResponseDto updateChannelInfo(String sourceAreaId, ChannelInfoCategory channelInfoCategory) {
        long beginTime = Instant.now().toEpochMilli();
        ChannelInfoEntity insertChannelInfoEntity = null;
        String pType = null;
        if (channelInfoCategory.getTypeEnum().isPresent()) {
            pType = channelInfoCategory.getTypeEnum().get().getCode();
        }
        ChannelInfoEntity channelInfoEntity = ChannelInfoEntity
                .builder()
                .source_id(channelInfoCategory.getSourceId())
                .sourceAreaId(sourceAreaId)
                .PType2(pType)
                .isUsed(channelInfoCategory.isUsed())
                .build();
        insertChannelInfoEntity = channelInfoRepository.save(channelInfoEntity);
        long endTime = Instant.now().toEpochMilli();
        logger.debug("花多少時間:" + (endTime - beginTime));
        return getRestDto(insertChannelInfoEntity, "更新成功");
    }

    @Override
    public ResponseDto deleteChannelInfo(String sourceAreaId) {
        ChannelInfoEntity channelInfoEntity = channelInfoRepository.findById(sourceAreaId).orElseThrow(() -> new DataNotFoundException("沒有該筆資料"));
        channelInfoRepository.deleteById(sourceAreaId);
        return getRestDto(channelInfoEntity, "刪除成功");
    }

    @Override
    public byte[] download(Optional<TypeEnum> typeCategoryEnum, Optional<TagNameEnum> tagNameEnum, String handleDownload) throws IOException {
        ResponseDto responseDto = findTargetByTagNameAndType(typeCategoryEnum, tagNameEnum, handleDownload);
        logger.debug("exportFile:" + EXPORT_PATH);
        String filePath = exportToCsv((List<TargetEntity>) responseDto.getData(), EXPORT_PATH);
        File file = new File(filePath);
        byte[] data = Files.readAllBytes(file.toPath());
        return data;
    }


    private String exportToCsv(List<TargetEntity> targetsEntity, String filePath) {
        String Date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
        String newFilePath = filePath + "\\" + Date;
        createDir(newFilePath);
        String PathName = newFilePath + "\\" + timestamp + "target.txt";
        try (CSVWriter writer = new CSVWriter(new FileWriter(PathName))) {
            writer.writeNext(new String[]{"標籤", "新聞", "部落格", "討論區", "社群網站", "評論", "問答網站", "影音"});

            for (TargetEntity targetEntity : targetsEntity) {
                writer.writeNext(new String[]{
                        String.valueOf(targetEntity.getTagName()),
                        String.valueOf(targetEntity.getNewsCount()),
                        String.valueOf(targetEntity.getBlogCount()),
                        String.valueOf(targetEntity.getForumCount()),
                        String.valueOf(targetEntity.getSocialCount()),
                        String.valueOf(targetEntity.getCommentCount()),
                        String.valueOf(targetEntity.getQaCount()),
                        String.valueOf(targetEntity.getVideoCount())
                });
            }
        } catch (IOException e) {

        }
        return PathName;
    }

    private void createDir(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
