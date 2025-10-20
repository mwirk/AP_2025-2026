package model;

public enum Position{
    CEO(25000.0f),
    viceCEO(18000.0f),
    Manager(12000.0f),
    Programmer(8000.0f),
    Intern(3000.0f);

    private float salary;
    private Position(float salary) {
        this.salary = salary;
    }

    public float getSalary() {
        return salary;
    }
}