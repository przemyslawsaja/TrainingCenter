package codeenthusiast.TrainingCenterApp.exercise.enduranceexercise;

import codeenthusiast.TrainingCenterApp.constants.DistanceUnit;
import codeenthusiast.TrainingCenterApp.exercise.Exercise;
import codeenthusiast.TrainingCenterApp.movement.Motion;
import codeenthusiast.TrainingCenterApp.trainingsession.TrainingSession;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Entity(name = "endurance_exercises")
@Getter
@Setter
public class EnduranceExercise extends Exercise {

    @Enumerated(EnumType.STRING)
    private DistanceUnit distanceUnit;

    @ManyToOne
    private TrainingSession trainingSession;

    private int distance[];

    @Enumerated(EnumType.STRING)
    private TimeUnit timeUnit;

    private Duration time[];

    @Embedded
    private EnduranceExerciseDetails enduranceExerciseDetails;

    public EnduranceExercise(Long id, Motion motion, int sets, DistanceUnit distanceUnit,
                             TrainingSession trainingSession, int[] distance, TimeUnit timeUnit,
                             Duration[] time, EnduranceExerciseDetails enduranceExerciseDetails) {
        super(id, motion, sets);
        this.distanceUnit = distanceUnit;
        this.trainingSession = trainingSession;
        this.distance = distance;
        this.timeUnit = timeUnit;
        this.time = time;
        this.enduranceExerciseDetails = enduranceExerciseDetails;
    }
}
