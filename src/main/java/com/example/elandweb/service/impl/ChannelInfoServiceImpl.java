package com.example.elandweb.service.impl;

import com.example.elandweb.dao.ChannelInfoRepository;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagNameEnum;
import com.example.elandweb.model.TypeCategoryEnum;
import com.example.elandweb.service.ChannelInfoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelInfoServiceImpl implements ChannelInfoService {
    private final ChannelInfoRepository channelInfoRepository;
    @Override
    public ResponseDto findAllTable(TypeCategoryEnum typeCategoryEnum, TagNameEnum tagNameEnum) {
//        channelInfoRepository.findAllTable();
        return null;
        use eland_web;
        SELECT
        ti.tag_name AS '屬性標籤',
                SUM(CASE WHEN ci.p_type_2 = 'news' THEN 1 ELSE 0 END) AS '新聞',
                SUM(CASE WHEN ci.p_type_2 = 'blog' THEN 1 ELSE 0 END) AS '部落格',
                SUM(CASE WHEN ci.p_type_2 = 'forum' THEN 1 ELSE 0 END) AS '討論區',
                SUM(CASE WHEN ci.p_type_2 = 'social' THEN 1 ELSE 0 END) AS '社群網站',
                SUM(CASE WHEN ci.p_type_2 = 'comment' THEN 1 ELSE 0 END) AS '評論',
                SUM(CASE WHEN ci.p_type_2 = 'qa' THEN 1 ELSE 0 END) AS '問答網站',
                SUM(CASE WHEN ci.p_type_2 = 'video' THEN 1 ELSE 0 END) AS '影音'
        FROM
        channel_info ci
        JOIN
        channel_tag_mapping ctm ON ci.source_area_id = ctm.s_area_id
        JOIN
        tag_info ti ON ctm.tag_id = ti.tag_id
        GROUP BY
        ti.tag_name
        ORDER BY
        ti.tag_name;
    }
}
