package org.dng.NoteBooksDevelopers.Model;

public class NotebookDeveloper {
    private long id;
    private String Name;
    private String Country;
    private String Logo;
    private int EmployeesNumber;
    private String ShortInfo;

    public NotebookDeveloper(long id, String name, String country, String logo, int employeesNumber, String shortInfo) {
        this.id = id;
        Name = name;
        Country = country;
        Logo = logo;
        EmployeesNumber = employeesNumber;
        ShortInfo = shortInfo;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getCountry() {
        return Country;
    }

    public String getLogo() {
        return Logo;
    }

    public int getEmployeesNumber() {
        return EmployeesNumber;
    }

    public String getShortInfo() {
        return ShortInfo;
    }
}
