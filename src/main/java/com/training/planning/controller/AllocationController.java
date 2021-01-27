package com.training.planning.controller;

import com.training.planning.domain.ResourceAllocationProblem;
import com.training.planning.service.SolvingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllocationController
{
    @Autowired
    private SolvingService solvingService;

    @PostMapping( value = "allocation" )
    public ResourceAllocationProblem solve( @RequestBody ResourceAllocationProblem problem )
    {
        return solvingService.solve( problem );
    }
}
