package com.example.elandweb.service;

import com.example.elandweb.category.ChannelInfoCategory;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagNameEnum;
import com.example.elandweb.model.TypeEnum;

import java.util.List;
import java.util.Optional;


public interface ChannelInfoService extends BasicService{
    ResponseDto findAllTable(Optional<TypeEnum> typeCategoryEnum, Optional<TagNameEnum> tagNameEnum);

    ResponseDto findAllPage(int page, int size);

    ResponseDto findChannelInfo(String sourceAreaId);

    ResponseDto createChannelInfo(ChannelInfoCategory channelInfoCategory);

    ResponseDto createChannelInfos(List<ChannelInfoCategory> channelInfosCategory);

    ResponseDto updateChannelInfo(String sourceAreaId,ChannelInfoCategory channelInfoCategory);

    ResponseDto deleteChannelInfo(String sourceAreaId);


}
