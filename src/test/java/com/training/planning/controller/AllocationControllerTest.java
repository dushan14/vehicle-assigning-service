package com.training.planning.controller;

import com.training.planning.domain.Passenger;
import com.training.planning.domain.Resource;
import com.training.planning.domain.ResourceAllocationProblem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest( properties = {
        "optaplanner.solver.termination.spent-limit=1h", // Effectively disable this termination in favor of the best-score-limit
        "optaplanner.solver.termination.best-score-limit=0hard/*soft"
} )
class AllocationControllerTest
{

    @Autowired
    AllocationController allocationController;

    @Test
    @Timeout( 600_000 )
    public void solve()
    {
        ResourceAllocationProblem problem = generateProblem();
        ResourceAllocationProblem solution = allocationController.solve( problem );
        assertFalse( solution.getPassengers().isEmpty() );
        for( Passenger passenger : solution.getPassengers() )
        {
            assertNotNull( passenger.getResource() );
        }

        assertTrue( solution.getScore().isFeasible() );
    }

    private ResourceAllocationProblem generateProblem()
    {
        List<Passenger> passengers = new ArrayList<>();
        passengers.add( new Passenger( 1, "A" ) );
        passengers.add( new Passenger( 2, "B" ) );
        passengers.add( new Passenger( 3, "C" ) );
        passengers.add( new Passenger( 4, "D" ) );
        passengers.add( new Passenger( 5, "E" ) );

        List<Resource> resources = new ArrayList<>();
        resources.add( new Resource( "Bus 1", 5,100 ) );
        resources.add( new Resource( "Bus 2", 5,50 ) );
        resources.add( new Resource( "Bus 3", 5, 60 ) );

        return new ResourceAllocationProblem( passengers, resources );
    }
}