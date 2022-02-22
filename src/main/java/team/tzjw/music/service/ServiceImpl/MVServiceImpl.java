package team.tzjw.music.service.ServiceImpl;

import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.dao.MVMapper;
import team.tzjw.music.pojo.MV;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.User;
import team.tzjw.music.service.MVService;
import team.tzjw.music.util.MvUpUtils;
import team.tzjw.music.util.MvUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MVServiceImpl implements MVService {

    @Autowired
    private MVMapper mvMapper;

    @Override
    public Result getAll(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<MV> list = mvMapper.selectAll(page, limit);
        Long count = mvMapper.countMv();
        Result result = new Result();
        result.setCode(0);
        result.setData(list);
        result.setCount(count);
        return result;
    }
    @Override
    public Result count() {
        Result result = new Result();
        int i = mvMapper.getNums();
        try {
            result.setCode(i);
            result.setMsg("成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("出现未知错误");
            result.setCode(500);
            return result;
        }

    }
    @Transactional
    @Override
    public Result updateStatus(Integer status, String id) {
        Result result = new Result();
        try{
            int i = mvMapper.updateStatus(status, id);
            if(i > 0){
                result.setMsg("SUCCESS");
            }else{
                result.setMsg("ERROR");
            }
            return result;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setMsg("ERROR");
            return result;
        }
    }

    @Transactional
    @Override
    public Result deleteById(String[] id) {
        Result result = new Result();
        try {
            for(int i = 0; i < id.length; i++){
                int j = mvMapper.deleteByPrimaryKey(id[i]);
            }
            result.setMsg("SUCCESS");
            result.setCode(200);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("系统繁忙，请重新刷新！");
            result.setCode(500);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return result;
        }
    }

    @Override
    public MV details(String id) {
        MV mv = mvMapper.detail(id);
        return mv;
    }

    @Override
    public Result up(MultipartFile file, HttpServletRequest request) {
        String mvpath = MvUpUtils.upfile(file, request);
        Result result = new Result();
        result.setMsg(mvpath);
        return result;
    }

    @Transactional
    @Override
    public Result add(String mvpath, String singer, String mvdesc, HttpServletRequest request) {
        MV mv = new MV();
        String id = UUID.randomUUID().toString();
        mv.setId(id);
        mv.setMvdesc(mvdesc);
        mv.setMvpath("mv/" + mvpath);
        mv.setSinger(singer);
        mv.setPlaynum(0l);
        mv.setLikecounts(0l);
        mv.setStatus(0);
        Date date = new Date();
        mv.setCreatetime(date);
        User user = (User)request.getSession().getAttribute("user");
        String userId = user.getId();
        mv.setUserid(userId);
        MultimediaInfo info = MvUtils.analysis(mvpath, request);
        Long duration = info.getDuration()/ 1000;
        Float mvtime = Float.valueOf(duration);
        int mvheight = info.getVideo().getSize().getHeight();
        int mvweight = info.getVideo().getSize().getWidth();
        mv.setMvtime(mvtime);
        mv.setMvheight(mvheight);
        mv.setMvwidth(mvweight);
        Double size = MvUtils.ReadVideoSize(mvpath, request);
        mv.setSize(size);
        Result result = new Result();
        try {
            int i = mvMapper.insert(mv);
            if (i > 0) {
                result.setCode(200);
                result.setMsg("SUCCESS");
            }else{
                result.setCode(500);
                result.setMsg("ERROR");
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setCode(500);
            result.setMsg("ERROR");
            return result;
        }
    }

    @Override
    public MV editDetail(String id) {
        MV mv = mvMapper.editDetail(id);
        return mv;
    }

    @Transactional
    @Override
    public Result mvUpdate(String id, String userid, String mvdesc, Long playnum, Long likecounts) {
        Result result = new Result();
        try {
            int i = mvMapper.mvUpdate(id, userid, mvdesc, playnum, likecounts);
            if(i > 0){
                result.setMsg("SUCCESS");
                result.setCode(200);
            }else{
                result.setCode(500);
                result.setMsg("ERROR");
            }
            return result;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("ERROR");
            return result;
        }
    }

}
