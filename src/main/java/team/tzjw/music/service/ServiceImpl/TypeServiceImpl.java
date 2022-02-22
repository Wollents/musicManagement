package team.tzjw.music.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import team.tzjw.music.dao.TypeMapper;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.Type;
import team.tzjw.music.service.TypeService;

import java.util.List;
import java.util.UUID;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Result getAll(Integer page,Integer limit) {
        page = (page - 1) * limit;
        List<Type> list = typeMapper.selectAll(page,limit);
        Long count = Long.valueOf(typeMapper.getNums());
        Result result = new Result();
        result.setCode(0);
        result.setData(list);
        result.setCount(count);
        return result;
    }

    @Override
    public Type selectById(String tid) {
        Type type = typeMapper.selectByPrimaryKey(tid);
        return type;
    }

    @Override
    @Transactional
    public Result deleteById(String[] tid) {
        Result result = new Result();
        try {
            for (int i = 0; i < tid.length; i++) {
                int j = typeMapper.deleteByPrimaryKey(tid[i]);
            }
            result.setCode(200);
            result.setMsg("Success");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("出错啦！");
            //设置手动回滚，不要抛出运行时异常
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return result;
        }

    }

    @Override
    @Transactional
    public Result upload(String tname, String tdesc) {
        Type type = new Type();
        String tid = UUID.randomUUID().toString();
        type.setTid(tid);
        type.setTdesc(tdesc);
        type.setTname(tname);
        Result result = new Result();
        try {
            int i = typeMapper.insert(type);
            if (i > 0) {
                result.setCode(200);
                result.setMsg("Success");
            } else {
                result.setMsg("Error");
                result.setCode(500);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setCode(500);
            result.setMsg("出现未知错误");
            return result;
        }


    }

    @Override
    @Transactional
    public Result edit(String tid, String tname, String tdesc) {
        Type type = new Type();
        type.setTname(tname);
        type.setTid(tid);
        type.setTdesc(tdesc);
        Result result = new Result();
        try {
            int i = typeMapper.updateByPrimaryKey(type);
            if (i > 0) {
                result.setMsg("修改成功");
                result.setCode(200);
            } else {
                result.setMsg("修改失败，请注意修改参数的类型值是否正确");
                result.setCode(500);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMsg("出现未知错误，请重试");
            result.setCode(500);
            return result;
        }
    }

    @Override
    public Result count() {
        Result result = new Result();
        int i = typeMapper.getNums();
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

    @Override
    public Result getSongtype() {
        Result result = new Result();
        List<String> list = typeMapper.getSongtype();
        result.setData(list);
        result.setMsg("Success");
        return result;
    }

    @Override
    public Result getSongtypeNums() {
        Result result = new Result();
        List<Integer>list = typeMapper.getSongtypeNums();
        result.setData(list);
        result.setMsg("Success");
        return result;
    }


//    @Override
//    public List<Type> getAll() {

//    }
}
