package codeenthusiast.TrainingCenterApp.mappers;

import codeenthusiast.TrainingCenterApp.abstracts.AbstractMapper;
import codeenthusiast.TrainingCenterApp.movement.Movement;
import codeenthusiast.TrainingCenterApp.movement.MovementDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovementMapper extends AbstractMapper<Movement, MovementDTO> {


    MovementDTO mapToDTO(Movement movement);

    Movement mapToEntity(MovementDTO movementDTO);

    List<Movement> mapToEntities(List<MovementDTO> movementDTOS);

    List<MovementDTO> mapToDTOs(List<Movement> movements);

}
