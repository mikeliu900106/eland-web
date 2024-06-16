package com.example.elandweb.model;

public enum TypeCategoryEnum {
    FORUM("forum", "討論區"),
    SOCIAL("social", "社群網站"),
    COMMENT("comment", "評論"),
    QA("qa", "問答網站"),
    BLOG("blog", "部落格"),
    NEWS("news", "新聞"),
    VIDEO("video", "影音");

    private final String code;
    private final String name;

    TypeCategoryEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TypeCategoryEnum fromCode(String code) {
        for (TypeCategoryEnum category : TypeCategoryEnum.values()) {
            if (category.getCode().equalsIgnoreCase(code)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown category code: " + code);
    }
}
