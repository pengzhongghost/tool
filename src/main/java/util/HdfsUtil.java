package util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaohei on 16/3/9.
 * HDFS操作类
 */
public class HdfsUtil {

    private static final String HDFS = "hdfs://192.168.33.22:9000/";
    private static final Configuration conf = new Configuration();

    static {
        conf.setBoolean("fs.hdfs.impl.disable.cache", true);
    }

    /**
     * 创建文件夹
     *
     * @param folder 文件夹名
     */
    public static void mkdirs(String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
            System.out.println("Create: " + folder);
        }
        fs.close();
    }

    /**
     * 删除文件夹
     *
     * @param folder 文件夹名
     */
    public static void rmr(String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        fs.deleteOnExit(path);
        System.out.println("Delete: " + folder);
        fs.close();
    }

    /**
     * 重命名文件
     * @param src 源文件名
     * @param dst 目标文件名
     * */
    public static void rename(String src, String dst) throws IOException {
        Path name1 = new Path(src);
        Path name2 = new Path(dst);
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        fs.rename(name1, name2);
        System.out.println("Rename: from " + src + " to " + dst);
        fs.close();
    }

    /**
     * 列出该路径的文件信息
     *
     * @param folder 文件夹名
     */
    public static List<Path> ls(String folder) throws IOException {
        List<Path> pathList = new ArrayList<>();
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        FileStatus[] list = fs.listStatus(path);
        for (FileStatus f : list) {
            pathList.add(f.getPath());
        }
        return pathList;
    }

    public static void close() throws Exception {
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        fs.close();
    }

    /**
     * 创建文件
     *
     * @param file    文件名
     * @param content 文件内容
     */
    public static void createFile(String file, String content) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        byte[] buff = content.getBytes();
        FSDataOutputStream os = null;
        try {
            os = fs.create(new Path(file));
            os.write(buff, 0, buff.length);
            System.out.println("Create: " + file);
        } finally {
            if (os != null)
                os.close();
        }
        fs.close();
    }

    /**
     * 复制本地文件到hdfs
     *
     * @param local  本地文件路径
     * @param remote hdfs目标路径
     */
    public static void copyFile(String local, String remote) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        fs.copyFromLocalFile(new Path(local), new Path(remote));
    }

    public static void main(String[] args) throws IOException {
        copyFile("/Users/pengzhong/Downloads/929cbde2-dc3a-b976-eed6-0392f790a588_3630204239768888103.xlsx", "/export/douyin_order/929cbde2-dc3a-b976-eed6-0392f790a588_3630204239768888103.xlsx");
    }

    /**
     * 从hdfs下载文件到本地
     *
     * @param remote hdfs文件路径
     * @param local  本地目标路径
     */
    public static void download(String remote, String local) throws IOException {
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        fs.copyToLocalFile(path, new Path(local));
        System.out.println("download: from" + remote + " to " + local);
        fs.close();
    }

    /**
     * 查看hdfs文件内容
     *
     * @param remoteFile hdfs文件路径
     */
    public static void cat(String remoteFile) throws IOException {
        Path path = new Path(remoteFile);
        FileSystem fs = FileSystem.get(URI.create(HDFS), conf);
        FSDataInputStream fsdis = null;
        System.out.println("cat: " + remoteFile);
        try {
            fsdis = fs.open(path);
            IOUtils.copyBytes(fsdis, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(fsdis);
            fs.close();
        }
    }
}
