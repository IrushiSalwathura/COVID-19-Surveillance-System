package util;

public class HospitalTM {
    private String hospitalId;
    private String hospitalName;
    private String city;
    private String district;
    private int capacity;
    private String director;
    private String directorContactNo;
    private String hospitalContactNo1;
    private String hospitalContactNo2;
    private String hospitalFax;
    private String hospitalEmail;

    public HospitalTM() {
    }

    public HospitalTM(String hospitalId, String hospitalName, String city, String district, int capacity, String director, String directorContactNo, String hospitalContactNo1, String hospitalContactNo2, String hospitalFax, String hospitalEmail) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.city = city;
        this.district = district;
        this.capacity = capacity;
        this.director = director;
        this.directorContactNo = directorContactNo;
        this.hospitalContactNo1 = hospitalContactNo1;
        this.hospitalContactNo2 = hospitalContactNo2;
        this.hospitalFax = hospitalFax;
        this.hospitalEmail = hospitalEmail;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirectorContactNo() {
        return directorContactNo;
    }

    public void setDirectorContactNo(String directorContactNo) {
        this.directorContactNo = directorContactNo;
    }

    public String getHospitalContactNo1() {
        return hospitalContactNo1;
    }

    public void setHospitalContactNo1(String hospitalContactNo1) {
        this.hospitalContactNo1 = hospitalContactNo1;
    }

    public String getHospitalContactNo2() {
        return hospitalContactNo2;
    }

    public void setHospitalContactNo2(String hospitalContactNo2) {
        this.hospitalContactNo2 = hospitalContactNo2;
    }

    public String getHospitalFax() {
        return hospitalFax;
    }

    public void setHospitalFax(String hospitalFax) {
        this.hospitalFax = hospitalFax;
    }

    public String getHospitalEmail() {
        return hospitalEmail;
    }

    public void setHospitalEmail(String hospitalEmail) {
        this.hospitalEmail = hospitalEmail;
    }

    @Override
    public String toString() {
        return hospitalName;
    }
}
