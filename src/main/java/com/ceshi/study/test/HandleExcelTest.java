package com.ceshi.study.test;

import org.apache.axis.utils.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bytedeco.javacv.FrameFilter;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: HandleExcelTest
 * @Author: shenyafei
 * @Date: 2020/11/5
 * @Desc
 **/
public class HandleExcelTest {
    /***
     * poi文件处理jar包
     * 		<dependency>
     * 			<groupId>org.apache.poi</groupId>
     * 			<artifactId>poi</artifactId>
     * 			<version>3.17</version>
     * 			<type>jar</type>
     * 			<scope>compile</scope>
     * 		</dependency>
     * 		<dependency>
     * 			<groupId>fr.opensagres.xdocreport</groupId>
     * 			<artifactId>fr.opensagres.xdocreport.document</artifactId>
     * 			<version>2.0.2</version>
     * 		</dependency>
     * 		<dependency>
     * 			<groupId>fr.opensagres.xdocreport</groupId>
     * 			<artifactId>org.apache.poi.xwpf.converter.xhtml</artifactId>
     * 			<version>1.0.6</version>
     * 		</dependency>
     * 		<dependency>
     * 			<groupId>org.apache.poi</groupId>
     * 			<artifactId>poi</artifactId>
     * 			<version>3.15</version>
     * 		</dependency>
     * 		<dependency>
     * 			<groupId>org.apache.poi</groupId>
     * 			<artifactId>poi-scratchpad</artifactId>
     * 			<version>3.15</version>
     * 		</dependency>
     * 		<dependency>
     * 			<groupId>org.apache.poi</groupId>
     * 			<artifactId>poi-ooxml</artifactId>
     * 			<version>3.15</version>
     * 		</dependency>
     * @param orginPath
     * @return
     */

