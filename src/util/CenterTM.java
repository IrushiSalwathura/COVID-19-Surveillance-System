package util;

public class CenterTM {
     private String centerId;
     private String centerName;
     private String city;
     private String district;
     private String head;
     private String headContactNo;
     private String centerContactNo1;
     private String centerContactNo2;
     private int capacity;

    public CenterTM() {
    }

    public CenterTM(String centerId, String centerName, String city, String district, String head, String headContactNo, String centerContactNo1, String centerContactNo2, int capacity) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.city = city;
        this.district = district;
        this.head = head;
        this.headContactNo = headContactNo;
        this.centerContactNo1 = centerContactNo1;
        this.centerContactNo2 = centerContactNo2;
        this.capacity = capacity;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHeadContactNo() {
        return headContactNo;
    }

    public void setHeadContactNo(String headContactNo) {
        this.headContactNo = headContactNo;
    }

    public String getCenterContactNo1() {
        return centerContactNo1;
    }

    public void setCenterContactNo1(String centerContactNo1) {
        this.centerContactNo1 = centerContactNo1;
    }

    public String getCenterContactNo2() {
        return centerContactNo2;
    }

    public void setCenterContactNo2(String centerContactNo2) {
        this.centerContactNo2 = centerContactNo2;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return centerName;
    }
}
