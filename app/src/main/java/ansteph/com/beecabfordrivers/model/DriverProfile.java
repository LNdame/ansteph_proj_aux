package ansteph.com.beecabfordrivers.model;

/**
 * Created by loicStephan on 12/07/16.
 */
public class DriverProfile {

    private String carModel;
    private String carNumberPlate;
    private String currentCity;
    private String profileRating;

    public DriverProfile(String carModel, String carNumberPlate, String currentCity, String profileRating) {
        this.carModel = carModel;
        this.carNumberPlate = carNumberPlate;
        this.currentCity = currentCity;
        this.profileRating = profileRating;
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
}
