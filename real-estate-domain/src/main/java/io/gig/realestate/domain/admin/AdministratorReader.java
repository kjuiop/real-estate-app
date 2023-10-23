package io.gig.realestate.domain.admin;

import io.gig.realestate.domain.admin.dto.AdminSearchDto;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import io.gig.realestate.domain.admin.dto.AdministratorListDto;
import io.gig.realestate.domain.team.Team;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
public interface AdministratorReader {

    long getCountAdministratorData();

    AdministratorDetailDto getAdminFindByUsername(String username);

    Administrator getAdministratorEntityByUsername(String username);

    Administrator getAdminEntityByUsername(String username);

    Page<AdministratorListDto> getAdminPageListBySearch(AdminSearchDto searchDto);

    boolean existUsername(String value);

    AdministratorDetailDto getAdminDetail(Long adminId);

    List<AdministratorListDto> getCandidateManagers(AdminSearchDto searchDto, String username);

    Page<AdministratorListDto> getCandidateMembers(AdminSearchDto searchDto);

    Administrator getAdminEntityById(Long adminId);

    List<AdministratorListDto> getAllAdministrators();

    List<AdministratorListDto> getAdministratorsByTeam(Team team);

    Page<AdministratorListDto> getAdminByTeamId(AdminSearchDto searchDto, Long teamId);
}
