package codeenthusiast.TrainingCenterApp.user.details;

import codeenthusiast.TrainingCenterApp.abstracts.SecurityService;
import codeenthusiast.TrainingCenterApp.exceptions.EntityNotFoundException;
import codeenthusiast.TrainingCenterApp.security.services.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
public class UDetailsServiceImpl implements UserDetailsService, SecurityService {

    private final UserDetailsRepository repository;

    private final UserDetailsMapper mapper;

    public UDetailsServiceImpl(UserDetailsRepository repository, UserDetailsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails saveUserDetails(UserDetails userDetails) {
        return save(userDetails);
    }

    @Override
    public UserDetailsDTO getUserDetailsByUserId(long userId) {
        UserDetails userDetails = getNotNullUserDetailsByUserIdFromRepo(userId);
        return mapToDTO(userDetails);
    }

    @Override
    public UserDetailsDTO updateUserDetails(long id, UserDetailsDTO dto) {
        UserDetails userDetails = getNotNullUserDetailsByIdFromRepo(id);
        authorize(hasAccess(userDetails));
        updateUserDetailsAttributes(dto, userDetails);
        return mapToDTO(save(userDetails));
    }

    private boolean hasAccess(UserDetails userDetails) {
        UserDetailsImpl userDetailsImpl = getPrincipal();
        return userDetails.getUser().getId().equals(userDetailsImpl.getId());
    }

    private UserDetails getNotNullUserDetailsByIdFromRepo(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private UserDetails getNotNullUserDetailsByUserIdFromRepo(long id) {
        return repository.findByUserId(id).orElseThrow(EntityNotFoundException::new);
    }

    private UserDetails save(UserDetails userDetails) {
        return repository.save(userDetails);
    }

    private void updateUserDetailsAttributes(UserDetailsDTO dto, UserDetails userDetails) {
        userDetails.setAge(dto.getAge());
        userDetails.setBodyWeightUnit(dto.getBodyWeightUnit());
        userDetails.setWeight(dto.getWeight());
        userDetails.setHeightUnit(dto.getHeightUnit());
        userDetails.setHeight(dto.getHeight());
        userDetails.setSex(dto.getSex());
    }

    private UserDetailsDTO mapToDTO(UserDetails userDetails) {
        return mapper.mapToDTO(userDetails);
    }
}
