package com.training.planning.service;

import com.training.planning.domain.ResourceAllocationProblem;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class SolvingService
{
    @Autowired
    private SolverManager<ResourceAllocationProblem,UUID> solverManager;

    public ResourceAllocationProblem solve( ResourceAllocationProblem problem )
    {
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving
        SolverJob<ResourceAllocationProblem,UUID> solverJob = solverManager.solve( problemId, problem );
        ResourceAllocationProblem solution;
        try
        {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        }
        catch( InterruptedException | ExecutionException e )
        {
            throw new IllegalStateException( "Solving failed.", e );
        }
        return solution;
    }
}
