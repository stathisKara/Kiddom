package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
**/

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "provider_reports", schema = "mydb")
public class ProviderReportsEntity implements Serializable {
    private int reportId;
    //private String report;
    private String user_username;

    @Id
    @Column(name = "report_id")
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

//    @Basic
//    @Column(name = "report")
//    public String getReport() {
//        return report;
//    }
//
//    public void setReport(String report) {
//        this.report = report;
//    }

    @Basic
    @Column(name = "user_username")
    public String getUserUsername() {
        return user_username;
    }

    public void setUserUsername(String user_username) {
        this.user_username = user_username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderReportsEntity that = (ProviderReportsEntity) o;

        if (reportId != that.reportId) return false;
        //if (report != null ? !report.equals(that.report) : that.report != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reportId;
        //result = 31 * result + (report != null ? report.hashCode() : 0);
        return result;
    }

}
