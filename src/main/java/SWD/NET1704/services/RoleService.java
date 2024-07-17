package SWD.NET1704.services;

import org.springframework.http.ResponseEntity;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.RoleDTO;

public interface RoleService {

    ResponseEntity<ApiResponse> getAllRoles();
    ResponseEntity<ApiResponse> getRoleById(int roleId) throws Exception;
    ResponseEntity<ApiResponse> createRole(RoleDTO roleDTO);
    ResponseEntity<ApiResponse> updateRole(int roleId,RoleDTO roleDTO);
    ResponseEntity<ApiResponse> deleteRole(int roleId);
}
