package com.example.blackrabbit.data.api.response

import com.example.blackrabbit.data.model.ErrorResult
import com.google.gson.annotations.SerializedName

/* Sample Data Sets
* {
  "SearchPublicToiletPOIService": {
    "list_total_count": 4938,
    "RESULT": {
      "CODE": "INFO-000",
      "MESSAGE": "정상 처리되었습니다"
    },
    "row": [
      {
        "POI_ID": "102423",
        "FNAME": "우성스포츠센터",
        "ANAME": "민간개방화장실",
        "CNAME": "",
        "CENTER_X1": 192026.077328,
        "CENTER_Y1": 443662.903901,
        "X_WGS84": 126.90983237468618,
        "Y_WGS84": 37.49238614627172,
        "INSERTDATE": "20100712",
        "UPDATEDATE": "20100712"
      }
    ],
    [
      {
        "POI_ID": "102423",
        "FNAME": "우성스포츠센터",
        "ANAME": "민간개방화장실",
        "CNAME": "",
        "CENTER_X1": 192026.077328,
        "CENTER_Y1": 443662.903901,
        "X_WGS84": 126.90983237468618,
        "Y_WGS84": 37.49238614627172,
        "INSERTDATE": "20100712",
        "UPDATEDATE": "20100712"
      }
    ], ...
  }
}
* */
data class ToiletResponse(
    @SerializedName("GeoInfoPublicToilet")
    var service:GeoInfoPublicToilet?
) : ErrorResult() {
    data class GeoInfoPublicToilet(
        @SerializedName("list_total_count")
        var listTotalCount: Int?,
        @SerializedName("RESULT")
        var apiResult: ApiResult,
        @SerializedName("row")
        var toiletList: ArrayList<ToiletInfo>
    ) {
        data class ApiResult(
            @SerializedName("CODE")
            var code: String?,
            @SerializedName("MESSAGE")
            var message: String?
        )

        data class ToiletInfo(
            @SerializedName("POI_ID")
            var id: String?,
            @SerializedName("FNAME")
            var fName: String?,
            @SerializedName("ANAME")
            var aName: String?,
            @SerializedName("CNAME")
            var cName: String?,
            @SerializedName("CENTER_X1")
            var centerX: Double?,
            @SerializedName("CENTER_Y1")
            var centerY: Double?,
            @SerializedName("X_WGS84")
            var wgsX: Double?,
            @SerializedName("Y_WGS84")
            var wgxY: Double?,
            @SerializedName("INSERTDATE")
            var insertDate: String?,
            @SerializedName("UPDATEDATE")
            var updateDate: String?
        )
    }

}