    public static List<String> getDataFromExcels(String orginPath) throws Exception {
        File orginFile = new File(orginPath);
        if (!orginFile.exists()) {
            System.out.println("文件不存在");
            return null;
        }
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(orginFile));
        // 打开Excel中的第一个Sheet
        Sheet sheetContent = workbook.getSheetAt(0);
        List<String> finalList = new ArrayList<>();
        int rowNum = 1;
        while (true) {
            StringBuffer stringBuffer = new StringBuffer("");
            Row row = sheetContent.getRow(rowNum);
            if (row == null || row.getCell(0) == null || row.getCell(0).toString() == "") {
                break;
            }
            rowNum++;
            stringBuffer.append("db.hospitalInfo.update(");
            //获取医院id
            String id = getCellValue(row,0);
            stringBuffer.append("{").append("\"hospitalId\" : NumberLong(\"").append(id).append("\")").append("},");
            //获取
            String b1 = getCellValue(row,4);//捐卵服务
            String b2 = getCellValue(row,5);//捐赠胚胎服务
            String b4 = getCellValue(row,6);//胚胎冷冻服务
            String b5 = getCellValue(row,7);//卵子冷冻服务
            String b3 = getCellValue(row,8);//单身女性
            String b6 = getCellValue(row,9);//第三方
            if (StringUtils.isEmpty(b1)) {
                continue;
            }
            //开头
            stringBuffer.append("{$set:{");
            //设置展示统计状态 statisticsInfo.dataStatisticsShowFlag:0
            stringBuffer.append("\"statisticsInfo.dataStatisticsShowFlag\":NumberInt(1),");
            //拼装经营范围
            stringBuffer.append("\"statisticsInfo.businessScopeCodeList\":[");
            StringBuffer busBuffer = new StringBuffer("");
            if (!StringUtils.isEmpty(b1) && b1.equals("T")) {
                busBuffer.append("NumberInt(1),");
            }
            if (!StringUtils.isEmpty(b2) && b2.equals("T")) {
                busBuffer.append("NumberInt(2),");
            }
            if (!StringUtils.isEmpty(b3) && b3.equals("T")) {
                busBuffer.append("NumberInt(3),");
            }
            if (!StringUtils.isEmpty(b4) && b4.equals("T")) {
                busBuffer.append("NumberInt(4),");
            }
            if (!StringUtils.isEmpty(b5) && b5.equals("T")) {
                busBuffer.append("NumberInt(5),");
            }
            if (!StringUtils.isEmpty(b6) && b6.equals("T")) {
                busBuffer.append("NumberInt(6),");
            }
            String busStr = busBuffer.toString();
            if (!StringUtils.isEmpty(busStr)) {
                busStr = busStr.substring(0,busStr.length() -1);
            }
            stringBuffer.append(busStr);
            stringBuffer.append("],");

            String sart = getCellValue(row,10);//sart成员
            if (!StringUtils.isEmpty(sart) && sart.equals("T")) {
                stringBuffer.append("\"statisticsInfo.sartMemberStatus\":NumberInt(1),");
            } else {
                stringBuffer.append("\"statisticsInfo.sartMemberStatus\":NumberInt(0),");
            }
            String cdc = getCellValue(row,11); //cdc认证
            if (!StringUtils.isEmpty(cdc) && cdc.equals("T")) {
                stringBuffer.append("\"statisticsInfo.cdcAuthStatus\":NumberInt(1),");
            } else {
                stringBuffer.append("\"statisticsInfo.cdcAuthStatus\":NumberInt(0),");
            }
            String p1 = getCellValue(row,12);//男性1
            String p2 = getCellValue(row,13);//子宫
            String p3 = getCellValue(row,14);//输卵管
            String p5 = getCellValue(row,15);//排卵障碍
            String p13 = getCellValue(row,16);//子宫问题
            String p9 = getCellValue(row,17);//三代试管
            String p6 = getCellValue(row,18);//第三方辅助
            String p10 = getCellValue(row,19);//卵巢能力差
            String p12 = getCellValue(row,20);//卵子或胚胎
            String p7 = getCellValue(row,21);//反复妊娠
            String p4 = getCellValue(row,22);//其他-不孕症
            String p8 = getCellValue(row,23);//其他-无
            String p11 = getCellValue(row,24);//其他-不明原因

            StringBuffer pp = new StringBuffer("\"statisticsInfo.patientTotalDataList\":[");
            if (!StringUtils.isEmpty(p1)) {
                pp.append("{\"code\":NumberInt(1),\"value\":"+p1+"},");
                pp.append("{\"code\":NumberInt(2),\"value\":"+p2+"},");
                pp.append("{\"code\":NumberInt(3),\"value\":"+p3+"},");
                pp.append("{\"code\":NumberInt(4),\"value\":"+p4+"},");
                pp.append("{\"code\":NumberInt(5),\"value\":"+p5+"},");
                pp.append("{\"code\":NumberInt(6),\"value\":"+p6+"},");
                pp.append("{\"code\":NumberInt(7),\"value\":"+p7+"},");
                pp.append("{\"code\":NumberInt(8),\"value\":"+p8+"},");
                pp.append("{\"code\":NumberInt(9),\"value\":"+p9+"},");
                pp.append("{\"code\":NumberInt(10),\"value\":"+p10+"},");
                pp.append("{\"code\":NumberInt(11),\"value\":"+p11+"},");
                pp.append("{\"code\":NumberInt(12),\"value\":"+p12+"},");
                pp.append("{\"code\":NumberInt(13),\"value\":"+p13+"}");
            }
            pp.append("]");

            //周期
            int zhouCount = 0;
            StringBuffer zhou = new StringBuffer("\"statisticsInfo.cycleTotalDataList\":[");
            String z1 = getCellValue(row,25);//<35
            String z2 = getCellValue(row,26);//35-37
            String z3 = getCellValue(row,27);//38-40
            String z4 = getCellValue(row,28);//41-42
            String z5 = getCellValue(row,29);//43
            StringBuffer subZ = new StringBuffer("");
            if (!StringUtils.isEmpty(z1)) {
                subZ.append("{\"code\":NumberInt(1),\"value\":NumberInt("+z1+")},");
                zhouCount += Integer.valueOf(z1);
            }
            if (!StringUtils.isEmpty(z2)) {
                subZ.append("{\"code\":NumberInt(2),\"value\":NumberInt("+z2+")},");
                zhouCount += Integer.valueOf(z2);
            }
            if (!StringUtils.isEmpty(z3)) {
                subZ.append("{\"code\":NumberInt(3),\"value\":NumberInt("+z3+")},");
                zhouCount += Integer.valueOf(z3);
            }
            if (!StringUtils.isEmpty(z4)) {
                subZ.append("{\"code\":NumberInt(4),\"value\":NumberInt("+z4+")},");
                zhouCount += Integer.valueOf(z4);
            }
            if (!StringUtils.isEmpty(z5)) {
                subZ.append("{\"code\":NumberInt(5),\"value\":NumberInt("+z5+")},");
                zhouCount += Integer.valueOf(z5);
            }
            if (!StringUtils.isEmpty(subZ.toString())) {
                zhou.append(subZ.toString().substring(0,subZ.toString().length() - 1));
            }
            zhou.append("]");
            //活产率
            double aliveAvg = 0d;
            StringBuffer chan = new StringBuffer("\"statisticsInfo.aliveTotalDataList\":[");
            String c1 = getCellValueAlive(row,30);//<35
            String c2 = getCellValueAlive(row,31);//35-37
            String c3 = getCellValueAlive(row,32);//38-40
            String c4 = getCellValueAlive(row,33);//41-42
            String c5 = getCellValueAlive(row,34);//43
            StringBuffer subC = new StringBuffer("");
            if (!StringUtils.isEmpty(c1)) {
                subC.append("{\"code\":NumberInt(1),\"value\":"+c1+"},");
                aliveAvg += Double.valueOf(c1);
            }
            if (!StringUtils.isEmpty(c2)) {
                subC.append("{\"code\":NumberInt(2),\"value\":"+c2+"},");
                aliveAvg += Double.valueOf(c2);
            }
            if (!StringUtils.isEmpty(c3)) {
                subC.append("{\"code\":NumberInt(3),\"value\":"+c3+"},");
                aliveAvg += Double.valueOf(c3);
            }
            if (!StringUtils.isEmpty(c4)) {
                subC.append("{\"code\":NumberInt(4),\"value\":"+c4+"},");
                aliveAvg += Double.valueOf(c4);
            }
            if (!StringUtils.isEmpty(c5)) {
                subC.append("{\"code\":NumberInt(5),\"value\":"+c5+"},");
                aliveAvg += Double.valueOf(c5);
            }
            aliveAvg = new BigDecimal(aliveAvg).divide(new BigDecimal(5),1,BigDecimal.ROUND_HALF_UP).doubleValue();
            if (!StringUtils.isEmpty(subC.toString())) {
                chan.append(subC.toString().substring(0,subC.toString().length() - 1));
            }
            chan.append("]");
            //案例数
            int caseCount = 0;
            StringBuffer an = new StringBuffer("\"statisticsInfo.caseTotalDataList\":[");
            String a1 = getCellValue(row,35);//<35
            String a2 = getCellValue(row,36);//35-37
            String a3 = getCellValue(row,37);//38-40
            String a4 = getCellValue(row,38);//41-42
            String a5 = getCellValue(row,39);//43
            StringBuffer subA = new StringBuffer("");
            if (!StringUtils.isEmpty(a1)) {
                subA.append("{\"code\":NumberInt(1),\"value\":NumberInt("+a1+")},");
                caseCount += Integer.valueOf(a1);
            }
            if (!StringUtils.isEmpty(a2)) {
                subA.append("{\"code\":NumberInt(2),\"value\":NumberInt("+a2+")},");
                caseCount += Integer.valueOf(a2);
            }
            if (!StringUtils.isEmpty(a3)) {
                subA.append("{\"code\":NumberInt(3),\"value\":NumberInt("+a3+")},");
                caseCount += Integer.valueOf(a3);
            }
            if (!StringUtils.isEmpty(a4)) {
                subA.append("{\"code\":NumberInt(4),\"value\":NumberInt("+a4+")},");
                caseCount += Integer.valueOf(a4);
            }
            if (!StringUtils.isEmpty(a5)) {
                subA.append("{\"code\":NumberInt(5),\"value\":NumberInt("+a5+")},");
                caseCount += Integer.valueOf(a5);
            }
            if (!StringUtils.isEmpty(subA.toString())) {
                an.append(subA.toString().substring(0,subA.toString().length() -1));
            }
            an.append("]");
            //结尾
            stringBuffer.append(zhou.toString()).append(",");
            stringBuffer.append("\"statisticsInfo.cycleTotalCount\":NumberInt(").append(zhouCount).append("),");
            stringBuffer.append(chan.toString()).append(",");
            stringBuffer.append("\"statisticsInfo.aliveAvgRate\":").append(aliveAvg).append(",");
            stringBuffer.append(an.toString()).append(",");
            stringBuffer.append("\"statisticsInfo.caseTotalCount\":NumberInt(").append(caseCount).append("),");
            stringBuffer.append(pp.toString());
            stringBuffer.append("}});");
            finalList.add(stringBuffer.toString());
        }
        return finalList;
    }

    public static String getCellValue(Row row, int rowNum){
        String value = "";
        Cell cell = row.getCell(rowNum);
        if (cell != null) {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                double a = row.getCell(rowNum).getNumericCellValue();
                int b = (int)a;
                if (b == a) {
                    value =  b+"";
                } else {
                    value =  a +"";
                }
            } else {
                value = row.getCell(rowNum).getStringCellValue();
            }

            if (!StringUtils.isEmpty(value)) {
                if (value.equals("<1")) {
                    value = "0";
                }
                value = value.replace(",","");
                value = value.replace("%","");
            }
        }
        return value;
    }

    public static String getCellValueAlive(Row row, int rowNum){
        String value = "";
        Cell cell = row.getCell(rowNum);
        if (cell != null) {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                double a = row.getCell(rowNum).getNumericCellValue();
                System.out.println(a);
                a = new BigDecimal(a+"").multiply(new BigDecimal(100)).doubleValue();
                int b = (int)a;
                if (b == a) {
                    value =  b+"";
                } else {
                    value =  a +"";
                }
            } else {
                value = row.getCell(rowNum).getStringCellValue();
            }

            if (!StringUtils.isEmpty(value)) {
                if (value.equals("<1")) {
                    value = "0";
                }
                value = value.replace(",","");
                value = value.replace("%","");
            }
        }
        return value;
    }
    public static void main(String[] args)throws Exception {
        List<String> ss = getDataFromExcels("D:/d.xlsx");
        System.out.println("--------ss："+ss.size()+"----------");
        if (ss != null) {
            ss.stream().forEach(e->{ System.out.println(e);});
        } else {
            System.out.println("无数据");
        }

    }
}
