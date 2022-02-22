package team.tzjw.music.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;

//专门解析音频或者视频
public class MvUtils {

    public static MultimediaInfo analysis(String videoName, HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/mv/");
        //获取视频的文件对象
        File file = new File(realPath + videoName);
        //获取解析视频的对象 it jave
        Encoder encoder = new Encoder();
        try {
            //info是视频解析以后的对象
            MultimediaInfo info = encoder.getInfo(file);
            return info;
        } catch (EncoderException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Double ReadVideoSize(String videoName, HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/mv/");
        //获取视频的文件对象
        File file = new File(realPath + videoName);
        FileChannel fc = null;
        Double size = 0.0;
        try {
            @SuppressWarnings("resource")
            FileInputStream fis = new FileInputStream(file);
            fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            BigDecimal divide = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP);
            size = divide.doubleValue();
            return size;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return 0.0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
