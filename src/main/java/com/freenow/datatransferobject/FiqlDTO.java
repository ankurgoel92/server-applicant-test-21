package com.freenow.datatransferobject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FiqlDTO
{

    private static final Pattern DRIVER_CAR_QUERY_RGX = Pattern.compile("driver\\((.*)\\)([;,])car\\((.*)\\)");
    private static final Pattern DRIVER_QUERY_RGX = Pattern.compile("driver\\((.*)\\)");
    private static final Pattern CAR_QUERY_RGX = Pattern.compile("car\\((.*)\\)");

    private String driverQuery;
    private String operator;
    private String carQuery;


    public FiqlDTO(String query)
    {
        setValues(query);
    }


    private void setValues(String query)
    {
        Matcher m = DRIVER_CAR_QUERY_RGX.matcher(query);
        if (m.find())
        {
            this.driverQuery = m.group(1);
            this.operator = m.group(2);
            this.carQuery = m.group(3);
            return;
        }

        m = DRIVER_QUERY_RGX.matcher(query);
        if (m.find())
        {

            this.driverQuery = m.group(1);
            this.operator = null;
            this.carQuery = null;

            return;
        }

        m = CAR_QUERY_RGX.matcher(query);
        if (m.find())
        {

            this.carQuery = m.group(1);
            this.operator = null;
            this.driverQuery = null;

            return;
        }

    }


    public String getDriverQuery()
    {
        return driverQuery;
    }


    public void setDriverQuery(String driverQuery)
    {
        this.driverQuery = driverQuery;
    }


    public String getOperator()
    {
        return operator;
    }


    public void setOperator(String operator)
    {
        this.operator = operator;
    }


    public String getCarQuery()
    {
        return carQuery;
    }


    public void setCarQuery(String carQuery)
    {
        this.carQuery = carQuery;
    }

}
