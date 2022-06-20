package com.jd.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.yygh.common.util.MD5;
import com.jd.yygh.hosp.service.HospitalSetService;
import com.jd.yygh.model.hosp.HospitalSet;
import com.jd.yygh.common.result.Result;
import com.jd.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author 一个胖胖的瘦子
 * @date 2022/6/18
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;


    //1.查询医院设置表里的所有信息
    @ApiOperation("获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospitalSet(){
         List<HospitalSet> list = hospitalSetService.list();
         return Result.ok(list);

    }

    //2.删除医院设置
    @ApiOperation("逻辑删除医院设置")
    @DeleteMapping("delete{id}")
    public Result removeHospitalSet(@PathVariable Long id){
        boolean flag = hospitalSetService.removeById(id);
        if(flag) {
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //3.条件查询带分页
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageHospitalSet(@PathVariable Long current, @PathVariable Long limit, @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current,limit);
        //构造条件
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if(!StringUtils.isEmpty(hosname)){
            queryWrapper.like("hosname", hosname);
        }
        if(!StringUtils.isEmpty(hoscode)){
            queryWrapper.eq("hoscode", hoscode);
        }

        //调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, queryWrapper);
        //返回结果
        return Result.ok(pageHospitalSet);

    }
    //4.添加医院设置
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        //设置状态 1 使用，0 不能使用
        hospitalSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        String encrypt = MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000));
        hospitalSet.setSignKey(encrypt);
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if(save){
            return Result.ok();
        }else{
            return Result.fail();
        }

    }
    //5.根据id获取医院设置
    @GetMapping("findById/{id}")
    public Result findById(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //6.修改医院设置
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag){
            return Result.ok();
        }else{
            return Result.fail();
        }

    }

    //7.批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> list){
        boolean flag = hospitalSetService.removeByIds(list);
        if(flag){
            return Result.ok();
        }else{
            return Result.fail();
        }

    }

    //8 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id, @PathVariable Integer status){
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //9 发送签名密钥
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();

    }



}
