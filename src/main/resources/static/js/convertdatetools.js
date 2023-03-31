/**
 * 将常规日期格式字符串转换为大写日历格式字符串
 @dateStr 日期字符串如"2022-09-08"
 return 返回大写日历格式
*/
function toCapitalDate(dateStr)
{
    var result = "";
    var date = new Date(dateStr);
    //如果是有效日期
    if(date instanceof Date && !isNaN(date.getTime())){
        var chinese = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九'];
        var yearStr = date.getFullYear().toString();
        var monthStr = (date.getMonth() + 1).toString();
        var dayStr = date.getDate().toString();
        result = "公元";
        for (var i = 0; i < yearStr.length; i++) {
            result += chinese[yearStr.charAt(i)];
        }
        result += "年";
        if (monthStr.length == 2) {
            if (monthStr.charAt(0) == "1") {
                if (monthStr.charAt(1) == "0") {
                    result += ("十月");
                }else{
                    result += ("十" + chinese[monthStr.charAt(1)] + "月");
                }
            }
        } else {
            result += (chinese[monthStr.charAt(0)] + "月");
        }
        if (dayStr.length == 2) {
            if (dayStr.charAt(1) != "0") {
                if (dayStr.charAt(0) == "1") {
                    result += ("十" + chinese[dayStr.charAt(1)] + "日");
                }else{
                    result += (chinese[dayStr.charAt(0)] + "十" + chinese[dayStr.charAt(1)] + "日");
                }
            }else{
                if (dayStr.charAt(0) == "1") {
                    result += ("初十日");
                }else if (dayStr.charAt(0) == "2") {
                    result += ("二十日");
                }else{
                    result += ("三十日");
                }
            }
        } else {
            result += ("初" + chinese[dayStr.charAt(0)] + "日");
        }
    }
    return result;
}

/**
 * 计算两个时间之间的差值
 @param sdate 起始时间字符串
 @param edate 结束时间字符串
 return 返回两个日期之间相差的年数
 */
function getYears(sdate, edate){
    var yearNum = 0;
    //转换起始时间
    var sdate = new Date(sdate);
    //转换结束时间
    var edate = new Date(edate);
    //起始时间和结束时间有效情况下
    if((sdate instanceof Date && !isNaN(sdate.getTime())) && (edate instanceof Date && !isNaN(edate.getTime()))){
        if(edate > sdate) {
            //获得各自的年、月、日
            var sY = sdate.getFullYear();
            var sM = sdate.getMonth() + 1;
            var sD = sdate.getDate();
            var eY = edate.getFullYear();
            var eM = edate.getMonth() + 1;
            var eD = edate.getDate();
            var yL = eY - sY;
            var mL = eM - sM;
            var dL = eD - sD;
            //console.log("年相差：" + yL);
            //console.log("月相差：" + mL);
            //console.log("日相差：" + dL);
            if (dL < 0) {
                mL--;
            }
            if (mL < 0) {
                yL--;
            }
            if (yL > 0) {
                yearNum = yL;
            }else{
                yearNum = 0;
            }
        }
    }
    return yearNum;
}
/**
 * 计算两个时间之间的差值(天数)
 @param sdate 起始时间字符串
 @param edate 结束时间字符串
 return 返回两个日期之间相差的天数
 */
function GetDateDiff(sdate, edate) {
    var diffDate = -1;
    //转换起始时间
    var sdate = new Date(sdate);
    //转换结束时间
    var edate = new Date(edate);
    //起始时间和结束时间有效情况下
    if((sdate instanceof Date && !isNaN(sdate.getTime())) && (edate instanceof Date && !isNaN(edate.getTime()))) {
        var milli_secs = edate.getTime() - sdate.getTime();
        var days = milli_secs / (1000 * 3600 * 24);
        diffDate = Math.round(Math.abs(days));
    }
    return diffDate;
}