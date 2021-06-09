package com.macro.mall.tiny.modules.dlm.util;

import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CommonUtil {
    //获取32位随机数
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean AssertEmpty(Collection collection){
        if (collection == null || collection.size() == 0){
            return true;
        }
        return false;
    }

    //获取六位随机数
    public static Integer getSixRandomNum(){
        return (int)((Math.random()*9+1)*100000);
    }

    public static boolean StringIsEmpty(String temp){
        if (temp == null || "".equals(temp)){
            return true;
        }
        return false;
    }

    //上传图片
    public static String uploadFile(MultipartFile file,String uploadFilePath){
        String fileName = "";
        String smallFile = "";
        if(file != null){
            fileName = file.getOriginalFilename();//直接返回文件的名字
            fileName = fileName.replaceAll(",","");
            String subffix = fileName.substring(fileName.lastIndexOf(".") + 1);//取文件的后缀
            fileName = fileName.substring(0,fileName.lastIndexOf("."))+"-"+new Date().getTime();
            //获取保存图片的路径
            File filePack=new File(uploadFilePath);
            if(!filePack.exists()){//目录不存在就创建
                filePack.mkdirs();
            }
//            FileOutputStream os = null;
//            InputStream inputStream = null;
            try {
//                resultFile = fileName+"."+subffix;
                //无压缩的原图
                File fileTemp = new File(filePack+"/"+fileName+"."+subffix);
                file.transferTo(fileTemp);//保存文件

                //已压缩过的图片  不再压缩
//                if(fileName.contains("_small")){
//                    return fileName+"."+subffix;
//                }

                String smallSubffix = subffix;
                //压缩原图
                if(smallSubffix.contains("png")){
                    smallSubffix = "jpg";
                }

                if(smallSubffix.contains("e")){
                    smallSubffix = smallSubffix.replace("e", "");
                }

                smallFile = fileName+"_small."+smallSubffix;
                Thumbnails.of(filePack+"/"+fileName+"."+subffix).scale(1f).outputQuality(0.5f).outputFormat("jpg").toFile(filePack+"/" +smallFile);
            } catch (IOException e) {
                System.out.println("上传图片失败...");
                e.printStackTrace();
                throw new MyException(MyEumException.UPLOAD_PIC_FAILURE);
            }
        }
        return smallFile;
    }

    //base64字符串转化成图片
    public static String GenerateImage(String imgStr,String uploadFilePath) {
        //对字节数组字符串进行Base64解码并生成图片
        String fileName = "";
        String subffix = "";
        if (imgStr == null) //图像数据为空
            return "";
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        String [] fileData = imgStr.split(",");
        String imageData = fileData[1];
        subffix = fileData[0].split(";")[0].split("/")[1];
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imageData);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }

            //生成jpeg图片
            fileName = "audit_" + getUUID32() + "." + subffix;
            String imgFilePath = uploadFilePath + "/" + fileName;//新生成的图片
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileName;

    }

    //发送Get请求
    public static String GET(String url) {
        String result = "";
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            // 异常记录
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e2) {
                // 异常记录
            }
        }
        return result;
    }
}
