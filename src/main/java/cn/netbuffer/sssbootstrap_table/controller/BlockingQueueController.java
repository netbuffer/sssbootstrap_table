package cn.netbuffer.sssbootstrap_table.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列测试
 */
@RestController
@RequestMapping("/queue")
public class BlockingQueueController {

    private BlockingQueue blockingQueue=new ArrayBlockingQueue(3);

    /**
     * 添加数据
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public boolean send(@RequestParam(value = "data",required = false) String data){
        return blockingQueue.add(data);
    }

    /**
     * take数据
     * @return
     */
    @RequestMapping(value = "take",method = RequestMethod.GET)
    public Object take(){
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取队列head数据
     * @return
     */
    @RequestMapping(value = "poll",method = RequestMethod.GET)
    public Object poll(){
        return blockingQueue.poll();
    }

    /**
     * 等待一段时间获取元素
     * @param timeunit
     * @param time
     * @return
     */
    @RequestMapping(value = "poll/{timeunit}/{time}",method = RequestMethod.GET)
    public Object polltimeout(@PathVariable("timeunit") String timeunit,@PathVariable("time") Long time){
        try {
            return blockingQueue.poll(time, TimeUnit.valueOf(timeunit.toUpperCase()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 阻塞放入
     * @param data
     * @return
     */
    @RequestMapping(value = "put",method = RequestMethod.GET)
    public Object put(@RequestParam(value = "data",required = false)String data){
        try {
            blockingQueue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取所有数据
     * @return
     */
    @RequestMapping(value = "all",method = RequestMethod.GET)
    public List all(){
        List arr=new ArrayList();
        blockingQueue.drainTo(arr);
        return arr;
    }

    /**
     * 查看队列大小
     * @return
     */
    @RequestMapping(value = "size",method = RequestMethod.GET)
    public int size(){
        return blockingQueue.size();
    }

    public static void main(String[] args){
        System.out.println("TimeUnit.MINUTES.name():"+TimeUnit.MINUTES.name());
        System.out.println("TimeUnit.SECONDS.name():"+TimeUnit.SECONDS.name());
        System.out.println("TimeUnit.valueOf(timeunit):"+TimeUnit.valueOf("MINUTES"));
    }

}
