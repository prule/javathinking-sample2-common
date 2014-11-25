package com.javathinking.sample2.common.file.input.beanio.example;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Date: 8/03/2014
 */
public class Employee {
    private String firstName;
    private String lastName;
    private String title;
    private BigDecimal salary;
    private Date hireDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
}
