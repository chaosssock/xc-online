package com.xuecheng.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: chaosssock
 * @Date: 2024/3/21 21:42
 * @Description: 测试minio的sdk
 */
public class MinioTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.1.8:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    //上传文件
    @Test
    public void testUpload() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }

        //上传文件的参数信息
        UploadObjectArgs testbucket = UploadObjectArgs.builder()
                .bucket("testbucket")//桶
                .filename("C:\\Users\\huang\\Pictures\\壁纸\\1174529.jpg")//指定本地文件路径
//                .object("1174529.jpg")//对象名 在桶下存储
                .object("test/01/1174529.jpg")//对象名 在子目录下存储
                .contentType(mimeType)//设置媒体文件类型
                .build();

        //上传文件
        minioClient.uploadObject(testbucket);


    }

    //删除文件
    @Test
    public void delete() throws Exception {
        //RemoveObjectArgs
        RemoveObjectArgs testbucket = RemoveObjectArgs.builder()
                .bucket("testbucket")//桶
                .object("1174529.jpg")//对象名
                .build();

        //删除文件
        minioClient.removeObject(testbucket);
    }

    //查询文件 从minio中下载
    @Test
    public void getFile() throws Exception{
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("test/01/1174529.jpg").build();

        //查询远程服务获取到一个流对象
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        //指定输出流
        FileOutputStream outputStream = new FileOutputStream(new File("D:\\minio_data\\1174529.jpg"));
        IOUtils.copy(inputStream, outputStream);

        //校验文件的完整性 对文件的内容进行md5
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\huang\\Pictures\\壁纸\\1174529.jpg"));
        String source_md5 = DigestUtils.md5Hex(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream(new File("D:\\minio_data\\1174529.jpg"));
        String local_md5 = DigestUtils.md5Hex(fileInputStream1);
        if (source_md5.equals(local_md5)) {
            System.out.println("下载成功");
        }
    }


}
