package Model;

import java.util.Date;

public class SchoolFees {
    private Integer idSchoolFees;
    private Date paymentDate;
    private Integer amount;
    private Integer idStudent;

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setIdSchoolFees(Integer idSchoolFees) {
        this.idSchoolFees = idSchoolFees;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getIdSchoolFees() {
        return idSchoolFees;
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }
}
