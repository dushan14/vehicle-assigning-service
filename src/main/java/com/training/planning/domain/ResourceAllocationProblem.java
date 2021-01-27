package com.training.planning.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.ArrayList;
import java.util.List;

@PlanningSolution
public class ResourceAllocationProblem
{
    @PlanningEntityCollectionProperty
    List<Passenger> passengers = new ArrayList<>();

    @ValueRangeProvider( id = "resourceRange" )
    @ProblemFactCollectionProperty
    List<Resource> resources = new ArrayList<>();

    @PlanningScore
    private HardSoftScore score;

    public ResourceAllocationProblem()
    {
    }

    public ResourceAllocationProblem( List<Passenger> passengers, List<Resource> resources )
    {
        this.passengers = passengers;
        this.resources = resources;
    }

    public List<Passenger> getPassengers()
    {
        return passengers;
    }

    public void setPassengers( List<Passenger> passengers )
    {
        this.passengers = passengers;
    }

    public List<Resource> getResources()
    {
        return resources;
    }

    public void setResources( List<Resource> resources )
    {
        this.resources = resources;
    }

    public HardSoftScore getScore()
    {
        return score;
    }

    public void setScore( HardSoftScore score )
    {
        this.score = score;
    }
}
