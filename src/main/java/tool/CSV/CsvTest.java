package tool.CSV;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.text.csv.*;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.sax.ExcelSaxReader;
import cn.hutool.poi.excel.sax.ExcelSaxUtil;
import pojo.ColumnInfo;
import tool.SqlTest;
import util.HdfsUtil;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author pengzhong
 * @since 2023/8/14
 */
public class CsvTest {

    /**
     * 清洗csv中的数据
     * 逗号分割符改为<#$>
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws IOException, SQLException {
        String configFile = args[3];
        //String configFile = "/Users/pengzhong/Downloads/zhong.config";
        List<String> stringList = FileUtil.readLines(new File(configFile), StandardCharsets.UTF_8);

        String srcDir = args[0];
        String tarDir = args[1];
        String table = args[2];
        boolean isNeedNewTable = Boolean.parseBoolean(args[4]);


        String cookie = stringList.get(0);
        String newCol;
        if (stringList.size() > 1) {
            newCol = stringList.get(1);
        } else {
            newCol = null;
        }
        String columnsInfo = null;
        if (stringList.size() > 2) {
            columnsInfo = stringList.get(2);
        }
//        String srcDir = "/Users/pengzhong/Downloads/src";
//        String tarDir = "/Users/pengzhong/Downloads/target";
//        String table = "zhong_test_3";
//        boolean isNeedNewTable = true;

        String label = UUID.randomUUID().toString().replace("-", "");
        List<String> fileNames = FileUtil.listFileNames(srcDir);
        int handleFilesNum = fileNames.size();
        System.out.println("---------开始文件清洗转换---------");
        //1.文件清洗转换
        AtomicInteger columnNum = new AtomicInteger(0);
        for (int i = 0; i < handleFilesNum; i++) {
            String fileName = fileNames.get(i);
            AtomicInteger temp = new AtomicInteger();
            if (fileName.endsWith(".csv")) {
                FileWriter fileWriter = new FileWriter(new File(tarDir + "/" + fileName));
                CsvReader csvReader = CsvUtil.getReader(new FileReader(srcDir + "/" + fileName));
                int finalI = i;
                AtomicInteger tempI = new AtomicInteger(0);
                csvReader.stream().forEach(strings -> {
                    if (0 != temp.getAndIncrement()) {
                        StringBuilder sb = new StringBuilder();
                        if (isNeedNewTable) {
                            sb.append(tempI.incrementAndGet()).append("<#$>");
                        }
                        List<String> rawList = strings.getRawList();
                        columnNum.set(rawList.size());
                        for (String str : rawList) {
                            sb.append(str).append("<#$>");
                        }
                        String result = sb.toString();
                        result = result.substring(0, result.length() - 4);
                        fileWriter.write(result + "\n", true);
                    }
                });
            } else if (fileName.endsWith(".xlsx")) {
                FileWriter fileWriter = new FileWriter(new File(tarDir + "/" + fileName.replace(".xlsx", ".csv")));
                ExcelSaxReader<?> saxReader = ExcelSaxUtil.createSaxReader(true, (sheetIndex, rowIndex, rowCells) -> {
                    if (0 != temp.getAndIncrement()) {
                        StringBuilder sb = new StringBuilder();
                        if (isNeedNewTable) {
                            sb.append(temp.get()).append("<#$>");
                        }
                        for (Object str : rowCells) {
                            sb.append(str).append("<#$>");
                        }
                        String result = sb.toString();
                        result = result.substring(0, result.length() - 4);
                        fileWriter.write(result + "\n", true);
                    }
                });
                saxReader.read(new FileInputStream(srcDir + "/" + fileName));
            }
            System.out.println("文件清洗转换进度：" + new BigDecimal(i + 1).divide(new BigDecimal(handleFilesNum), 4, RoundingMode.FLOOR).multiply(new BigDecimal("100")).setScale(2, RoundingMode.FLOOR) + "%");
        }
        //2.文件上传到hdfs
        System.out.println("---------开始上传文件到hdfs---------");
        List<String> tarFileNames = FileUtil.listFileNames(tarDir);
        int uploadFileNum = tarFileNames.size();
        for (int i = 0; i < uploadFileNum; i++) {
            String tarFileName = tarFileNames.get(i);
            if (".DS_Store".equals(tarFileName)) {
                continue;
            }
            HdfsUtil.copyFile(tarDir + "/" + tarFileName, "/import/" + label + "/" + tarFileName);
            System.out.println("上传hdfs文件进度：" + new BigDecimal(i + 1).divide(new BigDecimal(uploadFileNum), 4, RoundingMode.FLOOR).multiply(new BigDecimal("100")).setScale(2, RoundingMode.FLOOR) + "%");
        }
        System.out.println("---------开始往sr导入数据---------");

//        Db db = DbUtil.use(new SimpleDataSource("jdbc:mysql://192.168.33.14:3306/test_data?useSSL=false", "root", "123456", "com.mysql.cj.jdbc.Driver"));
//        Connection connection = db.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement("show create table " + table);
//        ResultSetImpl resultSet = (ResultSetImpl)preparedStatement.executeQuery();
//        ResultsetRows rows = resultSet.getRows();
//        System.out.println(rows);
//        Row row = rows.get(0);
        Map<String, Object> requestParam = new HashMap<>();
//        requestParam.put("sql", "show create table " + table);
        HttpRequest httpRequest = HttpUtil.createPost("https://e.reduxingxuan.com/bj/pixiu/offline/table/config/showTableInfo")
                .header("cookie", cookie)
                .form(requestParam);
        HttpResponse response = httpRequest.execute();
//        String createTable = JSONUtil.toBean(response.body(), new TypeReference<ServiceResult<List<Map<String, String>>>>() {
//        }, false).getData().get(0).get("Create Table");
//        List<String> columns = SqlTest.parseSql(createTable).stream().map(ColumnInfo::getName).collect(Collectors.toList());
//        if (null != newCol) {
//            columns.remove(newCol.split("=")[0]);
//        }
        if (isNeedNewTable) {
            String tableSql = createTableSql(table, columnNum.get());
            List<String> columns = SqlTest.parseSql(tableSql).stream().map(ColumnInfo::getName).collect(Collectors.toList());
            columnsInfo = CollUtil.join(columns, ",");
            requestParam.put("sql", createTableSql(table, columnNum.get()));
            HttpResponse httpResponse = HttpUtil.createPost("https://e.reduxingxuan.com/bj/pixiu/offline/table/config/createTable")
                    .header("cookie", cookie)
                    .form(requestParam).execute();
            String body = httpResponse.body();
            System.out.println("create table: " + body);
        }
        String loadSql = loadSql(label, table, columnsInfo, newCol, isNeedNewTable);
        System.out.println("loadSql: " + loadSql);
        requestParam.put("sql", loadSql);
        httpRequest = HttpUtil.createPost("https://e.reduxingxuan.com/bj/pixiu/offline/table/config/createTable")
                .header("cookie", cookie)
                .form(requestParam);
        response = httpRequest.execute();
        System.out.println("sr label：" + label);
        System.out.println("sr result: " + response.body());

    }

    private static String createTableSql(String tableName, Integer columnNum) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE if not exists`").append(tableName).append("` (\n").append("  `id` int(11) NOT NULL,");
        for (int i = 0; i < columnNum; i++) {
            sb.append("col_").append(i).append(" varchar(765) ").append("null ").append(",");
        }
        String substring = sb.substring(0, sb.length() - 1);
        return substring + ") ENGINE=OLAP \n" +
                "DUPLICATE KEY(`id`)\n" +
                "DISTRIBUTED BY HASH(`id`) BUCKETS 6 \n" +
                "PROPERTIES (\n" +
                "\"replication_num\" = \"3\",\n" +
                "\"in_memory\" = \"false\",\n" +
                "\"storage_format\" = \"DEFAULT\",\n" +
                "\"enable_persistent_index\" = \"false\",\n" +
                "\"replicated_storage\" = \"true\",\n" +
                "\"compression\" = \"LZ4\"\n" +
                ");";
    }

    private static String loadSql(String label, String table, String columns, String newCol, boolean isNeedNewTable) {
//        StringBuilder sb = new StringBuilder();
//        for (String column : columns) {
//            sb.append(column).append(",");
//        }
//        String columnStr = sb.substring(0, sb.length() - 1);
        String pre = "LOAD LABEL " + label + "\n" +
                "(\n" +
                "    DATA INFILE(\"hdfs://192.168.33.22:9000/import/" + label + "/*\")\n" +
                "    INTO TABLE " + table + "\n" +
                "    COLUMNS TERMINATED BY \"<#$>\"\n" +
                "\t\tFORMAT AS 'CSV'\n" +
//                "\t\t(\n" +
//                "        skip_header = 1\n" +
//                "        trim_space = TRUE\n" +
//                "    )\n" +
                "    (" + columns + ")\n";
        String mid = "    SET(" + newCol + ") \n";
        String suf = ")\n" +
                "WITH BROKER\n" +
                "(\n" +
                "     \"username\" = \"root\",\n" +
                "    \"password\" = \"\"\n" +
                ")\n" +
                "PROPERTIES\n" +
                "(\n" +
                "    \"timeout\" = \"36000\"\n" +
                ");";

        if (null == newCol || isNeedNewTable) {
            return pre + suf;
        } else {
            return pre + mid + suf;
        }
    }

    private static String getResult(String label) {
        return "show load where label = '" + label + "';";
    }

//    /**
//     * 清洗csv中的数据
//     * 逗号分割符改为<#$>
//     * @param args
//     * @throws FileNotFoundException
//     */
//    public static void main(String[] args) throws FileNotFoundException {
//        String dirName = "/Users/pengzhong/Downloads/2021&2022抖音历史订单数据_new";
//        List<String> fileNames = FileUtil.listFileNames(dirName);
//        for (String fileName : fileNames) {
//            CsvReader csvReader = CsvUtil.getReader(new FileReader(dirName + "/" + fileName));
//            FileWriter fileWriter = new FileWriter(new File("/Users/pengzhong/Downloads/2021&2022抖音历史订单数据_v1/" + fileName));
//            List<CsvRow> collect = csvReader.stream().map(strings -> {
//                StringBuilder sb = new StringBuilder();
//                List<String> rawList = strings.getRawList();
//                for (String str : rawList) {
//                    sb.append(str).append("<#$>");
//                }
//                String result = sb.toString();
//                result = result.substring(0, result.length() - 4);
//                fileWriter.write(result + "\n", true);
//                return strings;
//            }).collect(Collectors.toList());
//        }
//    }

//    public static void main(String[] args) throws FileNotFoundException {
////        String dirName = "/Users/pengzhong/Downloads/2021&2022抖音历史订单数据_new";
////        List<String> fileNames = FileUtil.listFileNames(dirName);
////        for (String fileName : fileNames) {
//            CsvReader csvReader = CsvUtil.getReader(new FileReader("/Users/pengzhong/Downloads/79ed79f8-1abe-d053-3295-9eed151f6448_3634064105361029313.csv"));
//            FileWriter fileWriter = new FileWriter(new File("/Users/pengzhong/Downloads/nihao.csv"));
//            List<CsvRow> collect = csvReader.stream().map(strings -> {
//                StringBuilder sb = new StringBuilder();
//                List<String> rawList = strings.getRawList();
//                for (String str : rawList) {
//                    sb.append(str).append("<#$>");
//                }
//                String result = sb.toString();
//                result = result.substring(0, result.length() - 4);
//                fileWriter.write(result + "\n", true);
//                return strings;
//            }).collect(Collectors.toList());
//        }


}
