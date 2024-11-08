package org.switch2022.project.datamodel.jpa.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "status_history")
public class StatusHistoryJPA {

    @Id
    @GeneratedValue
    private Long id;
    private String projectCode;
    private String status;
    private String date;

    /**
     * Constructor used by JPA through reflection.
     */
    public StatusHistoryJPA() {
    }

    /**
     * Constructs a new StatusHistoryJPA object with the specified project code, status, and date.
     *
     * @param projectCode the project code
     * @param status      the status
     * @param date        the date
     */
    public StatusHistoryJPA(String projectCode, String status, String date) {
        this.projectCode = projectCode;
        this.status = status;
        this.date = date;
    }

    /**
     * Returns the project code associated with the status history.
     *
     * @return the project code
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Returns the status associated with the status history.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the date associated with the status history.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }
}
