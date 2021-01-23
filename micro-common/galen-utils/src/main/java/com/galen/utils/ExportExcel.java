package com.galen.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Galen
 * @Date: 2019/8/7-17:31
 * @TODO: 利用开源组件POI 3.17 (pom引入此版本的poi即可)动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 应用泛型，代表任意一个符合javabean风格的类
 * 注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 * byte[]表jpg格式的图片数据
 * setTitleStyle()自定义数据区的样式
 * setBodyRowIndex() 自定义对象属性对应的列
 **/
public class ExportExcel<T> {

    // 内存中创建一个excel文件
    private HSSFWorkbook workbook;
    //表头样式
    private HSSFCellStyle titleStyle;
    //数据区样式
    private HSSFCellStyle bodyStyle;
    //数据的对象对应的列
    private Map<String, Integer> bodyRowIndex;


    public ExportExcel() {
        workbook = new HSSFWorkbook();
    }

    public HSSFCellStyle getTitleStyle() {
        return titleStyle;
    }

    public void setTitleStyle(HSSFCellStyle titleStyle) {
        this.titleStyle = titleStyle;
    }

    public HSSFCellStyle getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(HSSFCellStyle bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public Map<String, Integer> getBodyRowIndex() {
        return bodyRowIndex;
    }

    public void setBodyRowIndex(Map<String, Integer> bodyRowIndex) {
        this.bodyRowIndex = bodyRowIndex;
    }

    /**
     * @Author:Galen
     * @Description: 设置表头样式
     * @Date:2019/8/7 - 15:21
     * @Param:[workbook]
     * @return: void
     **/
    private void initTitleCellStyle() {
        titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体,应用到当前的样式
        HSSFFont titleFont = workbook.createFont();
        titleFont.setColor(HSSFColor.VIOLET.index);
        titleFont.setFontHeightInPoints((short) 12);
        titleStyle.setFont(titleFont);
    }

    /**
     * @Author:Galen
     * @Description: 设置数据区的样式
     * @Date:2019/8/7 - 15:22
     * @Param:[workbook]
     * @return:void
     **/

    private void initBodyCellStyle() {
        bodyStyle = workbook.createCellStyle();
        bodyStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        bodyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成一个字体,应用到当前的样式
        HSSFFont bodyFont = workbook.createFont();
        bodyStyle.setFont(bodyFont);
    }

    /**
     * @Author:Galen
     * @Description: 初始化 bodyRowIndex
     * @Date:2019/8/7 - 16:52
     * @Param:[t]
     * @return:void
     **/
    private void initBodyRowIndex(T t) {
        bodyRowIndex = new HashMap<String, Integer>();
        // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
        Field[] fields = t.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            bodyRowIndex.put(fieldName, i);
        }
    }

    public void exportExcel(Collection<T> dataSet, String outFilePath) {
        exportExcel(null, dataSet, outFilePath);
    }

    public void exportExcel(Collection<T> dataSet, OutputStream out) {
        exportExcel(null, dataSet, out);
    }

    public void exportExcel(String[] titles, Collection<T> dataSet, String outFilePath) {
        if (!outFilePath.endsWith(".xlsx")) {
            return;
        }
        File file = new File(outFilePath);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        exportExcel(titles, dataSet, outputStream);
    }

    public void exportExcel(String[] titles, Collection<T> dataSet, OutputStream out) {
        exportExcel(titles, dataSet, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] titles, Collection<T> dataSet, OutputStream out, String pattern) {
        exportExcel("测试POI导出EXCEL文档", titles, dataSet, out, pattern);
    }

    /**
     * @Author:Galen
     * @Description: 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * @Date:2019/8/7 - 14:32
     * @Param:[title, headers, dataSet, out, pattern]
     * @return:void
     **/
    @SuppressWarnings("unchecked")
    public void exportExcel(String sheetName, String[] titles,
                            Collection<T> dataSet, OutputStream out, String pattern) {
        if (null == titleStyle) {
            this.initTitleCellStyle();
        }
        if (null == bodyStyle) {
            this.initBodyCellStyle();
        }
        // 内存的excel中生成一个工作薄
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置工作薄表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");
        //行号
        int lineIndex = 0;
        /**
         * @Author: Galen
         * @TODO: 写入表头
         **/
        if (null != titles && 0 < titles.length) {
            HSSFRow titleRow = sheet.createRow(lineIndex++);
            HSSFCell titleCell;
            for (short i = 0; i < titles.length; i++) {
                titleCell = titleRow.createCell(i);
                titleCell.setCellStyle(titleStyle);
                titleCell.setCellValue(titles[i]);
            }
        }
        /**
         * @Author: Galen
         * @TODO: 遍历集合数据，产生数据行
         **/
        Iterator<T> it = dataSet.iterator();
        Integer rowIndex;
        HSSFRow row;
        HSSFCell bodyCell;
        while (it.hasNext()) {
            row = sheet.createRow(lineIndex++);
            T t = (T) it.next();
            if (null == bodyRowIndex) {
                this.initBodyRowIndex(t);
            }
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                rowIndex = bodyRowIndex.get(fieldName);
                if (null == rowIndex) {
                    //此字段不需要输出到excel
                    continue;
                }
                bodyCell = row.createCell(rowIndex);
                bodyCell.setCellStyle(bodyStyle);
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "true";
                        if (!bValue) {
                            textValue = "false";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (int) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, lineIndex, (short) 6, lineIndex);
                        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else if (null == value) {
                        textValue = "";
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            bodyCell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            bodyCell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}