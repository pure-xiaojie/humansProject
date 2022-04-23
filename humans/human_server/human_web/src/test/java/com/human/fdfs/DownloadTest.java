package com.human.fdfs;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Li JieGui
 * @date 2021/3/18 21:12
 */
public class DownloadTest {
    /**
     * 文件下载
     */
    @Test
    void testDownload() {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            byte[] bytes = client.download_file1("group1/M00/00/00/wKgAQ1_hj3aAd1CUAADrZmbdaGE693.jpg");
            FileOutputStream fos = new FileOutputStream(new File("E:\\666.jpg"));
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     */
    @Test
    void testUpload() {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            NameValuePair nvp[] = null;
            //上传到文件系统
            String fileId = client.upload_file1("C:\\Users\\javaboy\\Pictures\\picpick\\1.png", "png", nvp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
