package com.training.planning.domain;

import java.util.Objects;

public class Resource
{
    private String code;
    private int capacity;
    private int cost;
    private String costType = "UNIT";

    public Resource()
    {
    }

    public Resource( String code, int capacity, int cost )
    {
        this.code = code;
        this.capacity = capacity;
        this.cost = cost;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity( int capacity )
    {
        this.capacity = capacity;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost( int cost )
    {
        this.cost = cost;
    }

    public String getCostType()
    {
        return costType;
    }

    public void setCostType( String costType )
    {
        this.costType = costType;
    }

    @Override
    public boolean equals( Object o )
    {
        if( this == o ) return true;
        if( !( o instanceof Resource ) ) return false;
        Resource resource = ( Resource ) o;
        return Objects.equals( getCode(), resource.getCode() );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( getCode() );
    }
}
