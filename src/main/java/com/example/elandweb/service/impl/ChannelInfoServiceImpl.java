package com.example.elandweb.service.impl;

import com.example.elandweb.category.ChannelInfoCategory;
import com.example.elandweb.config.DataNotFoundException;
import com.example.elandweb.dao.ChannelInfoRepository;
import com.example.elandweb.dao.TargetDao;
import com.example.elandweb.dto.PageDataDto;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.dto.TargetDto;
import com.example.elandweb.model.ChannelInfoEntity;
import com.example.elandweb.model.TagNameEnum;
import com.example.elandweb.model.TypeEnum;
import com.example.elandweb.service.ChannelInfoService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelInfoServiceImpl implements ChannelInfoService {
    private static final Logger logger = Logger.getLogger(ChannelInfoServiceImpl.class);
    private final ChannelInfoRepository channelInfoRepository;
    private final TargetDao targetDao;
    private static final String EXPORT_FILE = "C:\\test\\export";
    @Override
        public ResponseDto findTargetByTagNameAndType(Optional<TypeEnum> typeCategoryEnum, Optional<TagNameEnum> tagNameEnum, String HandleDownload) {
        String type = null;
        String tagName = null;
        if(!typeCategoryEnum.isEmpty()){
             type = typeCategoryEnum.get().getCode();
        }
        if(!tagNameEnum.isEmpty()){
            tagName = tagNameEnum.get().name();
        }
        List<TargetDto> targetsDto = targetDao.findTargetByTagNameAndType(tagName,type);
        System.out.println(targetsDto);
        if(HandleDownload.equals("true")){
            logger.warn("匯出csv檔案");
            exportToCsv(targetsDto,EXPORT_FILE);
        }
        return getRestDto(targetsDto,"查詢完成");
    }

    @Override
    public ResponseDto findAllPage(int page, int size) {
        int offset = getSelectOffset(page,size);
        int limit = size;
        System.out.println(offset);
        System.out.println(limit);
        List<ChannelInfoEntity> channelInfosEntity = channelInfoRepository.findAllPage(limit,offset).orElseThrow(() -> new DataNotFoundException("資料沒找到"));
        long total = channelInfoRepository.count();
        PageDataDto pageDataDto = getPageDataDto(page ,size,total,channelInfosEntity);
        System.out.println(channelInfosEntity);
        return getRestDto(pageDataDto,"查詢成功");
    }

    @Override
    public ResponseDto findChannelInfo(String sourceAreaId) {
        ChannelInfoEntity channelInfoEntity = channelInfoRepository.findById(sourceAreaId).orElseThrow(()->new DataNotFoundException("沒有該筆資料"));
        return getRestDto(channelInfoEntity,"查詢成功");
    }

    @Override
    public ResponseDto createChannelInfo(ChannelInfoCategory channelInfoCategory) {
        ChannelInfoEntity insertChannelInfoEntity = null;
        Optional<ChannelInfoEntity> lastChannelInfo = channelInfoRepository.findLastChannelInfo(channelInfoCategory.getSourceId());
        System.out.println(channelInfoCategory.getSourceId());
        saveChannelInfo(channelInfoRepository , insertChannelInfoEntity, channelInfoCategory, lastChannelInfo);
        return getRestDto(channelInfoCategory,"新增成功");
     }



    @Override
    public ResponseDto createChannelInfos(List<ChannelInfoCategory> channelInfosCategory) {
        channelInfosCategory.forEach((channelInfoCategory)->{
            ChannelInfoEntity insertChannelInfoEntity = null;
            Optional<ChannelInfoEntity> lastChannelInfo = channelInfoRepository.findLastChannelInfo(channelInfoCategory.getSourceId());
            System.out.println(channelInfoCategory.getSourceId());
            saveChannelInfo(channelInfoRepository , insertChannelInfoEntity, channelInfoCategory, lastChannelInfo);
        });
        return getRestDto(channelInfosCategory,"新增成功");
    }

    @Override
    public ResponseDto updateChannelInfo(String sourceAreaId,ChannelInfoCategory channelInfoCategory ) {
        String ptype = null;
        if (channelInfoCategory.getTypeEnum().isEmpty()) {
            ptype = null;
        }else{
            ptype = channelInfoCategory.getTypeEnum().get().getCode();
        }
        ChannelInfoEntity channelInfoEntity = ChannelInfoEntity
                .builder()
                .source_id(channelInfoCategory.getSourceId())
                .sourceAreaId(sourceAreaId)
                .PType2(ptype)
                .isUsed(channelInfoCategory.isUsed())
                .build();
        ChannelInfoEntity insertChannelInfoEntity = channelInfoRepository.save(channelInfoEntity);
        return getRestDto(insertChannelInfoEntity, "更新成功");
    }

    @Override
    public ResponseDto deleteChannelInfo(String sourceAreaId) {
        ChannelInfoEntity channelInfoEntity = channelInfoRepository.findById(sourceAreaId).orElseThrow(()-> new DataNotFoundException("沒有該筆資料"));
        channelInfoRepository.deleteById(sourceAreaId);
        return getRestDto(channelInfoEntity,"刪除成功");
    }

    @Override
    public byte[] download(Optional<TypeEnum> typeCategoryEnum, Optional<TagNameEnum> tagNameEnum, String handleDownload) throws IOException {
        ResponseDto responseDto = findTargetByTagNameAndType(typeCategoryEnum,tagNameEnum,handleDownload);
        String filePath = exportToCsv((List<TargetDto>) responseDto.getData(),EXPORT_FILE);
        File file = new File(filePath);
        byte[] data = Files.readAllBytes(file.toPath());
        return data;
    }

    private void saveChannelInfo(ChannelInfoRepository channelInfoRepository, ChannelInfoEntity insertChannelInfoEntity, ChannelInfoCategory channelInfoCategory, Optional<ChannelInfoEntity> lastChannelInfo) {
        String ptype = null;
        if (channelInfoCategory.getTypeEnum().isEmpty()) {
            ptype = null;
        }else{
            ptype = channelInfoCategory.getTypeEnum().get().getCode();
        }
        if (lastChannelInfo.isEmpty()) {
            StringBuilder sourceAreaId = new StringBuilder(channelInfoCategory.getSourceId())
                    .append("_0001");
            insertChannelInfoEntity = ChannelInfoEntity
                    .builder()
                    .source_id(channelInfoCategory.getSourceId())
                    .sourceAreaId(sourceAreaId.toString())
                    .isUsed(channelInfoCategory.isUsed())
                    .PType2(ptype)
                    .build();
            ;
        } else{
            String LastChannelInfoSourceAreaId = lastChannelInfo.get().getSourceAreaId();
            int cutWay = LastChannelInfoSourceAreaId.lastIndexOf("_");
            String cutNumber = LastChannelInfoSourceAreaId.substring(cutWay+1);
            String sourceId = lastChannelInfo.get().getSource_id();
            String newChannelInfoSourceAreaId = "";
            int number = Integer.parseInt(cutNumber);
            number = number + 1;
            System.out.println(number);
            if(number > 1000){
                newChannelInfoSourceAreaId = sourceId + "_" + number;
            }else if (number > 100){
                newChannelInfoSourceAreaId = sourceId + "_0" + number;
            }
            else if (number > 10){
                newChannelInfoSourceAreaId = sourceId + "_00" + number;
            }
            else if (number > 1){
                newChannelInfoSourceAreaId = sourceId + "_000" + number;
            }
            System.out.println(newChannelInfoSourceAreaId);
            insertChannelInfoEntity = ChannelInfoEntity
                    .builder()
                    .source_id(channelInfoCategory.getSourceId())
                    .sourceAreaId(newChannelInfoSourceAreaId)
                    .isUsed(channelInfoCategory.isUsed())
                    .PType2(ptype)
                    .build();
        }
        channelInfoRepository.save(insertChannelInfoEntity);
    }
    private String exportToCsv(List<TargetDto>targets, String filePath) {
        String  Date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'"));
        String  timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmmss"));
        String newFilePath = filePath+"\\"+Date;
        createDir(newFilePath);
        String PathName = newFilePath + "\\" + timestamp + "target.txt";
        try (CSVWriter writer = new CSVWriter(new FileWriter(PathName))) {
            writer.writeNext(new String[]{"標籤", "新聞", "部落格", "討論區", "社群網站", "評論", "問答網站", "影音"});

            for (TargetDto target : targets) {
                writer.writeNext(new String[]{
                        target.getTagName(),
                        String.valueOf(target.getNewsCount()),
                        String.valueOf(target.getBlogCount()),
                        String.valueOf(target.getForumCount()),
                        String.valueOf(target.getSocialCount()),
                        String.valueOf(target.getCommentCount()),
                        String.valueOf(target.getQaCount()),
                        String.valueOf(target.getVideoCount())
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return PathName;


    }

    private void createDir(String path){
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
