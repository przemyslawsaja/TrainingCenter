package codeenthusiast.TrainingCenterApp.trainingsession;

import codeenthusiast.TrainingCenterApp.exceptions.EntityNotFoundException;
import codeenthusiast.TrainingCenterApp.security.services.UserDetailsImpl;
import codeenthusiast.TrainingCenterApp.trainingplan.TrainingPlan;
import codeenthusiast.TrainingCenterApp.trainingplan.TrainingPlanServiceImpl;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;

    private final TrainingPlanServiceImpl trainingPlanService;

    private final TrainingSessionMapper trainingSessionMapper;

    public TrainingSessionServiceImpl(TrainingSessionRepository trainingSessionRepository, TrainingPlanServiceImpl trainingPlanService, TrainingSessionMapper trainingSessionMapper) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingPlanService = trainingPlanService;
        this.trainingSessionMapper = trainingSessionMapper;
    }

    @Override
    public TrainingSession findEntityById(Long trainingPlanId) {
        TrainingSession trainingSession = trainingSessionRepository.findById(trainingPlanId).orElseThrow(
                () -> new EntityNotFoundException(trainingPlanId));
        if (!hasAccess(trainingSession)) {
            throw new AccessDeniedException("Access Denied");
        }
        return trainingSession;
    }

    @Override
    public TrainingSessionDTO findById(Long id) {
        TrainingSession trainingSession = findEntityById(id);
        return trainingSessionMapper.mapToDTO(trainingSession);
    }

    @Override
    public TrainingSessionDTO save(TrainingSession trainingSession, Long trainingPlanId) {
        TrainingPlan trainingPlan = trainingPlanService.getTrainingPlanEntityById(trainingPlanId);
        trainingSession.setTrainingPlan(trainingPlan);

        TrainingSession newSession = trainingSessionRepository.save(trainingSession);
        return trainingSessionMapper.mapToDTO(newSession);
    }

    @Override
    public TrainingSessionDTO save(TrainingSession trainingSession) {
        TrainingSession savedSession = trainingSessionRepository.save(trainingSession);
        return trainingSessionMapper.mapToDTO(savedSession);
    }

    @Override
    public List<TrainingSessionDTO> getAllByTrainingPlanId(Long trainingPlanId) {
        TrainingPlan trainingPlan = trainingPlanService.getTrainingPlanEntityById(trainingPlanId);
        if (!trainingPlanService.hasAccess(trainingPlan)) {
            throw new AccessDeniedException("Access denied");
        }
        List<TrainingSession> trainingSessionsList = trainingSessionRepository.findAllByTrainingPlanId(trainingPlanId);

        return trainingSessionMapper.mapToDTOs(trainingSessionsList);
    }

    @Override
    public TrainingSessionDTO create(TrainingSessionDTO dto, Long trainingPlanId) {
        TrainingPlan trainingPlan = trainingPlanService.getTrainingPlanEntityById(trainingPlanId);
        if (!trainingPlanService.hasAccess(trainingPlan)) {
            throw new AccessDeniedException("Access denied");
        }
        TrainingSession trainingSession = new TrainingSession(dto);
        return save(trainingSession, trainingPlanId);
    }

    @Override
    public TrainingSessionDTO update(Long id, TrainingSessionDTO dto) {
        TrainingSession oldSession = findEntityById(id);
        if (!hasAccess(oldSession)) {
            throw new AccessDeniedException("Access denied");
        }
        TrainingSession updatedSession = updateData(oldSession, dto);

        return save(updatedSession);
    }

    @Override
    public TrainingSession updateData(TrainingSession oldSession, TrainingSessionDTO dto) {
        oldSession.setDate(dto.getDate());
        oldSession.setDayOfWeek(dto.getDayOfWeek());
        oldSession.setDifficulty(dto.getDifficulty());
        oldSession.setEndTime(dto.getEndTime());
        oldSession.setStartTime(dto.getStartTime());
        oldSession.setName(dto.getName());
        oldSession.setNotes(dto.getNotes());
        return oldSession;
    }

    @Override
    public void deleteById(Long id) {
        TrainingSession oldSession = findEntityById(id);
        if (!hasAccess(oldSession)) {
            throw new AccessDeniedException("Access denied");
        }
        trainingSessionRepository.deleteById(id);
    }

    public boolean hasAccess(TrainingSession trainingSession) {
        UserDetailsImpl userDetailsImpl = getPrincipal();
        return trainingSession.getTrainingPlan().getUser().getId().equals(userDetailsImpl.getId());
    }

    private UserDetailsImpl getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }
}
