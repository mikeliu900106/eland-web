# training-review-01
教育訓練成果驗證題目，目的為驗證受訓人員是否已經達到教育訓練的標準
> 難度:***

## 目標
1. 具備程式專案開發的基本邏輯及命名方式，並且能使用正確的資料結構（40%）
2. 了解HTTP Web service的運作原理，包含`request與resonponse的組成`、`請求方法`及`http狀態碼`（30%）
3. 了解關聯式資料庫的操作方式，並且能對資料進行彙整（20%）
4. 了解log記錄的基礎概念已經操作時機（10%）

## 評分標準
1. 對需求的了解程度
2. 對訓練目標的能力展現程度
3. 於時限內完成基礎需求（6天）

## 需求
1. 設計資料表，儲存給定的[目標資料](#輸入檔案說明)，用使用正確的資料型別 [0.5]
2. 提供存取資料庫的操作介面函式（CRUD）[1.5]
   1. 以物件的方式進行定義資料與操作
   2. 供後續功能所有資料庫操作的函式，需盡可能共用
3. 建立指定的cli（commend line interface）指令，包含資料匯入匯出功能 [0.5]
   ```shell
   java -jar cli.jar --import xxx.json
   java -jar cli.jar --export xxx.json
   ```
3. 建立web service，api設計準則請參考restful api
   1. 對頻道資訊（channel）、標籤資訊（tag）進行新刪查改的操作介面， [0.5]
      1. 獲取指定id資料
      2. 獲取資料列表，需包含翻頁功能，可自訂每頁幾筆以及取得第幾頁的資料
      3. 新增資料（可以新增1～n筆）
      4. 修改指定id資料
      5. 刪除指定id資料
   2. 基於需求的[資料格式](#輸出格式範例)，計算統計並回傳結果下載（csv）（共1支） [1.5]

4. 使用log4j建立log機制 [1]
   1. 針對匯入、匯出、api請求，記錄`使用狀況`及`例外狀況`
   2. 依照重要程度分別顯示於console與file
5. 設計設定檔，供後續設定資料庫連線資訊以及log檔案寫入位置 [0.5]
   1. 設定使用properties模組
6. 用commend執行程式 [0.5]
   1. 資料匯入
   2. 資料匯出
   3. web服務
   ```shell
   # 資料匯入
   java -jar cli.jar --import xxx.json
   # 資料匯出
   java -jar cli.jar --export xxx.json
   # web服務
   java -jar web_app.jar
   ```

## 加分條件
1. API交互時，只能使用自定義的物件，並有適當的繼承跟overwrite
2. 將統計結果並儲存至資料庫，並對資料表進行適當的正規化
3. 能夠考量資料量級，對效能進行適當的處置
4. 使用AOP處理log的相關機制

## 注意事項
1. 可以使用框架（如spring boot），但需說明使用方式

## 輸入檔案說明
- [channel_tag_mapping.json](assets%2Fchannel_tag_mapping.json):頻道與標籤對照表
- [tag_info.json](assets%2Ftag_info.json):標籤資料
- [channel_info.json](assets%2Fchannel_info.json):頻道資訊
- [p_type_2_list.json](assets%2Fp_type_2_list.json):來源分類清單

## 輸出格式範例 (csv)
![csv-sample.png](assets%2Fcsv-sample.png)   