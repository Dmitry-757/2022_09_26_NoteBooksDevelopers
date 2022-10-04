package org.dng.NoteBooksDevelopers.Model;

import java.util.Optional;

public class NotebookDeveloper {
    private long id;
    private String Name;
    private String Country;
    private String Logo;
    private int EmployeesNumber;
    private String ShortInfo;
    private byte[] photo;

    public NotebookDeveloper(long id, String name, String country, String logo, int employeesNumber, String shortInfo, byte[] photo) {
        this.id = id;
        Name = name;
        Country = country;
        Logo = logo;
        EmployeesNumber = employeesNumber;
        ShortInfo = shortInfo;
        this.photo = photo;
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

    public byte[] getPhoto() {
        return photo;
    }

    public Optional<byte[]> getPhotoOpt() {
        return Optional.ofNullable(photo);
    }

}
