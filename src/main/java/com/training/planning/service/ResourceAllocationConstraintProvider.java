package com.training.planning.service;

import com.training.planning.domain.Passenger;
import com.training.planning.domain.Resource;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.util.function.Function;

import static org.optaplanner.core.api.score.stream.ConstraintCollectors.count;
import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class ResourceAllocationConstraintProvider
//        implements ConstraintProvider
{
//    @Override
    public Constraint[] defineConstraints( ConstraintFactory constraintFactory )
    {
        return new Constraint[] {
                // Hard constraints
                vehicleCapacityConstraint( constraintFactory ),

                // Soft constraints are only implemented in the "complete" implementation
                vehicleCost( constraintFactory )
        };
    }

    private Constraint vehicleCapacityConstraint( ConstraintFactory constraintFactory )
    {

        return constraintFactory.from( Passenger.class )
                                .groupBy( Passenger::getResource, count() )
                                .filter( ( vehicle, requiredCapacity ) -> requiredCapacity > vehicle.getCapacity() )
                                .penalize( "requiredCpuPowerTotal",
                                        HardSoftScore.ONE_HARD,
                                        ( vehicle, requiredCapacity ) -> requiredCapacity - vehicle.getCapacity() );

    }

    // ************************************************************************
    // Soft constraints
    // ************************************************************************

    Constraint vehicleCost( ConstraintFactory constraintFactory )
    {
        return constraintFactory.from( Resource.class )
                                .ifExists( Passenger.class, equal( Function.identity(), Passenger::getResource ) )
                                .penalize( "vehicleCost",
                                        HardSoftScore.ONE_SOFT,
                                        Resource::getCost );
    }

}
