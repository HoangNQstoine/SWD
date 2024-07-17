package SWD.NET1704.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import SWD.NET1704.dtos.response.ApiResponse;
import SWD.NET1704.dtos.response.RoleDTO;
import SWD.NET1704.entities.RoleEntity;
import SWD.NET1704.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

import static SWD.NET1704.utils.Constants.*;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public ResponseEntity<ApiResponse> getAllRoles() {
        try {
            List<RoleEntity> roleEntityList = roleRepository.findAll();
            List<RoleDTO> roleDTOS = new ArrayList<>();
            if (!roleEntityList.isEmpty()) {
                roleEntityList.forEach(role -> {
                    RoleDTO roleDTO = new RoleDTO(role.getRoleId(),
                            role.getRoleName());
                    roleDTOS.add(roleDTO);
                });
            }
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, roleDTOS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<ApiResponse> getRoleById(int roleId) {
        try {
            if (roleRepository.findById(roleId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));
            RoleEntity roleEntity = roleRepository.findById(roleId).get();
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRoleId(roleEntity.getRoleId());
            roleDTO.setRoleName(roleEntity.getRoleName());
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, roleDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createRole(RoleDTO roleDTO) {
        try {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRoleName(roleDTO.getRoleName());
            roleRepository.save(roleEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, roleDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateRole(int roleId, RoleDTO roleDTO) {
        try {
            if (roleRepository.findById(roleId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));
            RoleEntity roleEntity = roleRepository.findById(roleId).get();
            roleEntity.setRoleName(roleDTO.getRoleName());
            roleRepository.save(roleEntity);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS, roleDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteRole(int roleId) {
        try {
            if (roleRepository.findById(roleId).isEmpty())
                return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, NOT_FOUND));
            roleRepository.deleteById(roleId);
            return ResponseEntity.ok(new ApiResponse(STATUS_TRUE, SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(STATUS_FALSE, e.getMessage()));
        }
    }
}
