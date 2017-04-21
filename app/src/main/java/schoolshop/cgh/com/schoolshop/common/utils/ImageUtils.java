package schoolshop.cgh.com.schoolshop.common.utils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 图片工具类
 * Created by HUI on 2017-04-20.
 */

public class ImageUtils {

    /**
     * 设置图片上传的工具类
     */
    public static List<MultipartBody.Part> getPartList(List<String> imgList){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        //循环加入多张图片
        for(int i = 0; i < imgList.size(); i++){
            String filePath = imgList.get(i);
            File file = new File(filePath);//filePath 图片地址
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("imgfile", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        return parts;
    }

}
