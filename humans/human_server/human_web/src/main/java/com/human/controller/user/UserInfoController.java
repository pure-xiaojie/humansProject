package com.human.controller.user;

import com.human.pojo.Hr;
import com.human.pojo.RespBean;
import com.human.service.UserService;
import com.human.util.FastDfsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Li JieGui
 * @date 2020/12/22 14:38
 * 用户个人资料
 */
@Api(tags = "用户信息")
@RestController
public class UserInfoController {

    @Autowired
    UserService userService;

    /**
     * 图片访问代理地址
     */
    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    /**
     * 获取用户信息
     * @param authentication 由 springSecurity 提供
     * @return
     */
    @ApiOperation("获取个人详细信息")
    @GetMapping("/user/info")
    public Hr getCurrentHr(Authentication authentication) {
        return ((Hr) authentication.getPrincipal());
    }

    /**
     * 修改用户信息
     * @param hr
     * @param authentication
     * @return
     */
    @ApiOperation("修改个人信息")
    @PutMapping("/user/info")
    public RespBean updateHr(@RequestBody Hr hr, Authentication authentication) {
        if (userService.updateHr(hr) == 1) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    /**
     * 修改用户密码
     * @param info
     * @return
     */
    @ApiOperation("修改个人密码")
    @PutMapping("/user/pass")
    public RespBean updateHrPasswd(@RequestBody Map<String, Object> info) {
        String oldPass = (String) info.get("oldPass");
        String newPass = (String) info.get("newPass");
        Integer id = (Integer) info.get("id");
        if (userService.updateHrPasswd(oldPass, newPass, id)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    /**
     * 修改头像
     * @param file
     * @param id
     * @param authentication
     * @return
     */
    @ApiOperation("修改个人头像")
    @PostMapping("/user/userface")
    public RespBean updateHrUserFace(MultipartFile file, Integer id, Authentication authentication) {
        String fileId = FastDfsUtils.upload(file);
        String url = nginxHost + fileId;
        if (userService.updateUserFace(id, url) == 1) {
            Hr hr = (Hr) authentication.getPrincipal();
            hr.setUserface(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("更新成功!", url);
        }
        return RespBean.error("更新失败!");
    }

}
