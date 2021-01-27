package com.training.planning.service;

import com.training.planning.domain.Passenger;
import com.training.planning.domain.Resource;
import com.training.planning.domain.ResourceAllocationProblem;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

import java.util.*;


public class ResourceAllocationEasyScore implements EasyScoreCalculator<ResourceAllocationProblem,HardSoftScore>
{
    @Override
    public HardSoftScore calculateScore( ResourceAllocationProblem resourceAllocationProblem )
    {
        int allResourceSize = resourceAllocationProblem.getResources().size();
        Map<Resource,Integer> passengerCountMap = new HashMap<>( allResourceSize );
        Map<Resource,Float> costMap = new HashMap<>( allResourceSize );
        for( Resource resource : resourceAllocationProblem.getResources() )
        {
            passengerCountMap.put( resource, 0 );
            costMap.put( resource, 0f );
        }

        Set<Resource> usedResources = new HashSet<>( allResourceSize );
        allocatePassengers( resourceAllocationProblem.getPassengers(), passengerCountMap, costMap, usedResources );

        int sumHardScore = sumHardScore( passengerCountMap );
        int sumSoftScore = sumSoftScore( costMap );
        return HardSoftScore.of( sumHardScore, sumSoftScore );
    }

    private void allocatePassengers( List<Passenger> passengers, Map<Resource,Integer> passengerCountMap, Map<Resource,Float> costMap, Set<Resource> usedResources )
    {
        for( Passenger passenger : passengers )
        {
            if( passenger.getResource() != null )
            {
                usedResources.add( passenger.getResource() );
                passengerCountMap.put( passenger.getResource(), fullPassengerCountOfResourceAfterAddingPassenger( passenger, passenger.getResource(), passengerCountMap ) );
                costMap.put( passenger.getResource(), fullCostOfResourceAfterAddingPassenger( passenger, passenger.getResource(), costMap ) );
            }
        }
    }

    private int sumHardScore( Map<Resource,Integer> passengerCountMap )
    {
        int hardScore = 0;
        for( Map.Entry<Resource,Integer> resourcePassengerCount : passengerCountMap.entrySet() )
        {
            int remainingCapacity = resourcePassengerCount.getKey().getCapacity() - resourcePassengerCount.getValue();
            if( remainingCapacity < 0 )
            {
                hardScore += remainingCapacity; // adding minus value -> giving minus score
            }
        }
        return hardScore;
    }

    private int sumSoftScore( Map<Resource,Float> costMap )
    {
        int softScore = 0;
        for( Map.Entry<Resource,Float> resourceCostEntry : costMap.entrySet() )
        {
            softScore -= resourceCostEntry.getValue(); // subtracting cost -> giving minus score
        }
        return softScore;
    }


    private float fullCostOfResourceAfterAddingPassenger( Passenger passenger, Resource resource, Map<Resource,Float> costMap )
    {
        if( "UNIT".equals( resource.getCostType() ) )
        {
            return resource.getCost();
        }
        else
        {
            return 0;
        }
    }

    private int fullPassengerCountOfResourceAfterAddingPassenger( Passenger passenger, Resource resource, Map<Resource,Integer> passengerCountMap )
    {
        Integer integer = passengerCountMap.get( resource );
        if( integer != null )
        {
            return integer + 1;
        }
        else
        {
            return 1;
        }
    }
}
