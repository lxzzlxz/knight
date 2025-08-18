package com.fm.knight.knight.client;

import com.fm.knight.knight.helper.RHelper;
import com.fm.knight.knight.helper.ResultMapHelper;
import com.fm.knight.knight.knight.model.*;
import com.fm.knight.knight.model.*;
import com.fm.knight.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@FeignClient(value = "security", fallback = SecurityFeignClientFallBack.class)
public interface SecurityFeignClient {

    /**
     * 查询服务列表
     *
     * @param serviceName
     * @return
     */
    @RequestMapping(value = "/baseService/v1/findList", method = RequestMethod.GET)
    List<BaseService> findBaseServiceList(@RequestParam("serviceName") String serviceName);

    /**
     * 新增服务
     *
     * @param baseServices
     * @return
     */
    @RequestMapping(value = "/baseService/v1/insertList", method = RequestMethod.POST)
    Map<String, Object> insertBaseServiceList(@RequestBody List<BaseService> baseServices);

    /**
     * 查询服务权限
     *
     * @param serviceName
     * @return
     */
    @RequestMapping(value = "/servicePermission/v1/findList", method = RequestMethod.GET)
    List<ServicePermission> findServicePermissionList(@RequestParam("serviceName") String serviceName);

    /**
     * 新增服务权限
     *
     * @param servicePermissions
     * @return
     */
    @RequestMapping(value = "/servicePermission/v1/insertList", method = RequestMethod.POST)
    Map<String, Object> insertServicePermissionList(@RequestBody List<ServicePermission> servicePermissions);

    /**
     * 用户权限点增量保存
     *
     * @param scope
     * @param permissions
     * @return
     */
    @RequestMapping(value = "/permission/v1/save", method = RequestMethod.POST)
    Map<String, Object> saveUserPermissionList(@RequestParam("scope") String scope, @RequestBody List<Permission> permissions);

    /**
     * 查询用户数据权限
     *
     * @param dimensionCode
     * @param rId
     * @param prId
     * @return
     */
    @RequestMapping(value = "/program/v1/findUserProgramList", method = RequestMethod.GET)
    Map<String, Object> findUserProgramList(@RequestParam("dimensionCode") String dimensionCode, @RequestParam("rId") String rId, @RequestParam("prId") String prId);

    @RequestMapping(value = "/program/v1/findProgramItemsByUserId", method = RequestMethod.GET)
    Map<String, Object> findProgramItemsByUserId(@RequestParam("dimensionCode") String dimensionCode, @RequestParam("rId") String rId, @RequestParam("prId") String prId, @RequestParam("userId") Long userId);


    @RequestMapping(value = "/program/v1/findList", method = RequestMethod.POST)
    R<List<Program>> findProgramByIds(@RequestBody Program program);


    @RequestMapping(value = "/userRoleProgram/v1/findByUserIds", method = RequestMethod.POST)
    R<List<UserRoleProgram>> findUserRoleProgramByUserIds(@RequestBody UserRoleProgram userRoleProgram);
}


@Component
class SecurityFeignClientFallBack implements SecurityFeignClient {

    @Override
    public List<BaseService> findBaseServiceList(String serviceName) {

        return Collections.EMPTY_LIST;
    }

    @Override
    public Map<String, Object> insertBaseServiceList(List<BaseService> baseServices) {
        return ResultMapHelper.getServiceErrorMap("security");
    }

    @Override
    public List<ServicePermission> findServicePermissionList(String serviceName) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Map<String, Object> insertServicePermissionList(List<ServicePermission> servicePermissions) {
        return ResultMapHelper.getServiceErrorMap("security");
    }

    @Override
    public Map<String, Object> saveUserPermissionList(String scope, List<Permission> permissions) {
        return ResultMapHelper.getServiceErrorMap("security");
    }

    @Override
    public Map<String, Object> findUserProgramList(String dimensionCode, String rId, String prId) {

        return ResultMapHelper.getServiceErrorMap("security");
    }

    @Override
    public Map<String, Object> findProgramItemsByUserId(String dimensionCode, String rId, String prId, Long userId) {
        return ResultMapHelper.getServiceErrorMap("security");
    }

    @Override
    public R<List<Program>> findProgramByIds(Program program) {
        return RHelper.getServiceErrorR("security");
    }

    @Override
    public R<List<UserRoleProgram>> findUserRoleProgramByUserIds(UserRoleProgram userRoleProgram) {
        return RHelper.getServiceErrorR("security");
    }
}
