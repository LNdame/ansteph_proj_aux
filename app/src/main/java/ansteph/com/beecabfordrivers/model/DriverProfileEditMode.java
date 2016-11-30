package ansteph.com.beecabfordrivers.model;

/**
 * Created by loicStephan on 24/11/2016.
 */

public class DriverProfileEditMode {

    private String carModel;
    private String carNumberPlate;
    private String currentCity;
    private String profileRating;
    String email, licence, year;

    public DriverProfileEditMode(String carModel, String carNumberPlate, String currentCity, String profileRating) {
        this.carModel = carModel;
        this.carNumberPlate = carNumberPlate;
        this.currentCity = currentCity;
        this.profileRating = profileRating;
    }


    public DriverProfileEditMode() {
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarNumberPlate() {
        return carNumberPlate;
    }

    public void setCarNumberPlate(String carNumberPlate) {
        this.carNumberPlate = carNumberPlate;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getProfileRating() {
        return profileRating;
    }

    public void setProfileRating(String profileRating) {
        this.profileRating = profileRating;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
