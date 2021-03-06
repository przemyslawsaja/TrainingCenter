package codeenthusiast.TrainingCenterApp.movement;

import codeenthusiast.TrainingCenterApp.image.ImageMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface MovementMapper {

    @Mapping(target = "muscles", ignore = true)
    MovementDTO mapToDTO(Movement movement);

    @Mapping(target = "musclesInvolved", ignore = true)
    @Mapping(target = "keyTechniqueElements", ignore = true)
    Movement mapToEntity(MovementDTO movementDTO);

    List<Movement> mapToEntities(List<MovementDTO> movementDTOS);


}
