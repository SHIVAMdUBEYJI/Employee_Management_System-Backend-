package com.sileo.bej.exception;

public class EmployeeAlreadyExists extends Exception{
    public EmployeeAlreadyExists(String message){
        super("Employee Already Exists,Please  Check Again");
    }
}
