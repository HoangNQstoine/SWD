package SWD.NET1704.controllers;

import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.RoleDTO;
import SWD.NET1704.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo-server/api/v1/role")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/getAllRoles")
    public   ResponseEntity<ApiResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/getRoleById/{id}")
    public   ResponseEntity<ApiResponse> getRoleById(@PathVariable("id") int roleId) throws Exception {
        return roleService.getRoleById(roleId);
    }

    @PostMapping("/createNewRole")
    public ResponseEntity<ApiResponse> createNewRole(@RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

    @PutMapping("/updateRole/{id}")
    public   ResponseEntity<ApiResponse> updateRole(@PathVariable("id") int roleId, @RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(roleId, roleDTO);
    }

    @DeleteMapping("/deleteRole/{id}")
    public   ResponseEntity<ApiResponse> deleteRole(@PathVariable("id") int roleId) {
        return roleService.deleteRole(roleId);
    }
}
