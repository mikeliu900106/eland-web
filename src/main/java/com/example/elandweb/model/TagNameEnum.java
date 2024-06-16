package com.example.elandweb.model;

public enum TagNameEnum {
    品牌(1),
    人物(2),
    地點(3),
    組織團體(4),
    政府(5),
    活動(6),
    媒體(7),
    其他(8),
    旅遊住宿(9),
    美食(10),
    料理烹飪(11),
    美妝保養(12),
    時尚穿搭(13),
    髮型設計(14),
    電玩遊戲(15),
    三C資訊(16),
    電器設備(17),
    親子互動(18),
    親子教育(19),
    媽咪寶寶(20),
    繪畫插圖(21),
    攝影平面(22),
    文字創作(23),
    空間設計(24),
    語言翻譯(25),
    金融理財(26),
    兩性議題(27),
    知識分享(28),
    才藝學習(29),
    書評_書籍介紹(30),
    房屋資訊(31),
    學校系所(32),
    法律知識(33),
    氣象地理(34),
    醫療健康(35),
    運動健身(36),
    汽機車(37),
    寵物(38),
    生活小物(39),
    植物盆栽(40),
    菸酒雪茄(41),
    玩具模型(42),
    趣味分享(43),
    動漫(44),
    命理宗教(45),
    潛水釣魚(46),
    軍事(47),
    結婚婚禮(48),
    自行車(49),
    露營登山(50),
    居家家具(51),
    交通工具(52),
    政治社會(53),
    影劇藝文(54),
    音樂評論(55),
    消費(56),
    新聞(57),
    社會公益(58),
    影音創作(59),
    音樂創作(60),
    舞蹈(61),
    樂器演奏(62),
    生活分享(63),
    魔術師(64),
    工作職業(65),
    論壇(66);

    private final int tagId;

    TagNameEnum(int tagId) {
        this.tagId = tagId;
    }

    public int getTagId() {
        return tagId;
    }

    public static TagNameEnum fromTagId(int tagId) {
        for (TagNameEnum tagName : TagNameEnum.values()) {
            if (tagName.getTagId() == tagId) {
                return tagName;
            }
        }
        throw new IllegalArgumentException("Unknown tag ID: " + tagId);
    }
}