package team.tzjw.music.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

//专门解析视频或者音频
public class AudioUtils {
    public static MultimediaInfo analysis(String songName, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/audio/");
        File file = new File(realPath + songName);
        //获取解析视频的对象

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
}
