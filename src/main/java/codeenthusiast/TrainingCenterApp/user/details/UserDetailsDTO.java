package codeenthusiast.TrainingCenterApp.user.details;

import codeenthusiast.TrainingCenterApp.constants.BodyWeightUnit;
import codeenthusiast.TrainingCenterApp.constants.HeightUnit;
import codeenthusiast.TrainingCenterApp.constants.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDTO {

    private Long id;

    private BodyWeightUnit bodyWeightUnit;

    @Range(min = 0)
    private double weight;

    private HeightUnit heightUnit;

    @Range(min = 0)
    private double height;

    @Range(min = 1, max = 120)
    private short age;

    private Sex sex;

}
